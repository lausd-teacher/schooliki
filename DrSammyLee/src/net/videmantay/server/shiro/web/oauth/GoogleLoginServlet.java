// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        GoogleLoginServlet.java  (04-Oct-2012)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package net.videmantay.server.shiro.web.oauth;

import static net.videmantay.server.util.DB.*;

import net.videmantay.server.GoogleUtils;
import net.videmantay.server.entity.AppUser;
import net.videmantay.shared.UserRoles;
import net.videmantay.server.shiro.googlegae.GoogleGAEAuthenticationToken;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.event.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Google authentication is handled using the built-in service API, rather than calls
 * to OAuth, as for Facebook.  This is deliberate as if you only want this, then you don't
 * need to include the OAuth library.
 * <p>The idea is that you login by (a) logging in the user service and then (b) logging in to
 * Shiro with an access token, whose password is a dummy.  You "check" the password by making sure
 * that the logged-in user is the same as the one you are authenticating.
 * <p>What happens is that a request to URL "googleLogin" is made.  This then logs you
 * in with GAE user service.
 * Once this is done, and you're definitely logged in there is a redirect to URL "googleLoginAuth".
 * You are logged in to the suer service when you get here, and if there is no GAEUser with your Email
 * we create one (tagged as a Google user).
 * <p>When the <code>DatastoreRealm</code> is queried about this user (with dummy password) it authenticates
 * if the Google user has the same Email as the GAEUser.  This seems to be secure.
 * <p>One tiny fly in the ointment is that we really log you out on logout, which means that your browser
 * is logged out & you'll have to re-authenticate with other Google services.  I guess we could make this
 * an option if users find it too annoying.
 */

public class GoogleLoginServlet extends HttpServlet {
    static final Logger LOG = Logger.getLogger(GoogleLoginServlet.class.getName());

    @Override
    // this is called from login, and all that's required is to login via the Google URl with a return
    // to this place, but with GET as the verb
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AuthenticationException {
       
    	LOG.info("login server running");
    	
    	
    	UserService userService =  UserServiceFactory.getUserService();
            User currentUser = userService.getCurrentUser();
            
            if (currentUser == null) {
            	String currentUri = WebUtils.getRequestUri(request);
                String authUrl = userService.createLoginURL(currentUri);
                response.sendRedirect(authUrl);
                return;
            }
            String username = currentUser.getEmail();
            LOG.info(username + " is logged in");
            //upon login may be super user if so login auto
            Subject subject = SecurityUtils.getSubject();
            if(!subject.isAuthenticated()){//subject need to login/////
            if(username.equals("lee@videmantay.net")){
            	LOG.info("User is lee and if is passed");
            	UsernamePasswordToken token = new UsernamePasswordToken();
            	token.setPassword("adminpw".toCharArray());
            	token.setUsername("lee@videmantay.net");
            	token.setRememberMe(false);
            	subject.login(token);
            	response.sendRedirect("/admin");
            	return;
            }// end if super user
            
         }//end if subject login

            // add the user to the database
            AppUser user = db().load().type(AppUser.class).filter("email", username).first().now();
            if (user == null) {
               response.sendRedirect("/error.html");
            }
            //check to see if firstTime yes then pop the fields
        
            if(user.isFirstLogin()){
            	doFirstLogin(user,currentUser,response);
            }/// end if fist login //////
            

            String host = request.getRemoteHost();
            GoogleGAEAuthenticationToken token = new GoogleGAEAuthenticationToken(username,  host);
          
               loginWithNewSession(token, subject); 
            
    }
    
    private void doFirstLogin(AppUser user, User currentUser, HttpServletResponse response) throws IOException{
    	user.setFirstLogin(false);
        
    	
    	//get plus 
    	Credential cred = GoogleUtils.cred(currentUser.getUserId());
    	//possible null for cred if this is the case have user auth
    	//then send back here
    	if(cred == null){
    		response.sendRedirect("/auth");
    	}
    	Plus plus = GoogleUtils.plus(cred);
    	Person person = plus.people().get("me").execute();
    	//always do null check
    	if(person == null){
    		LOG.warning(user.getEmail() +" user doesn't have a Google+ account");
    		
    	}else{ //populate using person
    		user.setFirstName(person.getName().getGivenName());
    		user.setLastName(person.getName().getFamilyName());
    		user.setPersonalTitle(person.getName().getHonorificPrefix());
    		//if user is a student an image should be supplied by admin
    		if(user.getImageUrl() == null || user.getImageUrl().isEmpty()){
    			user.setImageUrl(person.getImage().getUrl());
    		}/// end populate person//////
    	}//end person null check////
    	//save changes //////
    	db().save().entity(user);
    }
    
    /**
     * Login and make sure you then have a new session.  This helps prevent session fixation attacks.
     *
     * @param token
     * @param subject
     */
    protected static void loginWithNewSession(AuthenticationToken token, Subject subject) {
        Session originalSession = subject.getSession();

        Map<Object, Object> attributes = Maps.newLinkedHashMap();
        Collection<Object> keys = originalSession.getAttributeKeys();
        for(Object key : keys) {
            Object value = originalSession.getAttribute(key);
            if (value != null) {
                attributes.put(key, value);
            }
        }
        originalSession.stop();
        subject.login(token);

        Session newSession = subject.getSession();
        for(Object key : attributes.keySet() ) {
            newSession.setAttribute(key, attributes.get(key));
        }
    }
  
}
