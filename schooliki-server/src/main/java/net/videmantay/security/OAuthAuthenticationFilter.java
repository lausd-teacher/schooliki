package net.videmantay.security;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import net.videmantay.rest.LoginService;

@Service
public class OAuthAuthenticationFilter extends GenericFilterBean {
	
	private static final String AUHTORIZATION_HEADER_NAME = "Authorization";
	
	private final Logger log = Logger.getLogger("logger");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException{
		
		   HttpServletResponse responseAsHttp = ((HttpServletResponse)response);
		   
		   HttpServletRequest requestAsHttp = ((HttpServletRequest)request);
		
		   String tokenInHeader = requestAsHttp.getHeader(AUHTORIZATION_HEADER_NAME);
		   
		   HttpSession currentSession = requestAsHttp.getSession(false);
		   
		   if(currentSession != null){
			   String currentToken = currentSession.getAttribute(LoginService.TOKEN_SESSION_ATTRIBUTE).toString();
			   
			     if(currentToken != null){
			    	 if(currentToken.equals(tokenInHeader)){
			              chain.doFilter(request, response);
					   }else{
						   responseAsHttp.setStatus(HttpStatus.UNAUTHORIZED_401);
						   //chain.doFilter(request, response); 
					   }
			     }else{
			    	 responseAsHttp.setStatus(HttpStatus.UNAUTHORIZED_401);
			     }
			   
		   }else{
			   responseAsHttp.setStatus(HttpStatus.UNAUTHORIZED_401);
		   }
		   
		   
		   log.log(Level.INFO, "received " + tokenInHeader);
		   
		   
		   
		   
		   
		   
	}

}
