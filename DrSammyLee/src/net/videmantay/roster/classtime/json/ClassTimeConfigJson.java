package net.videmantay.roster.classtime.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ClassTimeConfigJson extends JavaScriptObject {

	protected ClassTimeConfigJson(){}
	
	public final native JsArray<GroupJson> getGroups()/*-{
	return this.groups;
}-*/;
	
	public final native JsArray<ProcedureJson> getProcedures()/*-{
	return this.procedures;
}-*/;
	
	public final native void setGroups(JsArray<GroupJson> groups)/*-{
	return this.groups = groups;
}-*/;
	
	public final native void setProcedures(JsArray<ProcedureJson> procs)/*-{
	return this.procedures = procs;
}-*/;
}
