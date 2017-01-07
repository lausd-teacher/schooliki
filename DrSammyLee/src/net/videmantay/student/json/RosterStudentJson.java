package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;


public class RosterStudentJson extends JavaScriptObject{

	protected RosterStudentJson(){}
	
	
	public final native String getId() /*-{
		return id;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final native String getStudentId() /*-{
		return this.studentId;
	}-*/;

	public final native String getRosterId() /*-{
		return this.rosterId;
	}-*/;

	public final native void setStudentId(String studentId) /*-{
		this.studentId = studentId;
	}-*/;

	public final native void setRosterId(String rosterId) /*-{
		this.rosterId = rosterId;
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

public final  native void setImageUrl(String imageUrl) /*-{
this.imageUrl = imageUrl;
}-*/;

public final  native String getImageUrl() /*-{
return this.imageUrl;
}-*/;

public final native Integer posPoint()/*-{
	return this.posPoints;
}-*/;

public final native void posPoint(Integer points)/*-{
	this.posPoints = points
}-*/;

public final native Integer negPoint()/*-{
	return this.negPoints;
}-*/;

public final native void negPoint(Integer points)/*-{
this.negPoints = points
}-*/;

}