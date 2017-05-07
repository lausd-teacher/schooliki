package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class RosterConfig  implements Serializable{
	
	public Set<RosterStudent> students = new HashSet<>();
	
	public Set<Routine> classtimes = new HashSet<>();

	public Set<Incident>incidents= new HashSet<>();
	
	public RoutineConfig defaultTime;
	
	public Attendance attendance;
	
}
