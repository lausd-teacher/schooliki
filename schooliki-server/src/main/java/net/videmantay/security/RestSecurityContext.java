package net.videmantay.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.sun.jersey.spi.container.ContainerRequest;

public class RestSecurityContext implements SecurityContext{
	
	ContainerRequest containerRequest;
	
	public RestSecurityContext(ContainerRequest containerRequest) {
		this.containerRequest = containerRequest;
		
	}

	@Override
	public String getAuthenticationScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ContainerRequest getContainerRequest() {
		return this.containerRequest;
	}

}
