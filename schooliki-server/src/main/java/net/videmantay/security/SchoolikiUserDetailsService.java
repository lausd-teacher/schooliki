package net.videmantay.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SchoolikiUserDetailsService implements UserDetailsService {
	

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new SchoolikiUserDetails(userName);
	}

}
