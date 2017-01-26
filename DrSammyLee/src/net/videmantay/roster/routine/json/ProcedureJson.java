package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;

public class ProcedureJson extends JavaScriptObject {

	protected ProcedureJson(){}
	
	public final native Integer getStep()/*-{
		return this.step;
	}-*/;
	
	public final native void setStep(Integer num)/*-{
	 this.step = num;
	}-*/;
	
	public final native String getProcedure()/*-{
	return this.procedure;
	}-*/;
	
	public final native void setProcedure(String proc)/*-{
	 this.procedure = proc;
}-*/;
	
}
