package net.videmantay.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;


import net.videmantay.server.entity.Goal;
import net.videmantay.server.entity.StudentJob;
import net.videmantay.server.user.RosterStudent;
import net.videmantay.server.user.Thumbnail;



public class RosterStudentDTO implements Serializable {

	public Long id;

	public Long parentRosterId;

	public String firstName;

	public String lastName;
	public String extName;
	public Thumbnail[] thumbnails;

	public Date DOB;
	public Boolean glasses;
	public String eldLevel;
	public Set<StudentJob> jobs;
	public boolean inactive = false;

	public String acctId;

	public ArrayList<Integer> points;

	public Set<Goal> goals;

	public String studentFolderId;
	public String studentCalId;
	public String recordUrl;
	public String studentTasksId;

	public RosterStudentDTO() {

	}

	public RosterStudentDTO(RosterStudent rosterStudent) {

		this.id = rosterStudent.id;
		this.parentRosterId = rosterStudent.parentRosterId;
		this.firstName = rosterStudent.firstName;
		this.lastName = rosterStudent.lastName;
		this.extName = rosterStudent.extName;
		this.thumbnails = rosterStudent.thumbnails;
		DOB = rosterStudent.DOB;
		this.glasses = rosterStudent.glasses;
		this.eldLevel = rosterStudent.eldLevel;
		this.jobs = rosterStudent.jobs;
		this.inactive = rosterStudent.inactive;
		this.acctId = rosterStudent.acctId;
		this.points = rosterStudent.points;
		this.goals = rosterStudent.goals;
		this.studentFolderId = rosterStudent.studentFolderId;
		this.studentCalId = rosterStudent.studentCalId;
		this.recordUrl = rosterStudent.recordUrl;
		this.studentTasksId = rosterStudent.studentTasksId;
	}

	public Long getRoster() {
		return parentRosterId;
	}

	public void setRoster(Long roster) {
		this.parentRosterId = roster;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getGlasses() {
		return glasses;
	}

	public void setGlasses(Boolean glasses) {
		this.glasses = glasses;
	}

	public String getEldLevel() {
		return eldLevel;
	}

	public void setEldLevel(String eldLevel) {
		this.eldLevel = eldLevel;
	}

	public Set<StudentJob> getJobs() {
		return jobs;
	}

	public void setJobs(Set<StudentJob> jobs) {
		this.jobs = jobs;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExtName() {
		return this.extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public void setPicUrl(Thumbnail[] thumbnails) {
		this.thumbnails = thumbnails;
	}

	public Thumbnail[] getThumbnails() {
		return this.thumbnails;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String studentGoogleId) {
		this.acctId = studentGoogleId;
	}

	public Set<Goal> getBadges() {
		return goals;
	}

	public void setBadges(Set<Goal> goals) {
		this.goals = goals;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentFolderId() {
		return studentFolderId;
	}

	public void setStudentFolderId(String studentFolderId) {
		this.studentFolderId = studentFolderId;
	}

	public String getStudentCalId() {
		return studentCalId;
	}

	public void setStudentCalId(String studentCalId) {
		this.studentCalId = studentCalId;
	}

	public String getStudentTasksId() {
		return studentTasksId;
	}

	public String getRecordUrl() {
		return this.recordUrl;
	}

	public void setRecordUrl(String url) {
		this.recordUrl = url;
	}

	public void setStudentTasksId(String studentTasksId) {
		this.studentTasksId = studentTasksId;
	}

	public Boolean getInactive() {
		return inactive;
	}

	public void setInactive(Boolean inactive) {
		this.inactive = inactive;
	}

}
