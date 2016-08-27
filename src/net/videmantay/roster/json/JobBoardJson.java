package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

import net.videmantay.student.json.StudentJobJson;

public class JobBoardJson extends JavaScriptObject {
	
	protected JobBoardJson(){}
		
	public final native StudentJobJson[] getJobs()/*-{
		return this.jobs;
	}-*/;
	
	public final native String getStartDate()/*-{
		return this.startDate;
	}-*/;

	public final native String getEndDate()/*-{
		return this.startDate;
	}-*/;
	
	public final native JobBoardJson set(StudentJobJson[] jobs)/*-{
		this.jobs = jobs;
		return this;
	}-*/;
	
	public final native JobBoardJson setStartDate(String start)/*-{
		this.startDate = start;
		return this;
	}-*/;
	
	public final native JobBoardJson setEndDate(String end)/*-{
	this.endDate = end;
	return this;
}-*/;

}
