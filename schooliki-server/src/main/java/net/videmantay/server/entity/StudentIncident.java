package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.api.services.calendar.model.Event;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.shared.BehaviorType;

@Entity
public class StudentIncident implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8576611663190914147L;
	
	@Id
	public Long id;
	@Index
	public Long studentId;
	@Index
	public String date;
	public int incidentId;
	@Ignore
	public Incident incident;
	
	
}