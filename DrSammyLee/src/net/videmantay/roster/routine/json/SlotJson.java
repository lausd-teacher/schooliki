package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;


public class SlotJson extends JavaScriptObject {
	protected SlotJson(){}
	
	public final native int getStationNum()/*-{
		return this.stationNum;
	}-*/;
	public final native JsArrayString getStudentIds()/*-{
		return this.studentIds;
	}-*/;
	
	public native final SlotJson setStationNum(int num)/*-{
		this.stationNum = num;
	return this;
}-*/;
	
	public native final SlotJson setStudentIds(JsArrayString ids)/*-{
		this.studentIds = ids;
	return this;
}-*/;
	
	public native final Long GroupId()/*-{
		return this.groupId;
	}-*/;
	
	public native final SlotJson setGroupId(Long groupId)/*-{
		this.groupId = groupId;
		return this;
	}-*/;
	

}
