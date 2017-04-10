package net.videmantay.server.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.util.DB.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Query;

import net.videmantay.server.entity.AppUser;
import net.videmantay.server.entity.FullRoutine;
import net.videmantay.server.entity.Routine;
import net.videmantay.server.entity.RoutineConfig;
import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.JoinRequest;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.RosterCodeGenerator;
import net.videmantay.server.entity.RosterConfig;
import net.videmantay.server.entity.RosterDetail;
import net.videmantay.server.entity.RosterStudent;
import net.videmantay.server.entity.Schedule;
import net.videmantay.server.entity.SeatingChart;
import net.videmantay.server.entity.StudentIncident;
import net.videmantay.server.entity.StudentRoll;
import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.server.util.DB;

@Path("/roster")
public class RosterService {

	private final Logger log = Logger.getLogger("Roster Service");

	DB<Roster> rosterDB = new DB<Roster>(Roster.class);

	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);

	DB<RosterStudent> rosterStudentDB = new DB<RosterStudent>(RosterStudent.class);

	DB<Incident> incidentDB = new DB<Incident>(Incident.class);

	DB<StudentIncident> studentIncidentDB = new DB<StudentIncident>(StudentIncident.class);

	DB<Routine> classTimeDB = new DB<Routine>(Routine.class);

	DB<StudentRoll> studentRollDB = new DB<StudentRoll>(StudentRoll.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listRosters(@Context HttpServletRequest request) {
		ofy().clear();

		Subject subject = SecurityUtils.getSubject();
		Object currentUserId = subject.getPrincipal();

		if (currentUserId != null) {
			String ownerId = currentUserId.toString();
			log.log(Level.INFO, "List rosters is called");

			List<Roster> rosterList = db().load().type(Roster.class).filter("ownerId", ownerId).list();

			return Response.ok().entity(rosterList).build();
		}

		return Response.ok().build();
	}

	/*
	 * @GET
	 * 
	 * @Path("/{id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getRoster(@PathParam("id") Long id) {
	 * 
	 * Roster result = ofy().load().key(Key.create(Roster.class, id)).now();
	 * 
	 * if (result != null) {
	 * 
	 * return Response.ok().entity(new RosterDTO(result)).build(); }
	 * 
	 * return Response.status(Status.NOT_FOUND).build(); }
	 */

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveRoster(Roster roster) throws Exception {

		log.info("The roster id is " + roster.id);
		Long rosterId = roster.id;

		Subject subject = SecurityUtils.getSubject();
		Object currentUserId = subject.getPrincipal();
		if (currentUserId != null) {
			String ownerId = currentUserId.toString();
			roster.setOwnerId(ownerId);
			Long id = rosterDB.save(roster).getId();
			log.info("now the roster id is " + roster.id);
			if (rosterId == null) { // it's new and a rosterDetail must be
									// created
				roster.setId(id);
				RosterDetail detail = new RosterDetail();
				
				
				detail.setId(roster.getId());
				detail.setGradeLevel(roster.gradeLevels);
				detail.setTitle(roster.getTitle());
				detail.description = roster.description;
				// create a 5 to 7 character string for roster join code
				RosterCodeGenerator codeGen = db().load()
						.key(Key.create(RosterCodeGenerator.class, RosterCodeGenerator.KEYGEN)).now();
				if (codeGen == null) {
					codeGen = new RosterCodeGenerator();
				}
				roster.joinCode = codeGen.assignCode(id);
				db().save().entity(codeGen);
				TeacherInfo teacherInfo = new TeacherInfo();
				AppUser appUser = db().load().type(AppUser.class).filter("email", subject.getPrincipal().toString())
						.first().now();
				teacherInfo.setLastName(appUser.lastName);
				teacherInfo.setPicUrl(appUser.getImageUrl());
				detail.setTeacherInfo(teacherInfo);

				ArrayList<Incident> incidents = new ArrayList<>();
				Incident incident;
				// we also need to create default incidents here too
				// positive list////////
				// 1. turned in hw - 1px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Turned in HW");
				incident.setPoints(1);
				incident.setImageUrl("/img/10.png");
				incidents.add(incident);
				// 2. paricipatation -1px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Participation");
				incident.setPoints(1);
				incident.setImageUrl("/img/32.png");
				incidents.add(incident);
				// 3. help others -3px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Helping others");
				incident.setPoints(3);
				incident.setImageUrl("/img/1.png");
				incidents.add(incident);
				// 4. took responsibility -2px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Taking responsibility");
				incident.setPoints(2);
				incident.setImageUrl("/img/5.png");
				incidents.add(incident);
				// 5. shared ideas -1px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Shared idea");
				incident.setPoints(1);
				incident.setImageUrl("/img/9.png");
				incidents.add(incident);
				// 6. listened attentively -5px;
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Listened attentively");
				incident.setPoints(1);
				incident.setImageUrl("/img/7.png");
				incidents.add(incident);
				// negative list///////////
				// 1. no hw -1
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("No HW");
				incident.setPoints(-1);
				incident.setImageUrl("/img/6.png");
				incidents.add(incident);
				// 2. interrupted -1
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Interrupted");
				incident.setPoints(-1);
				incident.setImageUrl("/img/1.png");
				incidents.add(incident);
				// 3. shouted out -3px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Shouting out");
				incident.setPoints(-3);
				incident.setImageUrl("/img/7.png");
				incidents.add(incident);
				// 4. distracted -2px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("");
				incident.setPoints(-2);
				incident.setImageUrl("/img/5.png");
				incidents.add(incident);
				// 5. bathroom - 1px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Bathroom break");
				incident.setPoints(-1);
				incident.setImageUrl("/img/32.png");
				incidents.add(incident);
				// 6. fighting -5px
				incident = new Incident();
				incident.setRosterId(roster.id);
				incident.setName("Fighting");
				incident.setPoints(-5);
				incident.setImageUrl("/img/9.png");
				incidents.add(incident);
				db().save().entities(incidents);
				
				Routine defaultTime = new Routine();
				defaultTime.rosterId = id;
				defaultTime.setDefault(true);
				defaultTime.setDescript("Routines refer to a set of procedures, groups, and stations that help students transition form one task to the next." +
							" \" Carpet Time\" , \"Author's Chair\", \"Gallery Walks\" are all example of routines.");
				defaultTime.title = "Class Routine";
				defaultTime.setDefault(true);
				defaultTime.id = db().save().entity(defaultTime).now().getId();
				RoutineConfig ctConfig = new RoutineConfig();
				ctConfig.id = defaultTime.id;
				SeatingChart seatingChart = new SeatingChart();
				seatingChart.id = defaultTime.id;
				db().save().entities(detail,seatingChart, ctConfig);

			} // end if first save
			return Response.status(Status.CREATED).entity(roster).build();
		}

		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteRoster(@PathParam("id") Long id) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			// deleting a roster is a huge ordeal probably
			// best for taskque
			// also clear up the code for someone else to use
						RosterCodeGenerator codeGen = db().load()
								.key(Key.create(RosterCodeGenerator.class, RosterCodeGenerator.KEYGEN)).now();
						codeGen.relinquishCode(result.joinCode);
						db().save().entity(codeGen);
			rosterDB.delete(result);
			// get rosterDetail
			RosterDetail details = db().load().key(Key.create(RosterDetail.class, id)).now();
			final RosterConfig config = db().load().key(Key.create(RosterConfig.class, id)).now();
		
			// delete everything associate with details
			db().delete().entities(result, details);
			// load all student related entities
			// for each student taskque

			/*
			 * TODO: student delete que will depend on this info so do this
			 * there db().transactNew(new VoidWork(){
			 * 
			 * @Override public void vrun() {
			 * db().delete().keys(config.classtimeKeys);
			 * db().delete().keys(config.studentKeys);
			 * db().delete().keys(config.incidentKeys);
			 * db().delete().entity(config); }});
			 */

			// TODO:and for each assignment taskque
			return Response.ok().build();
		} // end if///

		return Response.status(Status.NOT_FOUND).build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoster(@PathParam("id") final Long id) {
		// must load the students
		/// class times
		// and default classtime
		final RosterConfig config = new RosterConfig();
		

				Set<RosterStudent> rosterStudents = new HashSet<RosterStudent>();
				rosterStudents.addAll(
						db().load().type(RosterStudent.class).ancestor(Key.create(RosterDetail.class, id)).list());
				if (rosterStudents != null) {
					config.students.addAll(rosterStudents);
				}
				
				//get routines for roster
				config.classtimes.addAll(db().load().type(Routine.class).filter("rosterId", id).list());
				//now cycle and find default
				if(config.classtimes.size() > 0){
				for(Routine r: config.classtimes){
					if(r.isDefault){
						config.defaultTime = db().load().key(Key.create(RoutineConfig.class, r.id)).now();
						break;
					}
				}
				
				if(config.defaultTime == null){
					Long configId = config.classtimes.iterator().next().id;
					config.defaultTime = db().load().key(Key.create(RoutineConfig.class, configId)).now();
				}
				}
				
				 config.incidents.addAll(db().load().type(Incident.class).filter("rosterId", id).list());

		return Response.ok().entity(config).build();
	}

	@POST
	@Path("/{id}/updateJoinCode")
	public Response updateJoinCode(@PathParam("id") Long id) {
		// get generator
		RosterCodeGenerator codeGen = db().load().key(Key.create(RosterCodeGenerator.class, RosterCodeGenerator.KEYGEN))
				.now();
		Roster config = db().load().key(Key.create(Roster.class, id)).now();
		codeGen.relinquishCode(config.joinCode);
		config.joinCode = codeGen.assignCode(id);
		db().save().entities(config, codeGen);
		return Response.ok().entity(codeGen).build();
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
		Key<RosterDetail> parent = Key.create(RosterDetail.class, rosterId);
		RosterStudent rosterStudent = ofy().load().key(Key.create(parent, RosterStudent.class, studentId)).now();

		if (rosterStudent != null) {
			return Response.ok().entity(rosterStudent).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Path("/{id}/student")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRosterStudents(@PathParam("id") Long id, List<JoinRequest> joinRequests) {
		log.info("create student called with data of : " + joinRequests.iterator().next().email);

		// cheking if roster exists and student exists
		Key<RosterDetail> rosKey = Key.create(RosterDetail.class, id);
		
		RosterDetail details = ofy().load().key(rosKey).now();
		List<RosterStudent> students = new ArrayList<>();
		// User user = UserServiceFactory.getUserService().getCurrentUser();
		if (details != null) {
			for (JoinRequest jr : joinRequests) {
				if (jr.status.name().equalsIgnoreCase("accepted")) {
					Key<RosterStudent> stuKey = Key.create(rosKey, RosterStudent.class, jr.email);
					// check that it doesn't exist in roster
					RosterStudent stuCheck = db().load().key(stuKey).now();
					if(stuCheck != null){
						continue;
					}
					AppUser appUser = db().load().type(AppUser.class).filter("email", jr.email).first().now();
					RosterStudent stu = new RosterStudent(id, appUser);
					students.add(stu);
				} // endif
			} // end for
			//save it to the db
			ArrayList<RosterStudent> sendMe = new ArrayList<>();
			 sendMe.addAll(db().save().entities(students).now().values());
			return Response.ok().entity(sendMe).build();

		} // end if

		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/student/search/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response studentSearch(@QueryParam("q") String q, @QueryParam("p") String pageNum,
			@QueryParam("g") Set<String> grade) {

		log.info("Search student Called!");
		log.info("q: " + q + "  p: " + pageNum + ",  g" + grade);
		Query<AppUser> userQ = db().load().type(AppUser.class).filter("roles", "STUDENT").filter("email >=", q)
				.limit(50);

		List<AppUser> students = userQ.list();
		log.info("appUser list is  " + students);
		return Response.ok().entity(students).build();

	}

	@POST
	@Path("/{id}/student/{studentId}/incident")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createIncidentForStudent(@PathParam("id") Long id, StudentIncident stuIncident,
			@PathParam("studentId") String studentId) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
			stuIncident.parent = Key.create(RosterStudent.class, studentId);

			RosterStudent student = ofy().load().key(Key.create(RosterStudent.class, studentId)).now();

			if (student != null) {

				if (stuIncident.points < 0) {
					student.negPoints += stuIncident.points;
				} else {
					student.posPoints += stuIncident.points;
				}

				// update points aggregate
				db().save().entity(student);
				stuIncident.id = db().save().entity(stuIncident).now().getId();

				return Response.ok().entity(stuIncident).build();
			}
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Path("/{id}/batch/student/incident")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response batchCreateStudentIncident(@PathParam("id") Long id, List<StudentIncident> stuIncidents) {
		// parent key
		Key<RosterDetail> parent = Key.create(RosterDetail.class, id);
		// Might as well batch get//
		ArrayList<Key<RosterStudent>> keys = new ArrayList<Key<RosterStudent>>();

		// save for batch save
		ArrayList<RosterStudent> students = new ArrayList<RosterStudent>();
		// obviously we need to serious validation
		for (StudentIncident si : stuIncidents) {
			keys.add(Key.create(parent, RosterStudent.class, si.studentAcct));
		}
		students.addAll(db().load().keys(keys).values());
		// complicated iteration ///
		for (RosterStudent rs : students) {
			// cycle through incidents and match
			for (StudentIncident si : stuIncidents) {
				if (rs.studentAcctId.equals(si.studentAcct)) {
					if (si.points < 0) {
						rs.negPoints += si.points;
					} else {
						rs.posPoints += si.points;
					}
					break;
				} // end if student match incident
			} // end for incidents
		} // end for students

		// update the student points
		db().save().entities(students);

		stuIncidents.clear();
		stuIncidents.addAll(db().save().entities(stuIncidents).now().values());

		return Response.ok().entity(stuIncidents).build();
	}

	@POST
	@Path("/{id}/student/{studentId}/roll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response takeRollForStudent(@PathParam("id") Long id, StudentRoll studentRoll,
			@PathParam("studentId") String studentId) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			RosterStudent student = ofy().load().key(Key.create(RosterStudent.class, studentId)).now();

			if (student != null) {

				Long newId = studentRollDB.save(studentRoll).getId();

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

			if (student != null) {
				List<StudentIncident> studentIncidents = db().load().type(StudentIncident.class)
						.filter("studentId", studentId).list();

				return Response.ok().entity(studentIncidents).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}/classtime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClasstimeList(@PathParam("id") Long id) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			List<Routine> routineList = ofy().load().type(Routine.class).filter("rosterId", id).list();
			
			
			return Response.ok().entity(routineList).build();
		}

		return Response.status(Status.NOT_FOUND).build();

	}

	@GET
	@Path("/{id}/classtime/{subId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClassTime(@PathParam("id") Long id, @PathParam("subId") Long classtimeId) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {
				Routine routine = ofy().load().key(Key.create(Routine.class, classtimeId)).now();
				RoutineConfig config = ofy().load().key(Key.create(RoutineConfig.class,classtimeId)).now();
			
				return Response.ok().entity(new FullRoutine(routine,config)).build();
		}else{

		return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/{id}/classtime/{subId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createClassTime(@PathParam("id") Long id, FullRoutine fullRoutine) {

		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			fullRoutine.routine.setRosterId(id);
			Long newId = classTimeDB.save(fullRoutine.routine).getId();
			fullRoutine.routineConfig.id = newId;
			db().save().entity(fullRoutine.routineConfig);
			return Response.ok().entity(newId).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}/classtime/{classtimeId}/seatingchart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeatingChart(@PathParam("id") Long id, @PathParam("classtimeId") Long classtimeId) {
		// TODO:check for roster blah blah
		SeatingChart seatingChart = db().load().key(Key.create(SeatingChart.class, classtimeId)).now();
		if (seatingChart == null) {
			seatingChart = new SeatingChart();
			seatingChart.id = classtimeId;
			db().save().entity(seatingChart);
		}
		return Response.ok().entity(seatingChart).build();
	}

	@POST
	@Path("/{id}/classtime/{classtimeId}/seatingchart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveSeatingChart(@PathParam("id") Long id,RoutineConfig config,
			SeatingChart seatingChart) {
		// TODO: set up checks
		db().save().entity(seatingChart);
		return Response.ok().build();
	}

	@GET
	@Path("/{id}/schedule")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchedule(@PathParam("id") Long id) {

		Key<Schedule> sKey = Key.create(Schedule.class, id);
		Schedule scheduleDB = db().load().key(sKey).now();
		// in case of null create new
		if (scheduleDB == null) {
			scheduleDB = new Schedule();
			scheduleDB.id = db().save().entity(scheduleDB).now().getId();
		}
		
		return Response.ok().entity(scheduleDB).build();

	}

	@POST
	@Path("/{id}/schedule")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveSchedule(@PathParam("id") Long id, Schedule schedule) {
		// do validation here
		

		// create key to persist the entity
		Key<Schedule> sKey = Key.create(Schedule.class, schedule.id);

	
		// 1. try and retrieve
		if (schedule.id != null) {

			Schedule scheCheck = db().load().key(sKey).now();

			if (scheCheck != null) {// this is a valid schedule now update

				return Response.ok().build();

			} else {// error so return an error response!
				return Response.status(Status.BAD_REQUEST).build();
			}
			// alse add something to trigger the auto kupdate
			// here it goes.

		} else {// this is just a first save

			db().save().entity(schedule).now().getId();

			return Response.ok().build();

		}

	}
	/*
	 * Every roster will always have one schedule so it can never be deleted
	 * just changed
	 */
	
	@GET
	@Path("/{id}/incident")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listIncidents(@PathParam("id") Long id){
		List<Incident> incidents = db().load().type(Incident.class).filter("rosterId", id).list();
		return Response.ok().entity(incidents).build();
	}
	
	@POST
	@Path("/{id}/incident/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateIncident(@PathParam("id") Long id, Incident incident){
		
		incident.setId(db().save().entity(incident).now().getId());
		return Response.ok().entity(incident).build();
	}

}
