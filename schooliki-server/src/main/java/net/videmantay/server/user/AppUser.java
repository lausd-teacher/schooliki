package net.videmantay.server.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.server.validation.ValidRoles;
import net.videmantay.server.validation.ValidStringWithNoSpecialCharacters;
import net.videmantay.server.validation.ValidationMessages;
import net.videmantay.shared.UserRoles;
import net.videmantay.shared.UserStatus;



@Entity
public  class AppUser  implements Serializable {

     
	@Id
    Long id;
    
    @Index
    String token;
    
    @Index
    String googleId;
    
    String name;
    
    String imageUrl;
    
    String eMail;

	public AppUser() {

	}
	
	

	public AppUser(Long id, String token, String name, String imageUrl, String eMail, String googleId) {
		this.id = id;
		this.token = token;
		this.name = name;
		this.imageUrl = imageUrl;
		this.eMail = eMail;
		this.googleId = googleId;
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



	public static AppUser createFromDTO(AppUserDTO appuserDTO) {
		AppUser appUser = new AppUser();
		appUser.id = appuserDTO.getId();
		appUser.googleId = appuserDTO.getGoogleId();
		appUser.token = appuserDTO.getToken();
		appUser.name = appuserDTO.getName();
		appUser.imageUrl = appuserDTO.getImageUrl();
		appUser.eMail = appuserDTO.geteMail();
		
		return appUser;
	}
	
	
}
