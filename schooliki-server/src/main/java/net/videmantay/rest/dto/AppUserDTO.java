package net.videmantay.rest.dto;

import org.codehaus.jackson.annotate.JsonIgnore;

import net.videmantay.server.user.AppUser;
import net.videmantay.shared.UserRoles;

public class AppUserDTO {
	
    Long id;
   
    String name;
    
    String firstName;
    
    String lastName;
    
    String imageUrl;
    
    String eMail;
    
    String googleId;
    
    boolean active;
    
    boolean firstLogin;
    
    String[] roles;
    
    @JsonIgnore
    String password;

	public AppUserDTO() {

	}
	
	public AppUserDTO(AppUser appuser) {
		this.id = appuser.getId();
		this.name = appuser.getName();
		this.imageUrl = appuser.getImageUrl();
		this.eMail = appuser.geteMail();
		this.googleId = appuser.getGoogleId();
		this.active = appuser.isActive();
		this.roles = appuser.getRoles();
		this.password = appuser.getPassword();
		this.firstLogin = appuser.isFirstLogin();
		this.firstName = appuser.getFirstName();
		this.lastName = appuser.getLastName();
	}


	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public String geteMail() {
		return this.eMail;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getGoogleId() {
		return this.googleId;
	}


	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}



	public boolean isActive() {
		return this.active;
	}


	public void setActive(boolean isActive) {
		this.active = isActive;
	}



	public String[] getRoles() {
		return this.roles;
	}



	public void setRoles(String[] roles) {
		this.roles = roles;
	}


	@JsonIgnore
	public String getPassword() {
		return this.password;
	}


	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}



	public boolean isFirstLogin() {
		return this.firstLogin;
	}



	public void setFirstLogin(boolean isFirstLogin) {
		this.firstLogin = isFirstLogin;
	}



	public String getFirstName() {
		return this.firstName;
	}



	public String getLastName() {
		return this.lastName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
