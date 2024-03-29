package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

import net.videmantay.roster.json.IncidentJson;
import net.videmantay.student.json.EventJson;
import net.videmantay.student.json.RosterStudentJson;

public class IncidentReportJson extends JavaScriptObject {

	protected IncidentReportJson(){};
	//Array dont make it across , Wierd
	public final native String getStudentIds()/*-{
			return this.studentIds;
	}-*/;
	
	public final native IncidentReportJson setStudentIds(String students)/*-{
			this.studentIds = students;
			return this;
	
	}-*/;
	
	public final native JsArray<RosterStudentJson> getStudents()/*-{
	return this.studentIds;
}-*/;

public final native IncidentReportJson setStudents(JsArray<RosterStudentJson> students)/*-{
	this.studentIds = students;
	return this;

}-*/;

public final native IncidentJson getIncident()/*-{
return this["incident"];
}-*/;

public final native IncidentReportJson setIncicdent(IncidentJson incident)/*-{
	this.incident = incident;
	return this;
}-*/;

	public final native Long getRosterId()/*-{
		return this.rosterId;
	}-*/;
	
	public final native IncidentReportJson setRosterId(Long roster)/*-{
			this.rosterId = roster;
			return this;
			
	}-*/;
	
	public final native EventJson getEvent()/*-{
			return this.event;
	}-*/;
	
	public final native IncidentReportJson setEvent(EventJson event)/*-{
		this.event = event;
		return this;
	}-*/;
	
	public final native String getActionType()/*-{
			return this.actionType;
	}-*/;
	
	public final native IncidentReportJson setActionType(String actionType)/*-{
			this.actionType = actionType;
			return this;
	}-*/;
	
}
