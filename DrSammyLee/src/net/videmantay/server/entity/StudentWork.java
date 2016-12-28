package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.rest.dto.StudentWorkDTO;
import net.videmantay.server.user.RosterStudent;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StudentWorkStatus;
import net.videmantay.shared.SubjectType;

@Entity
public class StudentWork implements Serializable {

	// same id as gradedwork////
	@Id
	public Long id;

	public Double percentage;
	public Double pointsEarned;
	public String letterGrade;
	public String message;
	public GradedWorkType type;
	public StudentWorkStatus studentWorkStatus = StudentWorkStatus.NOT_TURNED_IN;

	@Index
	public String dateTaken;

	public String mediaUrl;

	public SubjectType subject;
	
	@Index
	public Long rosterStudentId;

	// List of standard to review with accomany links;
	private List<Standard> standardReviews;

	public StudentWork() {

	}

	public String getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}

	public SubjectType getSubject() {
		return subject;
	}

	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}

	public GradedWorkType getType() {
		return type;
	}

	public void setType(GradedWorkType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Long getId() {
		return id;
	}

	public Double getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Double pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public StudentWorkStatus getStudentWorkStatus() {
		return studentWorkStatus;
	}

	public void setStudentWorkStatus(StudentWorkStatus studentWorkStatus) {
		this.studentWorkStatus = studentWorkStatus;
	}

	public void setStudentWorkStatus(String value) {
		value.trim();
		this.studentWorkStatus = StudentWorkStatus.valueOf(value.toUpperCase());
	}

	public Long getRosterStudentId() {
		return this.rosterStudentId;
	}

	public void setRosterStudentId(Long rosterStudentId) {
		this.rosterStudentId = rosterStudentId;
	}

	public static StudentWork createFromDTO(StudentWorkDTO stdWorkDTO) {

		StudentWork work = new StudentWork();
		work.id = stdWorkDTO.id;
		work.rosterStudentId = stdWorkDTO.getRosterStudentId();
		work.percentage = stdWorkDTO.percentage;
		work.pointsEarned = stdWorkDTO.pointsEarned;
		work.letterGrade = stdWorkDTO.letterGrade;
		work.message = stdWorkDTO.message;
		work.type = stdWorkDTO.type;
		work.studentWorkStatus = stdWorkDTO.studentWorkStatus;
		work.dateTaken = stdWorkDTO.dateTaken;
		work.mediaUrl = stdWorkDTO.mediaUrl;
		work.subject = stdWorkDTO.subject;

		return work;

	}

}
