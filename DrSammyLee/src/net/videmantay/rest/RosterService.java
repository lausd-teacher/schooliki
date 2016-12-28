package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.util.DB.db;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.googlecode.objectify.Key;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.rest.dto.ClassTimeDTO;
import net.videmantay.rest.dto.IncidentDTO;
import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.rest.dto.ScheduleDTO;
import net.videmantay.rest.dto.ScheduleItemDTO;
import net.videmantay.rest.dto.StudentRollDTO;
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.ClassTime;
import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.RosterStudent;
import net.videmantay.server.entity.Schedule;
import net.videmantay.server.entity.ScheduleItem;
import net.videmantay.server.entity.StudentIncident;
import net.videmantay.server.entity.StudentRoll;
import net.videmantay.server.util.DB;

@Path("/roster")
public class RosterService {

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
		
		Object currentUserId = request.getSession(false).getAttribute(LoginService.CURRENTUSERID_SESSION_ATTRIBUTE);

		if(currentUserId != null){
			String ownerId = currentUserId.toString();
			log.log(Level.INFO, "List rosters is called");

				List<Roster> rosterList = db().load().type(Roster.class).filter("ownerId", ownerId).list();
		
				List<RosterDTO> rosterDTOList = new ArrayList<RosterDTO>();
				for (Roster roster : rosterList) {
					RosterDTO dto = new RosterDTO(roster);
					rosterDTOList.add(dto);
				}
				return Response.ok().entity(rosterDTOList).build();
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
	public Response createRoster(RosterDTO rosterDTO, @Context HttpServletRequest request) throws Exception{
		
		Object currentUserId = request.getSession(false).getAttribute(LoginService.CURRENTUSERID_SESSION_ATTRIBUTE);
		
		if(currentUserId != null){
			String ownerId = currentUserId.toString();
				final Roster roster = Roster.createFromDTO(rosterDTO);
				roster.setOwnerId(ownerId);
		        Long id = rosterDB.save(roster).getId();
		        return Response.status(Status.CREATED).entity(id).build();
		}
		

		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delelteRoster(@PathParam("id") Long id) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			rosterDB.delete(result);

			return Response.ok().build();
		}

		return Response.status(Status.NOT_FOUND).build();

	}

	@GET
	@Path("/{id}/student")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRosterStudentsList(@PathParam("id") Long id) {

		List<RosterStudent> studentsList = ofy().load().type(RosterStudent.class).filter("rosterId", id).list();
		List<AppUserDTO> appUserDTOList = new ArrayList<AppUserDTO>();
	
		for (RosterStudent roster : studentsList) {
			AppUser student = ofy().load().key(Key.create(AppUser.class, roster.getStudentId())).now();
			appUserDTOList.add(new AppUserDTO(student));
		}
		return Response.ok().entity(appUserDTOList).build();
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
	public Response createRosterStudents(@PathParam("id") Long id, List<RosterStudentDTO> rosterStudents) {
		// cheking if roster exists and student exists
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();
		boolean studentsCheck = true;
		
		for(int i = 0; i < rosterStudents.size(); i++){
			RosterStudentDTO rosterStudentDTO = rosterStudents.get(i);
			AppUser student = ofy().load().key(Key.create(AppUser.class, rosterStudentDTO.getStudentId())).now();
			 if(student == null){
				 studentsCheck = false;
			 }
			
		}
		  
		if (result != null && studentsCheck) {
			
			for(int i = 0; i < rosterStudents.size(); i++){
				RosterStudentDTO rosterStudentDTO = rosterStudents.get(i);
				
				rosterStudentDB.save(RosterStudent.createFromDTO(rosterStudentDTO));
			}

			return Response.ok().build();
		}

		return Response.status(Status.NOT_FOUND).build();
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
	
//	@GET
//	@Path("/{id}/student/{studentId}/roll")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response takeRollForStudent(@PathParam("id") Long id, @PathParam("studentId") Long studentId) {
//		
//		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();
//
//		if (result != null) {
//
//			AppUser student = ofy().load().key(Key.create(AppUser.class, studentId)).now();
//			
//			if(student != null){
//					final StudentRoll newRoll = StudentRoll.createFromDTO(incidentDTO);
//				
//					Long newId = studentRollDB.save(newRoll).getId();
//						
//					return Response.ok().entity(newId).build();
//			}
//		}
//
//		return Response.status(Status.NOT_FOUND).build();
//	}
	
	
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
	
	@DELETE
	@Path("/{id}/schedule/{scheduleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSchedule(@PathParam("id") Long id, @PathParam("scheduleId") Long scheduleId){
		Key<Roster> rosKey = Key.create(Roster.class, id);
		Key<Schedule> sKey = Key.create(rosKey, Schedule.class, scheduleId);
		db().delete().key(sKey);
		
		return Response.ok().build();
	}

}
