package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class Auth extends AbstractAppEngineAuthorizationCodeServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException{
		checkCreds(res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException , ServletException{
		checkCreds(res);
	}
	
	private void checkCreds(HttpServletResponse res) throws ServletException, IOException{
		User user = UserServiceFactory.getUserService().getCurrentUser();
		Subject subject = SecurityUtils.getSubject();
		if(user == null){
			res.sendRedirect("/login");
		}
		Credential cred = GoogleUtils.cred(user.getUserId());
		if(cred == null|| cred.getExpiresInSeconds() < 1800){
			initializeFlow();
		}else{
			if(subject.hasRole("STUDENT")){
				res.sendRedirect("/student");
				return;
			}
			if(subject.hasRole("TEACHER")){
				res.sendRedirect("/teacher");
				return;
			}
			if(subject.hasRole("ADMIN")){
				res.sendRedirect("/amdin");
				return;
			}
		}
	}
	
	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
			return GoogleUtils.redirectUri(req);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		String userId = user.getUserId();
		return GoogleUtils.authFlow(userId);
	}

}
