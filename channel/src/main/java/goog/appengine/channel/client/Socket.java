package goog.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

public class Socket extends JavaScriptObject {

	protected Socket(){};
	
	public final Socket onOpen(Function f){
		onOpenJs(f);
		return this;
	}
	
	public final Socket onClose(Function f){
		onCloseJs(f);
		return this;
	}
	
	public final Socket onError(Function f){
		onErrorJs(f);
		return this;
	}
	
	public final Socket onMessage(Function f){
		onMessageJs(f);
		return this;
	}
	
	public final native void close()/*-{
			this.close();
	}-*/;
		
	private final native void onOpenJs(Function f)/*-{
	this.onopen = function(){f.@com.google.gwt.query.client.Function::f()();};	
	}-*/;
	
	private final native void onCloseJs(Function f)/*-{
	this.onclose = $entry(f.@com.google.gwt.query.client.Function::f());	
	}-*/;
	
	private final native void onErrorJs(Function f)/*-{
	this.onerror = function(){f.@com.google.gwt.query.client.Function::f();};	
	}-*/;
	
	private final native void onMessageJs(Function f)/*-{
	this.onmessage = function(data){f.@com.google.gwt.query.client.Function::f([Ljava/lang/Object;)([data]);};	
	}-*/;
}
