package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class CalendarListJson extends JavaScriptObject {

	protected CalendarListJson() {
		
	}
	
	
	public final native JsArray<CalendarItemJson> getCalendars()/*-{
		return this.items;
	}-*/;

}
