package net.videmantay.roster.classtime.json;

import com.google.gwt.core.client.JavaScriptObject;

public class ClassTimeJson extends JavaScriptObject {

	protected ClassTimeJson(){}
	
	

	public final native String getId()/*-{
		return this.id;
	}-*/;

	public final native String getRosterId() /*-{
		return this.rosterId;
	}-*/;

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native String getDescript() /*-{
		return this.descript;
	}-*/;

	public final native String getLastUpdate() /*-{
		return this.lastUpdate;
	}-*/;

	public final native String getStartTime() /*-{
		return this.startTime;
	}-*/;

	public final native String getEndTime() /*-{
		return this.endTime;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;

	public final native void setRosterId(String rosterId) /*-{
		this.rosterId = rosterId;
	}-*/;

	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;

	public final native void setDescript(String descript) /*-{
		this.descript = descript;
	}-*/;

	public final native void setLastUpdate(String lastUpdate) /*-{
		this.lastUpdate = lastUpdate;
	}-*/;

	public final native void setStartTime(String startTime) /*-{
		this.startTime = startTime;
	}-*/;

	public final native void setEndTime(String endTime) /*-{
		this.endTime = endTime;
	}-*/;	
	
	public final native void setIsDefault(boolean isDefault) /*-{
	   this.isDefault = isDefault;
     }-*/;
	
	public final native String getIsDefault() /*-{
	return this.isDefault;
}-*/;
	
	
	
}
