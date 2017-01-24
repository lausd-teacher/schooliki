package net.videmantay.roster.classtime.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class TimeSlotJson extends JavaScriptObject {

	protected TimeSlotJson(){}
	
	public native final String getSlotDuration()/*-{
		return this.slotDuration;
	}-*/;
	
	public native final String getStartTime()/*-{
			return this.startTime;
	}-*/;
	public native final JsArray<SlotJson> getSlots()/*-{
		return this.students;
	}-*/;
	
	public native final TimeSlotJson setSlotDuration (String duration)/*-{
		this.slotDuration = duration;
		return this;
	}-*/;
	
	public native final TimeSlotJson setStartTime(String start)/*-{
		this.startTime = start;
		return this;
	}-*/;
	
	public native final TimeSlotJson setSlots(JsArray<SlotJson> slots)/*-{
				this.slots = slots;
				return this;
	}-*/;
}
