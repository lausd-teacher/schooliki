package net.videmantay.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import net.videmantay.security.AppCurrentUsersTokens;
import net.videmantay.security.GoogleTokenVerifier;
import net.videmantay.security.SchoolikiUserDetailsService;
import net.videmantay.server.ErrorMessages;
import net.videmantay.server.ViewsUtils;
import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;
import net.videmantay.shared.UserRoles;

public class LoginService extends HttpServlet {
	
	private final Logger log = Logger.getLogger("logger");
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
	
	//ApplicationContext applicationContext = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	//SchoolikiUserDetailsService schoolikiUserDetailsServices = applicationContext.getBean(SchoolikiUserDetailsService.class);
	
	@Autowired
	SchoolikiUserDetailsService schoolikiUserDetailsServices;
	
	static {
		DB.start();
	}
	
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		         
		         String query = req.getQueryString();
		           if(query != null && !query.isEmpty()){
		              String firstQuery = (query.split("&"))[0];
		              String[] firstQueryTokens = firstQuery.split("=");
		              String firstQueryType = firstQueryTokens[0];
		              String firstQueryNumber = firstQueryTokens[1];
		              
		              if(firstQueryType.equals("error")){
		            	  switch(firstQueryNumber){
		            	  case "1":
		            		  res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_INVITED));
		            		  break;
		            	  case "2":
		            		  res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.TOKEN_NOT_VALID));
		            		  break;
		            	  case "3":
		            		  res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_ADMIN)); 
		            	      break;
		            	  default:
		            	      break;
		            	  
		            	  
		            	  }
		            	  
		              }
		           } else{
		        		 
		        	   String token = AppCurrentUsersTokens.getUserToken(req.getSession().getId());
			          if(token == null){
			            res.getWriter().write(ViewsUtils.loginView(false, ""));
			          }else{
			        	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			        	 res.getWriter().write(ViewsUtils.loginView(true, auth.getName()));
			          }
		          
		           }
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		String googleId = req.getParameter("googleId");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email= req.getParameter("email");
		String profilePicUrl= req.getParameter("profilePicUrl");
		String token = req.getParameter("token");
		String isAdmin = req.getParameter("isadminForm");

		
		log.log(Level.INFO, "Received " + googleId);
		log.log(Level.INFO, "Received " + firstName);
		log.log(Level.INFO, "Received " + lastName);
		log.log(Level.INFO, "Received " + profilePicUrl);
		log.log(Level.INFO, "Received admin" + isAdmin);
		
		
		if(googleId != null && firstName != null && lastName != null && email != null && profilePicUrl != null && token != null && isAdmin != null){
			    // First verify token
			    try {
					if(GoogleTokenVerifier.verifyToken(token)){
						//If token is valid then look for user in DB
						AppUser appUser = ofy().load().type(AppUser.class).filter("eMail", email).first().now();
						
						if(appUser != null){
							//if this is first time signing in
										if(appUser.isFirstLogin()){
											appUser.setFirstLogin(false);
											appUser.setFirstName(firstName);
											appUser.setLastName(lastName);
											appUser.setImageUrl(profilePicUrl);
											appUser.setGoogleId(googleId);
											appUserDB.save(appUser);
										}
						
									   if(appUser.isActive()){
										   if(Boolean.parseBoolean(isAdmin)){
											   if(appUser.hasRole(UserRoles.ADMIN)){
												   login(email, req);
												   AppCurrentUsersTokens.registerUserToken(req.getSession().getId(), token);
												   res.sendRedirect("/admin");
											   }else{
												   //res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_ADMIN)); 
												   res.sendRedirect("/login?error=3");
											   }   
										   }else{
											   if(appUser.hasRole(UserRoles.TEACHER)){
												   login(email, req);
												   AppCurrentUsersTokens.registerUserToken(req.getSession().getId(), token);
												   res.sendRedirect("/teacher");
											   }else if (appUser.hasRole(UserRoles.FACULTY)){
												   login(email, req);
												   AppCurrentUsersTokens.registerUserToken(req.getSession().getId(), token);
												   res.sendRedirect("/faculty");
											   }else if(appUser.hasRole(UserRoles.STUDENT)){
												   login(email, req);
												   AppCurrentUsersTokens.registerUserToken(req.getSession().getId(), token);
												   res.sendRedirect("/student");
											   }
										   }
									   }else{
										   log.log(Level.INFO, "user is inactive");
										   res.sendRedirect("/error.html");
									   }
						}else{
							//res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.USER_NOT_INVITED));
							res.sendRedirect("/login?error=1");
						}		
					}else{
						//res.getWriter().write(ViewsUtils.loginViewWithErrors(ErrorMessages.TOKEN_NOT_VALID));
						res.sendRedirect("/login?error=2");
					}
				} catch (Exception e) {
					res.sendRedirect("/error.html");
					
					e.printStackTrace();
				}
		}else{
			res.sendRedirect("/error.html");
		}
				
	}
	
	
	private void login(String username, HttpServletRequest request){
		
		   UserDetails userDetails = schoolikiUserDetailsServices.loadUserByUsername(username);
		   UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
		   
		    // Authenticate the user
		    Authentication authentication = authenticationManager.authenticate(authRequest);
		    SecurityContext securityContext = SecurityContextHolder.getContext();
		    securityContext.setAuthentication(authentication);

		    // Create a new session and add the security context.
		    HttpSession session = request.getSession(true);
		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		    
		    log.log(Level.INFO, "User successfully authenticated");
		
	}

}
