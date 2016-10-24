package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
import net.videmantay.security.GoogleTokenVerifier;
import net.videmantay.server.ErrorMessages;
import net.videmantay.server.ViewsUtils;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.shared.UserRoles;

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
		        	 res.getWriter().write(ViewsUtils.loginView(true, "tester"));
		        	  
		          }
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		
		String googleId = req.getParameter("googleId");
		String firstName = req.getParameter("email");
		String lastName = req.getParameter("firstName");
		String email= req.getParameter("lastName");
		String profilePicUrl= req.getParameter("profilePicUrl");
		String token = req.getParameter("token");
		String isAdmin = req.getParameter("isadminForm");
		
		log.log(Level.INFO, "Received " + googleId);
		log.log(Level.INFO, "Received " + firstName);
		log.log(Level.INFO, "Received " + lastName);
		log.log(Level.INFO, "Received " + profilePicUrl);
		log.log(Level.INFO, "Received " + isAdmin);
		
		
		if(googleId != null && firstName != null && lastName != null && email != null && profilePicUrl != null && token != null && isAdmin != null){
			    // First verify token
			    try {
					if(GoogleTokenVerifier.verifyToken(token)){
						//If token is valid then look for user in DB
						AppUser appUser = ofy().load().type(AppUser.class).filter("googleId", googleId).first().now();
						if(appUser != null){
							//if this is first time signing in
							if(!appUser.isActive()){
								appUser.setActive(true);
								//add user details to the database
							}
							
						   if(Boolean.parseBoolean(isAdmin)){
							   if(appUser.hasRole(UserRoles.ADMIN)){
								   res.sendRedirect("/admin");
							   }else{
								   res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_ADMIN)); 
							   }   
						   }else{
							   if(appUser.hasRole(UserRoles.TEACHER)){
								   res.sendRedirect("/teacher");
							   }else if (appUser.hasRole(UserRoles.FACULTY)){
								   res.sendRedirect("/faculty");
							   }else if(appUser.hasRole(UserRoles.STUDENT)){
								   res.sendRedirect("/student");
							   }
						   }
							
						}else{
							res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_INVITED));
						}
						
						
						
					}else{
						res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.TOKEN_NOT_VALID));
						
					}
				} catch (Exception e) {
					res.sendRedirect("/error.html");
					e.printStackTrace();
				}
			
			
			
			
		}else{
			
			res.sendRedirect("/error.html");
			
		}
				
	}

}
