package net.videmantay.roster.routine.json;

import com.google.gwt.core.client.JavaScriptObject;

public class FullRoutineJson extends JavaScriptObject {
	protected FullRoutineJson(){}
	public final native RoutineJson getRoutine()/*-{
			return this.routine;
	}-*/;
	
	public final native RoutineConfigJson getRoutineConfig()/*-{
	return this.routineConfig;
	}-*/; 
	
	public final native FullRoutineJson setRoutine(RoutineJson routine)/*-{
		this.routine = routine;
		return this;
	}-*/;
	
	public final native FullRoutineJson setRoutineConfig (RoutineConfigJson config)/*-{
	this.routineConfig = config;
	return this;
}-*/;
	
	public final void copy(FullRoutineJson data){
		this.setRoutine(data.getRoutine());
		this.setRoutineConfig(data.getRoutineConfig());
	}

}
