package net.videmantay.rest.dto;

import java.io.Serializable;

import net.videmantay.server.user.RosterStudent;



public class RosterStudentDTO implements Serializable {

	public Long id;
	
	Long studentId;
	
	Long rosterId;


	public RosterStudentDTO() {

	}

	public RosterStudentDTO(RosterStudent rosterStudent) {
		this.id = rosterStudent.id;
		this.studentId = rosterStudent.id;
		this.rosterId = rosterStudent.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public Long getRosterId() {
		return this.rosterId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}


}
