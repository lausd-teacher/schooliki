package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.AppUserDTO;
import net.videmantay.shared.UserRoles;



@Entity
public  class AppUser  implements Serializable {

     
	@Id
    public Long id;
        
    @Index
    @Email
    public String googleId;
    
    public String name;
    
    public String firstName;
    
    public String lastName;
    
    public String imageUrl;
    @Index
    public String gradeLevel;
    
    @Index
    @Email
    public String email;
    
    @NotNull
    public boolean active;
    
    @NotNull
    public boolean firstLogin = true;
    
    @Size(min=1)
    public Set<String> roles = new HashSet<>();
    
    public Set<String> permissions = new HashSet<>();
    
    public int incidentPointsAggregate = 0;
    
    public String personalTitle;
    
    @Index
    private Date dateRegistered;

	public AppUser() {

	}
	
	

	public AppUser(String firstName, String lastName, String imageUrl, String eMail, String gradeLevel, String googleId, boolean isActive,Set<String> roles,Set<String>permissions, boolean isFirstLogin) {
		this.name = firstName + " " + lastName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = firstName + " " + lastName;
		this.imageUrl = imageUrl;
		this.email = eMail;
		this.gradeLevel = gradeLevel;
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

	public String getEmail() {
		return this.email;
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

	public void setEmail(String eMail) {
		this.email = eMail;
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



	public Set<String> getRoles() {
		return this.roles;
	}



	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}



	public void setActive(boolean isActive) {
		this.active = isActive;
	}



	public Set<String> getPermissions(){
		return this.permissions;
	}
	
	public void setPermissions(Set<String> perms){
		this.permissions = perms;
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
	
	public String getPersonalTitle(){
		return this.personalTitle;
	}
	
	public void setPersonalTitle(String title){
		this.personalTitle = title;
	}



	public int getIncidentPointsAggregate() {
		return this.incidentPointsAggregate;
	}



	public void setIncidentPointsAggregate(int incidentPointsAggregate) {
		this.incidentPointsAggregate = incidentPointsAggregate;
	}

	
	
	public boolean hasRole(UserRoles role){
		return this.roles.contains(role.toString());
		
	}
	
	public Date getDateRegistered(){
		return this.dateRegistered;
	}
	
	public void setDateRegistered(Date regDate){
		this.dateRegistered = regDate;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String s: this.roles){
			sb.append(s + "/ ");
		}
		return "AppUser: id-" + this.id + "\nemail-"+ this.email + "\nfirst login-" + this.firstLogin +"\nroles: " + sb;
	}
	
	
}
