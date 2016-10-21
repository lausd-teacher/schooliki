package net.videmantay.security;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityFilter implements ContainerRequestFilter {

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {
		
		return new RestSecurityContext(containerRequest).getContainerRequest();
	}

}
