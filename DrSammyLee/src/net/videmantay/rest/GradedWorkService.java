package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

import net.videmantay.server.entity.AssignedGradedWork;
import net.videmantay.server.entity.GoogleCalendarEvent;
import net.videmantay.server.entity.GradedWork;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.RosterStudent;
import net.videmantay.server.entity.StudentWork;
import net.videmantay.server.util.DB;

@Path("/gradedwork")
public class GradedWorkService {

	private final Logger log = Logger.getLogger("GradedWork Service");

	DB<GradedWork> gradedWorkDB = new DB<GradedWork>(GradedWork.class);

	DB<Roster> rosterDB = new DB<Roster>(Roster.class);

	DB<GoogleCalendarEvent> googleCalendarDB = new DB<GoogleCalendarEvent>(GoogleCalendarEvent.class);

	DB<StudentWork> studentWorkDB = new DB<StudentWork>(StudentWork.class);

	DB<AssignedGradedWork> assignedGradedeWokDB = new DB<AssignedGradedWork>(AssignedGradedWork.class);


	// Calendar calendar = GoogleUtils.calendar(null);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGradedWorkList(@QueryParam("rosterId") Long rosterId) {

		List<GradedWork> gradedWorkList;

		if (rosterId != null) {
			gradedWorkList = ofy().load().type(GradedWork.class).filter("rosterId", rosterId).list();
		return Response.ok().entity(gradedWorkList).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGradedWork(@PathParam("id") Long id) {

		GradedWork result = ofy().load().key(Key.create(GradedWork.class, id)).now();

		log.log(Level.INFO, "get graded work" + id);

		if (result != null) {
			return Response.ok().entity(result).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGradedWork(final GradedWork gradedWork) {

		Roster result = ofy().load().key(Key.create(Roster.class, gradedWork.rosterId)).now();

		if (result != null) {

			

			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {
					gradedWork.setId(gradedWorkDB.save(gradedWork).getId());
				}
			});

			return Response.ok().entity(gradedWork).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{id}/studentwork")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsWorkList(@PathParam("id") Long id) {
		List<StudentWork> studentWorkList = new ArrayList<>();
		
		List<AssignedGradedWork> assignedGradedWorkList = ofy().load().type(AssignedGradedWork.class)
				.filter("gradedWorkId", id).list();
		
		for (AssignedGradedWork assignedGraded : assignedGradedWorkList) {
			StudentWork studentWork = ofy().load().key(Key.create(StudentWork.class, assignedGraded.getStudentWorkId()))
					.now();
			studentWorkList.add(studentWork);
		}

		return Response.ok().entity(studentWorkList).build();
	}

	@POST
	@Path("/{id}/studentwork")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStudentsWork(@PathParam("id") Long id, final StudentWork studentWork) {

		GradedWork result = ofy().load().key(Key.create(GradedWork.class, id)).now();

		final RosterStudent rosterStudent = ofy().load()
				.key(Key.create(RosterStudent.class, studentWork.getRosterStudentId())).now();

		if (result != null && rosterStudent != null) {
			final Long idAsFinalVariable = id;

			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {
					studentWork.id = studentWorkDB.save(studentWork).getId();
					AssignedGradedWork assignedWork = new AssignedGradedWork();
					assignedWork.setGradedWorkId(idAsFinalVariable);
					assignedWork.setStudentWorkId(studentWork.id);
					assignedWork.setRosterStudentId(studentWork.rosterStudentId);
					assignedWork.setRosterId(rosterStudent.rosterKey.getId());

					assignedGradedeWokDB.save(assignedWork);
				}
			});

			return Response.ok().entity(studentWork).build();

		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/{id}/studentwork/{studentWorkId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudentsWork(@PathParam("id") Long id, @PathParam("studentWorkId") Long studentWorkId) {

		GradedWork result = ofy().load().key(Key.create(GradedWork.class, id)).now();

		final StudentWork studentWork = ofy().load().key(Key.create(StudentWork.class, studentWorkId)).now();

		final AssignedGradedWork aGradedWork = ofy().load().type(AssignedGradedWork.class)
				.filter("studentWorkId", studentWork.id).first().now();

		if (result != null && studentWork != null) {

			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {

					assignedGradedeWokDB.delete(aGradedWork);

					studentWorkDB.delete(studentWork);

				}
			});

			return Response.ok().build();

		}

		return Response.status(Status.NOT_FOUND).build();
	}

}
