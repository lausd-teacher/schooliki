package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class IncidentJson extends JavaScriptObject {

	protected IncidentJson(){}
	
	public final native Integer getValue()/*-{
		return this.value;
	}-*/;
	
	public final native String getIconUrl()/*-{
	return this.iconUrl;
	}-*/;
	
	public final native String getName()/*-{
	return this.name;
	}-*/;
	
	public final native void setValue(Integer value)/*-{
		this.value = value;
	}-*/;
	
	public final native void setName(String name)/*-{
			this.name = name;
	}-*/;
	
	public final native void setIconUrl(String iconUrl)/*-{
		this.iconUrl = iconUrl;
	}-*/;
}
