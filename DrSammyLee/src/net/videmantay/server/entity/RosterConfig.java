package net.videmantay.server.entity;

import java.util.HashSet;
import java.util.Set;

public class RosterConfig {
	public Set<RosterStudent> students = new HashSet<>();
	public Set<ClassTime> classtimes = new HashSet<>();
	public ClassTimeConfig defaultTime = null;
}
