package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.routine.json.RoutineConfigJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterConfigJson extends JavaScriptObject {

	protected RosterConfigJson(){}
	
	public final native JsArray<RosterStudentJson> getStudents()/*-{
		return this.students;
	}-*/;
	
	public final native JsArray<RoutineJson> getClassTimes()/*-{
			return this.classtimes;
	}-*/;
	
	public final native RoutineConfigJson getDefaultTime()/*-{
		return this.defaultTime;
	}-*/;
}
