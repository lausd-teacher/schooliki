package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

import net.videmantay.shared.UserRoles;



public class AppUserJson extends JavaScriptObject {

 
		
	protected AppUserJson(){}
	
	public final  native String getId() /*-{
		return this.id;
	}-*/;

	public final  native String getName() /*-{
		return this.name;
	}-*/;

	public final  native String getImageUrl() /*-{
		return this.imageUrl;
	}-*/;

	public final  native String geteMail() /*-{
		return this.eMail;
	}-*/;

	public final  native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final  native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final  native void setImageUrl(String imageUrl) /*-{
		this.imageUrl = imageUrl;
	}-*/;

	public final  native void seteMail(String eMail) /*-{
		this.eMail = eMail;
	}-*/;

	public final  native String getGoogleId() /*-{
		return this.googleId;
	}-*/;


	public final  native void setGoogleId(String googleId) /*-{
		this.googleId = googleId;
	}-*/;



	public final  native boolean isActive() /*-{
		return this.active;
	}-*/;


	public final  native void setActive(boolean isActive) /*-{
		this.active = isActive;
	}-*/;



	public final  native String[] getRoles() /*-{
		return this.roles;
	}-*/;

	
	public final  native void addRole(String role) /*-{
		 if(!this.roles)
		    this.roles = new Array();
		    
	   this.roles.push(role);
    }-*/;


	public final  native String getPassword() /*-{
		return this.password;
	}-*/;



	public final  native void setPassword(String password) /*-{
		this.password = password;
	}-*/;



	public final  native boolean isFirstLogin() /*-{
		return this.firstLogin;
	}-*/;



	public final  native void setFirstLogin(boolean isFirstLogin) /*-{
		this.firstLogin = isFirstLogin;
	}-*/;
	
	public final  native String getFirstName() /*-{
		return this.firstName;
	}-*/;



	public final  native String getLastName() /*-{
		return this.lastName;
	}-*/;



	public final  native void setFirstName(String firstName) /*-{
		this.firstName = firstName;
	}-*/;



	public final  native void setLastName(String lastName) /*-{
		this.lastName = lastName;
	}-*/;
	
}
