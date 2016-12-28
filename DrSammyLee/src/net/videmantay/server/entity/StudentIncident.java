package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class StudentIncident implements Serializable {

	private static final long serialVersionUID = -8576611663190914147L;
	
	@Id
	public Long id;
	@Index
	public Long studentId;
	
	@Index
	public Long incidentId;

	public StudentIncident() {

	}

	public Long getId() {
		return this.id;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public Long getIncidentId() {
		return this.incidentId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
}
