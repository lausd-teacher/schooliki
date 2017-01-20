package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.util.DB.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import net.videmantay.rest.dto.ClassTimeDTO;
import net.videmantay.rest.dto.IncidentDTO;
import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.rest.dto.ScheduleDTO;
import net.videmantay.rest.dto.StudentRollDTO;
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.ClassTime;
import net.videmantay.server.entity.ClassTimeConfig;
import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.JoinRequest;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.RosterCodeGenerator;
import net.videmantay.server.entity.RosterConfig;
import net.videmantay.server.entity.RosterDetail;
import net.videmantay.server.entity.RosterStudent;
import net.videmantay.server.entity.Schedule;
import net.videmantay.server.entity.StudentIncident;
import net.videmantay.server.entity.StudentRoll;
import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.server.util.DB;

import com.google.api.client.repackaged.com.google.common.base.CharMatcher;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.primitives.Ints;

@Path("/roster")
public class RosterService{
	
	

	private final Logger log = Logger.getLogger("Roster Service");

	DB<Roster> rosterDB = new DB<Roster>(Roster.class);
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);

	DB<RosterStudent> rosterStudentDB = new DB<RosterStudent>(RosterStudent.class);
	
	DB<Incident> incidentDB = new DB<Incident>(Incident.class);
	
	DB<StudentIncident> studentIncidentDB = new DB<StudentIncident>(StudentIncident.class);
	
	DB<ClassTime> classTimeDB = new DB<ClassTime>(ClassTime.class);
	
	DB<StudentRoll> studentRollDB = new DB<StudentRoll>(StudentRoll.class);
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listRosters(@Context HttpServletRequest request) {
		ofy().clear();
		
		Subject subject = SecurityUtils.getSubject();
		Object currentUserId = subject.getPrincipal();
		
		if(currentUserId != null){
			String ownerId = currentUserId.toString();
			log.log(Level.INFO, "List rosters is called");

				List<Roster> rosterList = db().load().type(Roster.class).filter("ownerId", ownerId).list();
		
				
				return Response.ok().entity(rosterList).build();
		}

		return Response.ok().build();
	}
	

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoster(@PathParam("id") Long id) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			return Response.ok().entity(new RosterDTO(result)).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveRoster(Roster roster) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		Object currentUserId = subject.getPrincipal();
		if(currentUserId != null){
			String ownerId = currentUserId.toString();
				roster.setOwnerId(ownerId);
		        Long id = rosterDB.save(roster).getId();
		        if(roster.id == null){ //it's new and a rosterDetail must be created
		        	roster.setId(id);
		        	RosterDetail detail = new RosterDetail();
		        	detail.setId(roster.getId());
		        	detail.setGradeLevel(roster.gradeLevels);
		        	detail.setTitle(roster.getTitle());
		        	detail.description = roster.description;
		        	//create a 5 to 7 character string for roster join code
		        	RosterCodeGenerator codeGen = db().load().key(Key.create(RosterCodeGenerator.class, RosterCodeGenerator.KEYGEN)).now();
		        	detail.joinCode = codeGen.assignCode();
		        	
		        	TeacherInfo teacherInfo  = new TeacherInfo();
		        	AppUser appUser = db().load().type(AppUser.class)
		        			.filter("email", subject.getPrincipal().toString()).first().now();
		        	teacherInfo.setLastName(appUser.lastName);
		        	teacherInfo.setPicUrl(appUser.getImageUrl());
		        	detail.setTeacherInfo(teacherInfo);
		        	db().save().entities(detail,codeGen);
		        }
		        return Response.status(Status.CREATED).entity(roster).build();
		}
		

		return Response.status(Status.UNAUTHORIZED).build();
	}
	

	@DELETE
	@Path("/{id}")
	public Response delelteRoster(@PathParam("id") Long id) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			//deleting a roster is a huge ordeal probably
			//best for taskque
			rosterDB.delete(result);
			//get rosterDetail
			RosterDetail details = db().load().key(Key.create(RosterDetail.class, id)).now();
			
			//also clear up the code for someone else to use
			RosterCodeGenerator codeGen = db().load()
										.key(Key.create(RosterCodeGenerator.class,RosterCodeGenerator.KEYGEN)).now();
			codeGen.relinquishCode(details.joinCode);
			db().save().entity(codeGen);
			
			//delete everything associate with details
			db().delete().entities(db().load().ancestor(details).keys());
			return Response.ok().build();
		}//end if///

		return Response.status(Status.NOT_FOUND).build();

	}
	
	@POST
	@Path("/{id}/updateJoinCode")
	public Response updateJoinCode(@PathParam("id") Long id){
		//get generator
		RosterCodeGenerator codeGen = db().load().key(Key.create(RosterCodeGenerator.class, RosterCodeGenerator.KEYGEN)).now();
		RosterDetail detail = db().load().key(Key.create(RosterDetail.class, id)).now();
		detail.joinCode = codeGen.assignCode();
		db().save().entities(detail, codeGen);
		return Response.ok().entity(codeGen).build();
	}
	
	@GET
	@Path("{id}/rosterconfig")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRosterConfig(@PathParam("id") Long id){
		//must load the students
		///class times
		//and default classtime
		RosterConfig config = new RosterConfig();
		config.students.addAll(db().load().type(RosterStudent.class).ancestor(Key.create(RosterDetail.class, id)).list());
		config.classtimes.addAll(db().load().type(ClassTime.class).filter("rosterId", id).list());
		
		// if you have any classtimes load the default///
		if(config.classtimes.size() >= 1){
		Long ctId = null;
		boolean noMatch = true;
		for(ClassTime ct: config.classtimes){
			if(ct.isDefault){
			ctId = 	ct.getId();
			noMatch = false;
			break;
		}
		}
		if(noMatch){
			ctId = config.classtimes.iterator().next().id;
		}
		config.defaultTime = db().load().type(ClassTimeConfig.class).id(ctId).now();
		}//end check if has classtimes;
		return Response.ok().entity(config).build();	
	}

	@GET
	@Path("/{id}/student")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRosterStudentsList(@PathParam("id") Long id) {
	
		Key<RosterDetail> parent = Key.create(RosterDetail.class, id);
		List<RosterStudent> students = db().load().type(RosterStudent.class).ancestor(parent).list();
		return Response.ok().entity(students).build();
	}

	@GET
	@Path("/{id}/student/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRosterStudent(@PathParam("id") Long rosterId, @PathParam("studentId") Long studentId) {

		RosterStudent rosterStudent = ofy().load().key(Key.create(RosterStudent.class, studentId)).now();
		  
		if(rosterStudent != null){
			RosterStudentDTO rosterStudentDTO = new RosterStudentDTO(rosterStudent);
			return Response.ok().entity(rosterStudentDTO).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Path("/{id}/student")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRosterStudents(@PathParam("id") Long id, List<JoinRequest> joinRequests) {
		log.info("create student called with data of : " +joinRequests.iterator().next().email);
	
		// cheking if roster exists and student exists
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();
		List<RosterStudent> students = new ArrayList<>();
		//User user = UserServiceFactory.getUserService().getCurrentUser();
			if(result != null ){
			
				for(JoinRequest jr: joinRequests){
					if(jr.status.name().equalsIgnoreCase("accepted")){
					AppUser appUser = db().load().type(AppUser.class).filter("email", jr.email).first().now();
					RosterStudent stu = new RosterStudent(result.id, appUser);
					students.add(stu);
					}//endif
				}//end for
				db().save().entities(students);
				return Response.ok().entity(students).build();
				
			}//end if

		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/student/search/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response studentSearch(@QueryParam("q") String q, @QueryParam("p") String pageNum, @QueryParam("g") Set<String> grade){
		
		log.info("Search student Called!");
		log.info("q: " + q + "  p: " + pageNum + ",  g" + grade)  ;
		Query<AppUser> userQ =	db().load().type(AppUser.class).filter("roles", "STUDENT")
				.filter("email >=" , q).limit(50);
		
					List<AppUser> students = userQ.list();
					log.info("appUser list is  " + students);
			return Response.ok().entity(students).build();
	
	}
	@POST
	@Path("/{id}/student/{studentId}/incident")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createIncidentForStudent(@PathParam("id") Long id, IncidentDTO incidentDTO, @PathParam("studentId") Long studentId) {
		
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			incidentDTO.rosterId = id;

			AppUser student = ofy().load().key(Key.create(AppUser.class, studentId)).now();
			
			if(student != null){
					final Incident newIncident = Incident.createFromDTO(incidentDTO);
				
					Long newId = incidentDB.save(newIncident).getId();
					
					StudentIncident incident = new StudentIncident();
					incident.setIncidentId(newId);
					incident.setStudentId(studentId);
					
					studentIncidentDB.save(incident);
					
					int newIncidentPoints = incidentDTO.getValue() + student.getIncidentPointsAggregate();
					
					student.setIncidentPointsAggregate(newIncidentPoints);
					//update points aggregate
					appUserDB.save(student);
					
		
					return Response.ok().entity(newId).build();
			}
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	
	@POST
	@Path("/{id}/student/{studentId}/roll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response takeRollForStudent(@PathParam("id") Long id, StudentRollDTO incidentDTO, @PathParam("studentId") Long studentId) {
		
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			AppUser student = ofy().load().key(Key.create(AppUser.class, studentId)).now();
			
			if(student != null){
					final StudentRoll newRoll = StudentRoll.createFromDTO(incidentDTO);
				
					Long newId = studentRollDB.save(newRoll).getId();
						
					return Response.ok().entity(newId).build();
			}
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
		
	@GET
	@Path("/{id}/student/{studentId}/incident")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentIncidents(@PathParam("id") Long id, @PathParam("studentId") Long studentId) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			AppUser student = ofy().load().key(Key.create(AppUser.class, studentId)).now();
			
			if(student != null){
					List<StudentIncident> studentIncidents = db().load().type(StudentIncident.class).filter("studentId", studentId).list();
					List<IncidentDTO> incidentDTOList = new ArrayList<IncidentDTO>();
					
					for(StudentIncident studentIncident : studentIncidents){
						Incident incident = ofy().load().key(Key.create(Incident.class, studentIncident.getIncidentId())).now();
						
						incidentDTOList.add(new IncidentDTO(incident));
					}
		
					return Response.ok().entity(incidentDTOList).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	
//	@GET
//	@Path("/{id}/incident")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getIncidentList(@PathParam("id") Long id) {
//		List<Incident> incidentList = ofy().load().type(Incident.class).filter("rosterId", id).list();
//		List<IncidentDTO> incidentDTOList = new ArrayList<IncidentDTO>();
//
//		for (Incident incident : incidentList) {
//			IncidentDTO dto = new IncidentDTO(incident);
//			incidentDTOList.add(dto);
//		}
//
//		return Response.ok().entity(incidentDTOList).build();
//	}
	

	@GET
	@Path("/{id}/classtime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClasstimes(@PathParam("id") Long id) {
		
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			
			List<ClassTime> classTimeList = ofy().load().type(ClassTime.class).filter("rosterId", id).list();
			List<ClassTimeDTO> classTimeDTOList = new ArrayList<ClassTimeDTO>();
			for (ClassTime classTime : classTimeList) {
				ClassTimeDTO dto = new ClassTimeDTO(classTime);
				classTimeDTOList.add(dto);
			}
			return Response.ok().entity(classTimeDTOList).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
		
		
	}
	
	
	
	@GET
	@Path("/{id}/classtime/{subId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetailedClassTime(@PathParam("id") Long id, @PathParam("subId") Long classtimeId){
		
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			ClassTime classTime = ofy().load().key(Key.create(ClassTime.class, classtimeId)).now();
			if(classTime != null)
			    return Response.ok().entity(classTime).build();
			else
				Response.status(Status.NOT_FOUND).build();
				
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	@POST
	@Path("/{id}/classtime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createClassTime(@PathParam("id") Long id, ClassTimeDTO classTimeDTO) {
		
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			ClassTime classTime = ClassTime.createFromDTO(classTimeDTO);
			classTime.setRosterId(id);
			Long newId = classTimeDB.save(classTime).getId();
			return Response.ok().entity(newId).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/{id}/schedule")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchedule(@PathParam("id") Long id){
		
		Key<Roster> parent = Key.create(Roster.class, id);
		Schedule scheduleDB = db().load().type(Schedule.class).ancestor(parent).first().now();
		//in case of null create new
		if(scheduleDB == null){
			scheduleDB = new Schedule();
			scheduleDB.parent = parent;
			scheduleDB.id = db().save().entity(scheduleDB).now().getId();
			
		}
		ScheduleDTO retrieve = new ScheduleDTO(scheduleDB);
		return Response.ok().entity(retrieve).build();
		
	}
		
	
	
	@POST
	@Path("/{id}/schedule")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSchedule(@PathParam("id") Long id, ScheduleDTO schedule){
		//do validation here
		Schedule update = Schedule.fromDTO(schedule);
		
		//create key to persist the entity
		Key<Roster> parent = Key.create(Roster.class, id);
		Key<Schedule> sKey = Key.create(parent, Schedule.class, schedule.id);
		
		update.parent = parent;
			//1. try and retrieve
			if(schedule.id != null){
			
			Schedule scheCheck = db().load().key(sKey).now();
			
					if(scheCheck != null){// this is a valid schedule now update
					
						return Response.ok().build();
						
					}else{//error so return an error response!
						return Response.status(Status.BAD_REQUEST).build();
					}
				//alse add something to trigger the auto kupdate
					//here it goes.
					
			}else{//this is just a first save
				
				db().save().entity(schedule).now().getId();
				
				return Response.ok().build();
				
			}
		
	}
	/* Every roster will always have one schedule so it can never be deleted just changed 
	 */

}
