package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class IncidentJson extends JavaScriptObject {

	protected IncidentJson(){}
	
	public final native int getValue()/*-{
		return this.value;
	}-*/;
	
	public final native String getName()/*-{
	return this.name;
	}-*/;
	
	public final native String getId()/*-{
	   return this.id;
     }-*/;
	
	public final native Long getRosterId()/*-{
	      return this.rosterId;
     }-*/;
	
	public final native void setValue(int value)/*-{
		this.value = value;
	}-*/;
	
	public final native void setName(String name)/*-{
			this.name = name;
	}-*/;
	
	
	public final native void setId(String id)/*-{
		this.id = id;
	}-*/;
	
	public final native void setRosterId(Long rostId)/*-{
          this.rosterId = rostId;
     }-*/;
	
	public final native String getIncidentTypeId()/*-{
			return this.incidentTypeId;
	}-*/;
	
	public final native void setIncidentTypeId(String typeId)/*-{
			this.incidentTypeId = typeId;
	}-*/;
	
}
