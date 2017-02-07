package net.videmantay.server.entity;

public class FullRoutine {

	public Routine routine;
	public RoutineConfig routineConfig;
	
	public FullRoutine(Routine routine, RoutineConfig config){
		this.routine = routine;
		this.routineConfig = config;
	}
	
	public FullRoutine(){}
}
