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
import com.googlecode.objectify.VoidWork;

import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.server.user.DB;
import net.videmantay.server.user.Roster;
import net.videmantay.server.user.RosterStudent;

@Path("/roster")
public class RosterService {

	private final Logger log = Logger.getLogger("Roster Service");

	DB<Roster> rosterDB = new DB<Roster>(Roster.class);

	DB<RosterStudent> rosterStudentDB = new DB<RosterStudent>(RosterStudent.class);

	static {

		DB.start();
	}

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
	public Response createRoster(RosterDTO rosterDTO) {

		final Roster roster = Roster.createFromDTO(rosterDTO);

		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {

				Long id = rosterDB.save(roster).getId();

			}
		});

		return Response.status(Status.CREATED).build();
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
	public Response getRosterStudents(@PathParam("id") Long id) {

		List<RosterStudent> studentsList = ofy().load().type(RosterStudent.class).filter("parentRosterId", id).list();
		List<RosterStudentDTO> rosterStudentDTO = new ArrayList<RosterStudentDTO>();

		for (RosterStudent roster : studentsList) {
			RosterStudentDTO dto = new RosterStudentDTO(roster);
			rosterStudentDTO.add(dto);
		}

		return Response.ok().entity(rosterStudentDTO).build();
	}

	@POST
	@Path("/{id}/student")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRosterStudent(@PathParam("id") Long id, RosterStudentDTO rosterStudentDTO) {

		// cheking if roste exists
		Roster result = ofy().load().key(Key.create(Roster.class, id)).now();

		if (result != null) {

			rosterStudentDTO.parentRosterId = id;

			final RosterStudent rosterStd = RosterStudent.createFromDTO(rosterStudentDTO);

			ofy().transact(new VoidWork() {
				@Override
				public void vrun() {

					rosterStudentDB.save(rosterStd).getId();

				}
			});

			return Response.ok().build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

}
