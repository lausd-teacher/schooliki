package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.shared.GradeLevel;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentGroupJson;
import net.videmantay.student.json.StudentJobJson;
import net.videmantay.student.json.TeacherInfoJson;

public class RosterJson extends JavaScriptObject {

	protected RosterJson(){}

	public final native Long getId()/*-{
		return this.id;
	}-*/;
	
	public final native void setId(Long id)/*-{
	return this.id = id;
    }-*/;

	public final native String getOwnerId()/*-{
		return this.ownerId;
	}-*/;

	public final native RosterJson setOwnerId(String ownerId)/*-{
		this.ownerId = ownerId;
		return this;
	}-*/;

	public final native String getStartDate()/*-{
		return this.startDate;
	}-*/;

	public final native RosterJson setStartDate(String startDate)/*-{
		this.startDate = startDate;
		return this;
	}-*/;

	public final native String getEndDate()/*-{
		return this.endDate;
	}-*/;

	public final native RosterJson setEndDate(String endDate)/*-{
		this.endDate = endDate;
		return this;
	}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native RosterJson setTitle(String title)/*-{
		this.title = title;
		return this;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native RosterJson setDescription(String description)/*-{
		this.description = description;
		return this;
	}-*/;
	
	public final native JsArrayString getGradeLevels()/*-{
		return this.gradeLevels;
	}-*/;
	
	public final native void setGradeLevels(JsArrayString levels)/*-{
		this.gradeLevels = levels;
	}-*/;

	public final native  String getRoomNum()/*-{
		return this.roomNum;
	}-*/;

	public final native RosterJson setRoomNum(String roomNum)/*-{
		this.roomNum = roomNum;
		return this;
	}-*/;
	
	public final native String getColor()/*-{
		return this.color;
	}-*/;
	
	public final native RosterJson setColor(String color)/*-{
		this.color = color;
	}-*/;
	
}
