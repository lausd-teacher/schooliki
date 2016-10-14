package net.videmantay.rest;

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

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import com.google.appengine.api.datastore.Entity;

import static net.videmantay.server.user.DB.*;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appuser")
public class AppUserService {

	private final Logger log = Logger.getLogger("Admin Service");

	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);

	static {

		DB.start();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") Long id) {

		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now(); 
																				
																	
		log.log(Level.INFO, "get user is called " + id);

		if (result != null) {
			return Response.ok().entity(result).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listUsers() {

		ofy().clear();

		log.log(Level.INFO, "List users is called");

		List<AppUser> userAcctList = db().load().type(AppUser.class).list();

		return Response.ok().entity(userAcctList).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(AppUser appUser) {

		final AppUser toBeCreated = appUser;

		ofy().transact(new VoidWork() {

			@Override
			public void vrun() {
				toBeCreated.setId(appUserDB.save(toBeCreated).getId());

			}
		});
		return Response.status(Status.CREATED).build();
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUser(@PathParam("id") Long id, AppUser newProperties) {

		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now();

		if (result != null) {
			newProperties.setId(id);

			appUserDB.save(newProperties);

			return Response.ok().entity(newProperties).build();

		}

		return Response.status(Status.NOT_MODIFIED).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") Long id) {

		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now();

		if (result != null) {

			appUserDB.delete(result);

			return Response.ok().build();

		}

		return Response.status(Status.NOT_FOUND).build();
	}

}