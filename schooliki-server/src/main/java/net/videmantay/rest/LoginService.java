package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.security.AppCurrentUsers;
import net.videmantay.server.ViewsUtils;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;

public class LoginService extends HttpServlet {
	
	private final Logger log = Logger.getLogger("logger");
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);

	static {

		DB.start();
	}
	
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		          String token = AppCurrentUsers.getUser(req.getSession().getId());
		           
		          if(token == null){
		            res.getWriter().write(ViewsUtils.loginView(false, ""));
		          }else{
		        	  //AppUser appUser = ofy().load().type(AppUser.class).filter("token", token).first().now();
		        	 res.getWriter().write(ViewsUtils.loginView(true, "tester"));
		        	  
		          }
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		//reading payload from POST
		 StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = req.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		
		log.log(Level.INFO, "received " + data);
		
		ObjectMapper mapper = new ObjectMapper();
		
		AppUserDTO appuserDTO = new AppUserDTO();
		try{
			appuserDTO = mapper.readValue(data, AppUserDTO.class);
		}catch(Exception exp){
			log.log(Level.INFO, "jackson exception " + exp.getMessage());
			res.sendRedirect("/error.html");
		}
		
		//Check, if exists
		
		if(appuserDTO.getGoogleId().isEmpty() || appuserDTO.getGoogleId() == null){
			res.sendRedirect("/error.html");
		}
		else{
			boolean isadmin = false;
		AppUser appUser = ofy().load().type(AppUser.class).filter("googleId", appuserDTO.getGoogleId()).first().now();
		
		     if(appUser == null){
		    	 appUserDB.save(AppUser.createFromDTO(appuserDTO));
		     }
		     
		   
		     if(isadmin){
		    	 //send redirect does not work with ajax
		    	 //res.getWriter().write("<h1></h1>");
		     } else { //for now there is only teacher
		    	 HttpSession session = req.getSession(true);
		          
		    	 AppCurrentUsers.registerUser(session.getId(), appuserDTO.getToken());
		          	            
		          res.getWriter().write("/teacher");
		          
		          
		          
		     }
		
		}
		
		
	}

}
