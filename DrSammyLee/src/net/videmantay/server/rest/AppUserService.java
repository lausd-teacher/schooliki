package net.videmantay.server.rest;

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
import net.videmantay.server.entity.AppUser;
import net.videmantay.server.util.DB;
import static com.googlecode.objectify.ObjectifyService.ofy;
import static net.videmantay.server.util.DB.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@Path("/appuser")
public class AppUserService {

	private final Logger log = Logger.getLogger("Admin Service");
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);


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

		List<AppUser> userAcctList;

		 userAcctList = db().load().type(AppUser.class).list();
		
		

		return Response.ok().entity(userAcctList).build();
	}
	
	
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(final AppUser appUser) {
		//validate here!//
		Set<ConstraintViolation<AppUser>> constraints = validator.validate(appUser);
		if(!constraints.isEmpty()){
			return Response.status(Status.NOT_ACCEPTABLE).entity(appUser).build();
		}
		appUser.firstLogin = true;
		
		ofy().transact(new VoidWork() {
			@Override
			public void vrun() {
				appUser.setId(appUserDB.save(appUser).getId());
			}
		});
		return Response.status(Status.CREATED).entity(appUser.getId()).build();
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUser(@PathParam("id") Long id, AppUser newProperties) {

		//first validate
		Set<ConstraintViolation<AppUser>>constraints = validator.validate(newProperties);
		if(!constraints.isEmpty()){
			return Response.status(Status.NOT_ACCEPTABLE).entity(newProperties).build();
		}
		AppUser result = ofy().load().key(Key.create(AppUser.class, id)).now();

		if (result != null) {
			newProperties.setId(id);
			
			

			appUserDB.save(newProperties);

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
