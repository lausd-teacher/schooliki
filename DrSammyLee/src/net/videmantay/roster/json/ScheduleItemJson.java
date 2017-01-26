package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class ScheduleItemJson extends JavaScriptObject {

	public final native String getId()/*-{
		return this.id;
		}-*/;
	public final native String  getTitle()/*-{
			return this.title;
	}-*/;
	public final native String getStart()/*-{
		return this.start;
	}-*/;
	public final native String getEnd()/*-{
		return this.end
		}-*/;
	public final native String getConstraint()/*-{
		return this.constraint
		}-*/;
	
	public final native JsArrayString getClassNames()/*-{
	 return this.classNames
	 }-*/;
	public final native String getColor()/*-{
		return this.color;
		}-*/;
	public final native String getTextColor()/*-{
		return this.textColor;
		}-*/;
	
	//optional
	public final native String getBackgroundColor()/*-{
		return this.backgroundColor;
		}-*/;
	public final native String getBorderColor()/*-{
		return this.borderColor;
		}-*/;
	
	public final native ScheduleItemJson setTitle(String s)/*-{
		this.title = s;
		return this;
	}-*/;
	
	public final native ScheduleItemJson setStart(String s)/*-{
	this.start = s;
	return this;
}-*/;
	public final native ScheduleItemJson setEnd(String s)/*-{
	this.end = s;
	return this;
}-*/;
	
	public final native ScheduleItemJson setConstraint(String s)/*-{
	this.constraint = s;
	return this;
}-*/;
	public final native ScheduleItemJson setClassNames(JsArrayString s)/*-{
	this.classNames = s;
	return this;
}-*/;
	public final native ScheduleItemJson setColor(String s)/*-{
	this.color = s;
	return this;
}-*/;
	public final native ScheduleItemJson setTextColor(String s)/*-{
	this.textColor = s;
	return this;
}-*/;
	public final native ScheduleItemJson setBackgroundColor(String s)/*-{
	this.backgroundColor = s;
	return this;
}-*/;
}
