package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.rest.dto.RosterStudentDTO;
import net.videmantay.server.validation.ValidDateOfBirth;
import net.videmantay.server.validation.ValidationMessages;


@Entity
public class RosterStudent implements Serializable {
	
	@Id
	public Long id;
	///Key to roster detail not parent roster
	//why not set actual key???
	@Index
	Long studentId;
	
	@Index
	Long rosterId;
	
	
	
	public RosterStudent(){
		
		
	}
	
	

	public RosterStudent(Long studentId, Long rosterId) {
		super();
		this.studentId = studentId;
		this.rosterId = rosterId;
	}
	
	
	public static RosterStudent createFromDTO(RosterStudentDTO dto){
		RosterStudent student = new RosterStudent();
		student.id = dto.id;
		student.rosterId = dto.getRosterId();
		student.studentId = dto.getStudentId();
		
		return student;
	}



	public Long getId() {
		return this.id;
	}



	public Long getStudentId() {
		return this.studentId;
	}



	public Long getRosterId() {
		return this.rosterId;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}



	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	
	
}
