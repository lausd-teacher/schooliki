package net.videmantay.roster.classtime.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.shared.json.EventJson;
import net.videmantay.student.json.StudentGroupJson;

public class ClassTimeJson extends JavaScriptObject {

	protected ClassTimeJson(){}
	
	//title
			//description of proceedures
			//list of groups and  sections
			// a one to one with a seatingChart
			//durations in secs or minutes how long it should be on that time???
			//timer's good for section rotation

	
	public final native Long getId()/*-{
	return this.id;
}-*/;

public final native void setId(Long id)/*-{
	 this.id = id;
}-*/;
public final native String getTitle()/*-{
	return this.title;
}-*/;

public final native void setTitle(String title)/*-{
	 this.title = title;
}-*/;

public final native String getDescript()/*-{
		return this.descript;
}-*/;

public final native void setDescript(String descript)/*-{
	 this.descript = descript;
}-*/;

public final native JsArray<StudentGroupJson> getGroups()/*-{
return this.groups;
}-*/;

public final native ClassTimeJson setGroups(JsArray<StudentGroupJson> groups)/*-{
this.groups = groups;
}-*/;

public final native JsArray<StationJson> getStations()/*-{
return this.stations;
}-*/;

public final native ClassTimeJson setStations(JsArray<StationJson> stations)/*-{
this.stations = stations;
}-*/;

public final native JsArray<StationRotationJson> getStaionRotation()/*-{
return this.stationRotation;
}-*/;

public final native ClassTimeJson setStationRotation(JsArray<StationRotationJson> stationRotation)/*-{
this.stationRotation = stationRotation;
}-*/;

public final native JsArrayString getProcedures()/*-{
return this.procedures;
}-*/;

public final native ClassTimeJson setProcedures(JsArrayString procedures)/*-{
this.procedures = procedures;
}-*/;

public final native boolean getIsDefault()/*-{
return this.isDefault;
}-*/;

public final native void setIsDefault(boolean isDefault)/*-{
 		this.isDefault = isDefault;
}-*/;
		
}
