package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JoinRequestJson extends JavaScriptObject {

	protected JoinRequestJson(){}
	
	public final native String getPicUrl()/*-{
		return this.picUrl;
	}-*/;
	public final native String getEmail()/*-{
			return this.email;
	}-*/;
	public final native JoinRequestJson setPicUrl(String pic)/*-{
		this.picUrl = pic;
		return this;
	}-*/;
	public final native JoinRequestJson setEmail(String email)/*-{
			this.email = email;
			return this;
	}-*/; 
	
	public final native String getStatus()/*-{
		return this.status;
	}-*/;
	
	public final native JoinRequestJson setStatus(String stat)/*-{
		this.status = stat;
	}-*/;
}
