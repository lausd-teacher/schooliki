package net.videmantay.server.shiro.web.oauth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import net.videmantay.server.GoogleUtils;

public class OAuth2Filter extends AuthorizationFilter {
	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object obj) throws Exception {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		//check for oauth2 cred
		Credential cred = GoogleUtils.cred(user.getUserId());
		if(cred == null || cred.getExpiresInSeconds() < 600){
			return false;
		}else{
		return true;
		}
	}


}
