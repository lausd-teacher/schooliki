package net.videmantay.roster;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.json.IncidentJson;
import net.videmantay.student.json.RosterStudentJson;

public class IncidentReportJson extends JavaScriptObject {

	protected IncidentReportJson(){};
	
	public final native JsArray<RosterStudentJson> getStudent()/*-{
			return this.students;
	}-*/;
	
	public final native IncidentReportJson setStudents(JsArray<RosterStudentJson> students)/*-{
			this.students = students;
			return this;
	
	}-*/;
	
	public final native IncidentJson getIncident()/*-{
		return this.incident;
	}-*/;
	
	public final native IncidentReportJson setIncicdent(IncidentJson incident)/*-{
			this.incident = incident;
	}-*/;
}
