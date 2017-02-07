package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class RoutineJson extends JavaScriptObject {

	protected RoutineJson(){}
	
	

	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native Long getRosterId() /*-{
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

	public final native void setId(Long id) /*-{
		this.id = id;
	}-*/;

	public final native void setRosterId(Long rosterId) /*-{
		this.rosterId = rosterId;
	}-*/;

	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;

	public final native void setDescript(String descript) /*-{
		this.descript = descript;
	}-*/;

	
	public final native void setIsDefault(boolean isDefault) /*-{
	   this.isDefault = isDefault;
     }-*/;
	
	public final native boolean getIsDefault() /*-{
	return this.isDefault;
	}-*/;
	
	public final native JsArray<GroupJson> getGroup()/*-{
		return this.group;
	}-*/;
	
	public final native JsArrayString getProcedures()/*-{
	return this.procedures;
	}-*/;
	
	public final native void setProcedures(JsArrayString proc)/*-{
		this.procedures = proc;
	}-*/;
	
	

}
