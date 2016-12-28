package net.videmantay.rest;

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

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.util.DB;
import net.videmantay.server.util.UserPasswordGenerator;

import com.google.appengine.api.datastore.Entity;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.util.DB.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appuser")
public class AppUserService {

	private final Logger log = Logger.getLogger("Admin Service");

	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);


	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") Long id) {

		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now(); 
																				
																	
		log.log(Level.INFO, "get user is called " + id);

		if (result != null) {
			return Response.ok().entity(new AppUserDTO(result)).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listUsers() {

		ofy().clear();

		List<AppUser> userAcctList = new ArrayList<AppUser>();

		 userAcctList = db().load().type(AppUser.class).list();
		
		List<AppUserDTO> appUserDTOList = new ArrayList<AppUserDTO>();
		   
		   for(AppUser user: userAcctList)
			   appUserDTOList.add(new AppUserDTO(user));

		return Response.ok().entity(appUserDTOList).build();
	}
	
	
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(AppUserDTO appUser) {

		final AppUser toBeCreated = AppUser.createFromDTO(appUser);
		
		//Generate password for the newly created user
		
		  toBeCreated.setPassword(UserPasswordGenerator.nextPassword());

		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				toBeCreated.setId(appUserDB.save(toBeCreated).getId());
			}
		});
		return Response.status(Status.CREATED).entity(toBeCreated.getId()).build();
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUser(@PathParam("id") Long id, AppUserDTO newProperties) {

		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now();

		if (result != null) {
			newProperties.setId(id);
			
			AppUser modified = AppUser.createFromDTO(newProperties);

			appUserDB.save(modified);

			return Response.ok().build();

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
