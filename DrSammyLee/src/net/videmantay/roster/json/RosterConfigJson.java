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
	
	public final native Long getDefaultTimeId()/*-{
		return this.defaultTime;
	}-*/;
	
	public final native AttendanceJson getAttendance()/*-{
				return this.attendance;
	}-*/;
	
	public final native RoutineConfigJson getDefaultTime()/*-{
			return this.defaultTime;
	}-*/;
	
	
	
	public final native RosterConfigJson setAttendance(AttendanceJson attendance)/*-{
	this.attendance = attendance;
}-*/;
	
	public final native RosterConfigJson setDefaultTimeId(Long defaultId)/*-{
			this.defaultTime = defaultId;
			return this;
	}-*/;
	
	public final native RosterConfigJson setDefaultTime(RoutineConfigJson defaultTime)/*-{
	this.defaultTime = defaultTime;
	return this;
}-*/;
	
	public final native RosterConfigJson setClassTimes(JsArray<RoutineJson> times)/*-{
	this.classtimes = times;
	return this;
}-*/;
	
	public final native RosterConfigJson setStudents(JsArray<RosterStudentJson> students)/*-{
	this.students = students;
	return this;
}-*/;
}
