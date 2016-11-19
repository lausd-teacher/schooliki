package net.videmantay.rest.dto;

import java.util.Date;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.server.entity.StudentRoll;

public class StudentRollDTO {
	
	@Id
	Long id;
	
	Date date;
	
	@Index
	Long studentId;
	
	boolean isPresent;

	public StudentRollDTO(StudentRoll roll) {
		this.id = roll.getId();
		this.date = roll.getDate();
		this.studentId = roll.getStudentId();
		this.isPresent = roll.isPresent();
	}

	public Long getId() {
		return this.id;
	}

	public Date getDate() {
		return this.date;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public boolean isPresent() {
		return this.isPresent;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}
	
	
	

}
