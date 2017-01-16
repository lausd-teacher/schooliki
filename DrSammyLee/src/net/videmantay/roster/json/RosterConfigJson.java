package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.classtime.json.ClassTimeConfigJson;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterConfigJson extends JavaScriptObject {

	protected RosterConfigJson(){}
	
	public final native JsArray<RosterStudentJson> getStudents()/*-{
		return this.students;
	}-*/;
	
	public final native JsArray<ClassTimeJson> getClassTimes()/*-{
			return this.classtimes;
	}-*/;
	
	public final native ClassTimeConfigJson getDefaultTime()/*-{
		return this.defaultTime;
	}-*/;
}
