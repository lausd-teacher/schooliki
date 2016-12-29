package net.videmantay.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet{

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		return GoogleUtils.redirectUri(req);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		return GoogleUtils.authFlow(user.getUserId());
	}

	

}
