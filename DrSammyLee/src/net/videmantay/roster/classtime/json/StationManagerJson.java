package net.videmantay.roster.classtime.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;



public class StationManagerJson extends JavaScriptObject {
	protected StationManagerJson(){}
	
	public native final  Long getId()/*-{
		return this.id;
	}-*/;
	public native final String getStationDuration()/*-{
		return this.stationDuration;
	}-*/;
	public native final String getTransitionTime()/*-{
		return this.transitionTime;
	}-*/;
	
	public native final JsArray<TimeSlotJson> getTimeSlots()/*-{
		return this.timeSlots;
	}-*/;

	public native final JsArray<StationJson> stations()/*-{
		return this.stations;
	}-*/;
	
	
	public native final StationManagerJson setStationDuration(String duration)/*-{
		this.stationDuration = duration;
		return this;
	}-*/;
	public native final StationManagerJson setTransitionTime(String time)/*-{
		this.transitionTime = time;
	return this;
}-*/;
	public native final StationManagerJson setTimeSlots(JsArray<TimeSlotJson> slots)/*-{
		this.timeSlots = slots;
	return this;
}-*/;
	
}
