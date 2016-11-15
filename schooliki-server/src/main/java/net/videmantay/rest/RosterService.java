package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.user.DB.db;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.googlecode.objectify.Key;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.rest.dto.ClassTimeDTO;
import net.videmantay.rest.dto.IncidentDTO;
import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.server.entity.ClassTime;
import net.videmantay.server.entity.Incident;
import net.videmantay.server.entity.StudentIncident;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.server.user.Roster;
import net.videmantay.server.user.RosterStudent;

@Path("/roster")
public class RosterService {

	private final Logger log = Logger.getLogger("Roster Service");

	DB<Roster> rosterDB = new DB<Roster>(Roster.class);

	DB<RosterStudent> rosterStudentDB = new DB<RosterStudent>(RosterStudent.class);
	
	DB<Incident> incidentDB = new DB<Incident>(Incident.class);
	
	DB<StudentIncident> studentIncidentDB = new DB<StudentIncident>(StudentIncident.class);
	
	DB<ClassTime> classTimeDB = new DB<ClassTime>(ClassTime.class);


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listRosters() {
		ofy().clear();

		log.log(Level.INFO, "List rosters is called");

		List<Roster> rosterList = db().load().type(Roster.class).list();

		List<RosterDTO> rosterDTOList = new ArrayList<RosterDTO>();
		for (Roster roster : rosterList) {
			RosterDTO dto = new RosterDTO(roster);
			rosterDTOList.add(dto);
		}

		return Response.ok().entity(rosterDTOList).build();
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
	public Response createRoster(RosterDTO rosterDTO) throws Exception{
		final Roster roster = Roster.createFromDTO(rosterDTO);
		Long id = rosterDB.save(roster).getId();
		

		return Response.status(Status.CREATED).entity(id).build();
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

	
	@GET
	@Path("/{id}/incident")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIncidentList(@PathParam("id") Long id) {
		List<Incident> incidentList = ofy().load().type(Incident.class).filter("rosterId", id).list();
		List<IncidentDTO> incidentDTOList = new ArrayList<IncidentDTO>();

		for (Incident incident : incidentList) {
			IncidentDTO dto = new IncidentDTO(incident);
			incidentDTOList.add(dto);
		}

		return Response.ok().entity(incidentDTOList).build();
	}
	

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

}
