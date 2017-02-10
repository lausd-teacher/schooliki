package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.student.json.RosterStudentJson;

public class GroupRoleJson extends JavaScriptObject{
	
	protected GroupRoleJson(){}
	public final native JsArray<RosterStudentJson> getStudents()/*-{
		return this.students;
	}-*/;
	
	public final native String getTitle()/*-{
	return this.title;
}-*/;
	
	public final native String getDescription()/*-{
	return this.description;
}-*/;
	
	public final native GroupRoleJson setStudents(JsArray<RosterStudentJson> students)/*-{
	 this.students = students;
	 return this;
}-*/;
	
	public final native GroupRoleJson setTitle(String title)/*-{
	 this.title = title;
	 return this;
}-*/;
	
	public final native GroupRoleJson setDescription(String description)/*-{
	 this.description = description;
	 return this;
}-*/;
	
	

}
