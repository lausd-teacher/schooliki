package net.videmantay.server.shiro.web.oauth;

import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import net.videmantay.server.GoogleUtils;
import net.videmantay.server.shiro.googlegae.GoogleGAEAuthenticationToken;

public class OAuth2Filter extends AuthorizationFilter {
	Logger LOG = Logger.getAnonymousLogger();
	
	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object obj) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			User user = UserServiceFactory.getUserService().getCurrentUser();
			subject.login(new GoogleGAEAuthenticationToken(user.getEmail(), "lausd.net"));
		}
		return subject.isPermittedAll("oauth2:*");
	}
}
