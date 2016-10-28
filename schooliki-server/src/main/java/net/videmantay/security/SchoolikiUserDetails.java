package net.videmantay.security;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.videmantay.server.user.AppUser;
import net.videmantay.server.user.DB;

public class SchoolikiUserDetails implements UserDetails {
	
	DB<AppUser> appUserDB = new DB<AppUser>(AppUser.class);
	String username;
	AppUser currentUser;

	static {

		DB.start();
	}
	
	public SchoolikiUserDetails(String username){
		this.username = username;
		
		AppUser appuser = ofy().load().type(AppUser.class).filter("eMail", username).first().now();
		if(appuser == null)
			throw new UsernameNotFoundException(username + " not found in the dabase");
		
		
		this.currentUser = appuser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
		for(String role: currentUser.getRoles())
		     auths.add(new SimpleGrantedAuthority(role));
		return auths;
		
	}

	@Override
	public String getPassword() {
			 return currentUser.getPassword();
	}

	@Override
	public String getUsername() {
		return currentUser.geteMail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return currentUser.isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return currentUser.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return currentUser.isActive();
	}

	@Override
	public boolean isEnabled() {
		return currentUser.isActive();
	}

}
