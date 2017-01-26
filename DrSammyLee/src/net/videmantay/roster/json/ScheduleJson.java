package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


public class ScheduleJson extends JavaScriptObject {
	protected ScheduleJson(){}
	
	JsArray<ScheduleItemJson> items;
	
}
