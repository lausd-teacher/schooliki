package net.videmantay.server.entity;

import java.util.ArrayList;
import java.util.Set;

import com.google.api.services.calendar.model.Event;

import net.videmantay.server.user.RosterStudent;

public class IncidentReport {
	public Long rosterId;
	public Long incidentId;
	public Set<Long> studentIds;
	public ArrayList<RosterStudent> students;
	public Incident incident;
	public Event event;
	
	//Whole, Multi, Single
	public String actionType;

}
