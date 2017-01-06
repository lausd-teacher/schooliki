package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;

public class InfoJson extends JavaScriptObject {

	protected InfoJson(){}
	
	public final native String firstName()/*-{
		return this.firstName;
	}-*/;
	public final native String lastName()/*-{
		return this.lastName;
	}-*/;
	public final native String img()/*-{
		return this.img
	}-*/;
	public final native String token()/*-{
		return this.token;
	}-*/;
	
	public final native String logout()/*-{
		return this.logout;
	}-*/;
}
