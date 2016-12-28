package net.videmantay.rest.dto;


import net.videmantay.server.entity.StudentWork;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StudentWorkStatus;
import net.videmantay.shared.SubjectType;

public class StudentWorkDTO {

	public Long id;

	public Double percentage;

	public Double pointsEarned;

	public String letterGrade;

	public String message;

	public GradedWorkType type;

	public StudentWorkStatus studentWorkStatus = StudentWorkStatus.NOT_TURNED_IN;

	public String dateTaken;

	public String mediaUrl;

	public SubjectType subject;
	
	private Long rosterStudentId;


	public StudentWorkDTO() {

	}

	public StudentWorkDTO(StudentWork stdWork) {
		this.id = stdWork.id;
		this.rosterStudentId = stdWork.rosterStudentId;
		this.percentage = stdWork.percentage;
		this.pointsEarned = stdWork.pointsEarned;
		this.letterGrade = stdWork.letterGrade;
		this.message = stdWork.message;
		this.type = stdWork.type;
		this.studentWorkStatus = stdWork.studentWorkStatus;
		this.dateTaken = stdWork.dateTaken;
		this.mediaUrl = stdWork.mediaUrl;
		this.subject = stdWork.subject;


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


	public Long getRosterStudentId() {
		return this.rosterStudentId;
	}

	public void setRosterStudentId(Long rosterStudentId) {
		this.rosterStudentId = rosterStudentId;
	}

}
