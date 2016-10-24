package net.videmantay.rest.dto;

import net.videmantay.server.user.AppUser;
import net.videmantay.shared.UserRoles;

public class AppUserDTO {
	
    Long id;
   
    String name;
    
    String imageUrl;
    
    String eMail;
    
    String googleId;
    
    boolean isActive;
    
    UserRoles[] roles;
    
    String password;

	public AppUserDTO() {

	}
	
	

	public AppUserDTO(AppUser appuser) {
		this.id = appuser.getId();
		this.name = appuser.getName();
		this.imageUrl = appuser.getImageUrl();
		this.eMail = appuser.geteMail();
		this.googleId = appuser.getGoogleId();
		this.isActive = appuser.isActive();
		this.roles = appuser.getRoles();
		this.password = appuser.getPassword();
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
		return this.isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public UserRoles[] getRoles() {
		return this.roles;
	}



	public void setRoles(UserRoles[] roles) {
		this.roles = roles;
	}



	public String getPassword() {
		return this.password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
