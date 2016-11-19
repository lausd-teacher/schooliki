package net.videmantay.server.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.IncidentTypeDTO;
import net.videmantay.rest.dto.StudentRollDTO;

@Entity
public class StudentRoll {
	
	@Id
	Long id;
	
	Date date;
	
	@Index
	Long studentId;
	
	boolean isPresent;

	public StudentRoll() {
	
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
	
	public static StudentRoll createFromDTO(StudentRollDTO studentRollDTO) {
		StudentRoll studentRoll = new StudentRoll();
		studentRoll.id = studentRollDTO.getId();
		studentRoll.date = studentRollDTO.getDate();
		studentRoll.studentId = studentRollDTO.getStudentId();
		studentRoll.isPresent = studentRollDTO.isPresent();
		return studentRoll;
	}
}
