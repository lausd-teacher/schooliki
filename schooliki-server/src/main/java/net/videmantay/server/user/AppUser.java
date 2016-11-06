package net.videmantay.server.user;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.shared.UserRoles;



@Entity
public  class AppUser  implements Serializable {

     
	@Id
    Long id;
        
    @Index
    String googleId;
    
    String name;
    
    String firstName;
    
    String lastName;
    
    String imageUrl;
    
    @Index
    String eMail;
    
    boolean active;
    
    boolean firstLogin;
    
    String[] roles;
    
    String password;

	public AppUser() {

	}
	
	

	public AppUser(String firstName, String lastName, String imageUrl, String eMail, String googleId, boolean isActive, String[] roles, boolean isFirstLogin) {
		this.name = firstName + " " + lastName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = firstName + " " + lastName;
		this.imageUrl = imageUrl;
		this.eMail = eMail;
		this.googleId = googleId;
		this.active = isActive;
		this.roles = roles;
		this.firstLogin = isFirstLogin;
	}



	public Long getId() {
		return this.id;
	}


	public String getName() {
		return this.firstName + " " + this.lastName;
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



	public String[] getRoles() {
		return this.roles;
	}



	public void setRoles(String[] roles) {
		this.roles = roles;
	}



	public void setActive(boolean isActive) {
		this.active = isActive;
	}



	public String getPassword() {
		return this.password;
	}



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



	public static AppUser createFromDTO(AppUserDTO appuserDTO) {
		AppUser appUser = new AppUser();
		appUser.id = appuserDTO.getId();
		appUser.active = appuserDTO.isActive();
		appUser.googleId = appuserDTO.getGoogleId();
		appUser.name = appuserDTO.getName();
		appUser.imageUrl = appuserDTO.getImageUrl();
		appUser.eMail = appuserDTO.geteMail();
		appUser.password = appuserDTO.getPassword();
		appUser.firstName =appuserDTO.getFirstName();
		appUser.lastName = appuserDTO.getLastName();
		appUser.firstLogin = appuserDTO.isFirstLogin();
		appUser.setRoles(appuserDTO.getRoles());
		return appUser;
	}
	
	
	public boolean hasRole(UserRoles role){
		for(int i = 0; i < roles.length; i++){
			if(roles[i].toString().equals(role.toString()))
				return true;
		}
		return false;
	}
	
	
}
