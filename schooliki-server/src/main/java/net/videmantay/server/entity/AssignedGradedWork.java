package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

//entity used to break many to many relationship between GradedWork and StudentWork
@Entity
public class AssignedGradedWork implements Serializable {
	
	@Id
	Long id;
	
	@Index
	Long rosterId;
	@Index
	Long gradedWorkId;
	@Index
	Long studentWorkId;
	@Index
	Long rosterStudentId;

	public AssignedGradedWork(Long gradedWorkId, Long studentWorkId, Long rosterStudentId, Long rosterId) {
		this.gradedWorkId = gradedWorkId;
		this.studentWorkId = studentWorkId;
		this.rosterStudentId = rosterStudentId;
		this.rosterId = rosterId;
	}
	
	public AssignedGradedWork(){
		
		
	}

	public Long getId() {
		return this.id;
	}

	public Long getGradedWorkId() {
		return this.gradedWorkId;
	}

	public Long getStudentWorkId() {
		return this.studentWorkId;
	}

	public Long getRosterStudentId() {
		return this.rosterStudentId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGradedWorkId(Long gradedWorkId) {
		this.gradedWorkId = gradedWorkId;
	}

	public void setStudentWorkId(Long studentWorkId) {
		this.studentWorkId = studentWorkId;
	}

	public void setRosterStudentId(Long rosterStudentId) {
		this.rosterStudentId = rosterStudentId;
	}

	public Long getRosterId() {
		return this.rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	
}
