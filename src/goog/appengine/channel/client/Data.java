package goog.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Data extends JavaScriptObject {

	protected Data(){};
	
	//Use data to parse into object according to type
	public final native String getData()/*-{
		return this.data;
	}-*/;
	
	public final native String type()/*-{
		return this.type;
	}-*/;
}
