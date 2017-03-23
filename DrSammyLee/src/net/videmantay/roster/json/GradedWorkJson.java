package net.videmantay.roster.json;


import com.google.gwt.core.client.JsArrayString;

import net.videmantay.student.json.EventJson;

public class GradedWorkJson extends AssignmentJson {

	protected GradedWorkJson(){
	}
	
	public final native Long getRosterId()/*-{
			return this.rosterId;
		}-*/;
	
	public final native GradedWorkJson setRosterId(Long rosterId)/*-{
			this.rosterId = rosterId;
			return this;
	}-*/;
	public final native String  getEventId()/*-{
		return this.eventId;
	}-*/;


	public final native GradedWorkJson setEventId(String googleCalEventId)/*-{
		this.eventId = googleCalEventId;
		return this;
	}-*/;


	public final native String getGradedWorkType()/*-{
		return this.gradedWorkType;
	}-*/;


	public final native String setGradedWorkType(String gradedWorkType) /*-{
		this.gradedWorkType = gradedWorkType;
		return this;
	}-*/;
	
	public final native double getPointsPossible()/*-{
		return this.pointsPossible;
	}-*/;
	
	public final native GradedWorkJson setPointsPossible(double pointsPossible)/*-{
		this.pointsPossible = pointsPossible;
		return this;
	}-*/;


	public final native String getLang() /*-{
		return this.lang;
	}-*/;


	public final native String setLang(String lang) /*-{
		this.lang = lang;
		return this;
	}-*/;


	public final native String getAssignedDate() /*-{
		return this.assignedDate;
	}-*/;


	public final native void setAssignedDate(String assignedDate) /*-{
		this.assignedDate = assignedDate;
	}-*/;
	
	public final native String getDueDate()/*-{
		return this.dueDate;
	}-*/;
	
	public final native void setDueDate(String date)/*-{
	         this.dueDate = date;
	}-*/;
	
	public final native Boolean isFinishedGrading()/*-{
		return this.finishedGrading;
	}-*/;
	
	public final native void setFinishedGrading(Boolean status)/*-{
		this.finishedGrading = status;
	}-*/;


	public final native EventJson getEvent() /*-{
		return this.event;
	}-*/;


	public final native void setEvent(EventJson event) /*-{
		this.event = event;
	}-*/;
	
	public final native JsArrayString getAssignedTo()/*-{
		return this.assignTo;
	}-*/;
	
	public final native void setAssignedTo(JsArrayString assignedTo)/*-{
		this.assignedTo = assignedTo;
	}-*/;

}
