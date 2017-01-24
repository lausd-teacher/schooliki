package net.videmantay.roster.classtime.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;


public class SlotJson extends JavaScriptObject {
	protected SlotJson(){}
	
	public final native int getStation()/*-{
		return this.stationNum;
	}-*/;
	public final native JsArrayString getStudents()/*-{
		return this.studentIds;
	}-*/;
	
	public native final SlotJson setStatonNum(int num)/*-{
		this.stationNum = num;
	return this;
}-*/;
	
	public native final SlotJson setStudentIds(JsArrayString ids)/*-{
		this.studentIds = ids;
	return this;
}-*/;

}
