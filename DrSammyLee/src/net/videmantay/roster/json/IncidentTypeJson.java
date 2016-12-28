package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class IncidentTypeJson extends JavaScriptObject {
	
	
	protected IncidentTypeJson(){
		
	}
	
	

public final native String getId() /*-{
	return this.id;
}-*/;
public final native void setId(String id) /*-{
	this.id = id;
}-*/;
public final native String getName() /*-{
	return this.name;
}-*/;
public final native void setName(String name) /*-{
	this.name = name;
}-*/;
public final native String getImageUrl() /*-{
	return this.imageUrl;
}-*/;
public final native void setImageUrl(String imageUrl) /*-{
	this.imageUrl = imageUrl;
}-*/;

public final native int getPoints() /*-{
   return this.points;
}-*/;
public final native void setPoints(int incidentPoints) /*-{
   this.points = incidentPoints;
}-*/;
	

}
