package net.videmantay.rest.dto;

import net.videmantay.server.user.AppUser;

public class AppUserDTO {
	
	
	
    Long id;
   
    String token;
    
    String name;
    
    String imageUrl;
    
    String eMail;
    
    String googleId;

	public AppUserDTO() {

	}
	
	

	public AppUserDTO(AppUser appuser) {
		this.id = appuser.getId();
		this.token = appuser.getToken();
		this.name = appuser.getName();
		this.imageUrl = appuser.getImageUrl();
		this.eMail = appuser.geteMail();
		this.googleId = appuser.getGoogleId();
	}



	public Long getId() {
		return this.id;
	}

	public String getToken() {
		return this.token;
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

	public void setToken(String token) {
		this.token = token;
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
	
	

}
