package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class IncidentJson extends JavaScriptObject {

	protected IncidentJson(){}
	
	public final native int getValue()/*-{
		return this.value;
	}-*/;
	
	public final native String getIconUrl()/*-{
	return this.iconUrl;
	}-*/;
	
	public final native String getName()/*-{
	return this.name;
	}-*/;
	
	public final native int getId()/*-{
	return this.id;
}-*/;
	
	public final native Long getRosterId()/*-{
	      return this.rosterId;
     }-*/;
	
	public final native IncidentJson setValue(int value)/*-{
		this.value = value;
		return this;
	}-*/;
	
	public final native IncidentJson setName(String name)/*-{
			this.name = name;
			return this;
	}-*/;
	
	public final native IncidentJson setIconUrl(String iconUrl)/*-{
		this.iconUrl = iconUrl;
		return this;
	}-*/;
	
	public final native IncidentJson setId(int id)/*-{
		this.id = id;
		return this;
	}-*/;
	
	public final native void setRosterId(Long rostId)/*-{
          this.rosterId = rostId;
     }-*/;
	
	public final native String getBehaviorType()/*-{
			return this.type;
	}-*/;
	
	public final native IncidentJson setBehaviorType(String type)/*-{
			this.type = type;
			return this;
	}-*/;
	
}
