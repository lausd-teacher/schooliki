package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;


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
}