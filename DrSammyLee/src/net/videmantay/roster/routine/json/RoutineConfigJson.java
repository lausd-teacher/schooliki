package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class RoutineConfigJson extends JavaScriptObject {

	protected RoutineConfigJson(){}
	
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
	
	public final native Long getId()/*-{
		return this.id;
	}-*/;
	
	public final native void setId(Long id)/*-{
			this.id = id;
	}-*/;
}
