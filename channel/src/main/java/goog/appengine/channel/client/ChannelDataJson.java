package goog.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;

public class ChannelDataJson extends JavaScriptObject {

	protected ChannelDataJson(){};
	
	public final native JavaScriptObject getData()/*-{
	
		return this.data;
	}-*/;
	
}
