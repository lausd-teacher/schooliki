package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;

public class LoginService extends HttpServlet {
	
	private final Logger log = Logger.getLogger("logger");
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);

	static {

		DB.start();
	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		String login = req.getParameter("login");
		
		ObjectMapper mapper = new ObjectMapper();
		
		AppUserDTO appuser = new AppUserDTO();
		try{
			appuser = mapper.readValue(login, AppUserDTO.class);
		}catch(Exception exp){
			
			res.sendRedirect("/error.html");
		}
		
		//Check, if exists
		
		if(appuser.getGoogleId().isEmpty() || appuser.getGoogleId() == null){
			res.sendRedirect("/error.html");
		}
		else{
		AppUser appUser = ofy().load().type(AppUser.class).filter("googleId", appuser.getGoogleId()).first().now();
		
		     if(appUser != null){
		    	 //Login
		    	 res.sendRedirect("/teacher");
		    	 
		     }else{
		    	 
		    	 //Add it to the dabase and redirect 
		    	 res.sendRedirect("/teacher");
		     }
		
		}
		
		//Else add it to the database
		   
		   
		//Redirect teacher
		
	}

}
