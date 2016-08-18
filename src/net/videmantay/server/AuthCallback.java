package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import net.videmantay.server.user.DB;

public class AuthCallback extends
		AbstractAppEngineAuthorizationCodeCallbackServlet {

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		  GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		    url.setRawPath("/oauth2callback");
		    return url.build();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		return GoogleUtils.authFlow(user.getUserId());
	}
	
	  @Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
		  AuthorizationCodeFlow auth = initializeFlow();
		  
	    resp.sendRedirect("/teacher");
	  }

	  @Override
	  protected void onError(
	      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	      throws ServletException, IOException {
	    String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
	    resp.getWriter().print("<h3>" + nickname + ", why don't you want to play with me?</h1>");
	    resp.setStatus(200);
	    resp.addHeader("Content-Type", "text/html");
	  }
	
	

}
