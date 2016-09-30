package goog.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Channel extends JavaScriptObject {
	//channel is init on page load we just need to call an open to get a socket
	protected Channel(){};
	
	
	//static method because we just need to get to it
	public static native final  Socket open()/*-{
		console.log('from native window the log shows channel as ');
		console.log($wnd);
		return $wnd.channel.open();
	}-*/;
	

}
