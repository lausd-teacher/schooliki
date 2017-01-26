package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;

@Entity
public class RosterConfig  implements Serializable{
	@Id
	public Long id;
	public String joinCode;
	public transient Set<Key<Routine>> classtimeKeys = new HashSet<>();
	public transient Set<Key<Incident>>incidentKeys= new HashSet<>();
	
	@Ignore
	public Set<RosterStudent> students = new HashSet<>();
	@Ignore
	public Set<Routine> classtimes = new HashSet<>();
	@Ignore
	public Set<Incident>incidents= new HashSet<>();
	@Ignore
	public RoutineConfig defaultTime = null;
}
