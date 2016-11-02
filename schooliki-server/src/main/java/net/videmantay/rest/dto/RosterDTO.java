package net.videmantay.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import net.videmantay.server.entity.GoogleService;

import net.videmantay.server.entity.TeacherInfo;

import net.videmantay.server.user.Roster;

import net.videmantay.shared.GradeLevel;

public class RosterDTO implements Serializable {

	public static final long serialVersionUID = 1L;

	public Long id;

	public String ownerId;

	public String title;

	public String description;

	public String roomNum;

	public TeacherInfo teacherInfo;

	public GradeLevel gradeLevel;

	public String startDate;

	public String endDate;

	public String rollBook;

	public String gradeBook;

	public String behaviorReport;

	public List<GoogleService> googleCalendars = new ArrayList<GoogleService>();

	public List<GoogleService> googleTasks = new ArrayList<GoogleService>();

	public List<GoogleService> googleFolders = new ArrayList<GoogleService>();


	public RosterDTO() {

	}

	public RosterDTO(Roster roster) {
		
		this.id = roster.id;
		this.ownerId = roster.ownerId;
		this.title = roster.title;
		this.description = roster.description;
		this.roomNum = roster.roomNum;
		this.teacherInfo = roster.teacherInfo;
		this.gradeLevel = roster.gradeLevel;
		this.startDate = roster.startDate.toString();
		this.endDate = roster.endDate.toString();
		this.rollBook = roster.rollBook;
		this.gradeBook = roster.gradeBook;
		this.behaviorReport = roster.behaviorReport;
		this.googleCalendars = roster.googleCalendars;
		this.googleTasks = roster.googleTasks;
		this.googleFolders = roster.googleFolders;
	}

	public Long getId() {
		return id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public GradeLevel getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(GradeLevel grdLvl) {
		this.gradeLevel = grdLvl;
	}

	public List<GoogleService> getGoogleFolders() {
		return this.googleFolders;
	}

	public void setGoogleFolders(List<GoogleService> folders) {
		this.googleFolders = folders;

	}

	public List<GoogleService> getGoogleCalendars() {
		return this.googleCalendars;
	}

	public void setGoogleCalendars(List<GoogleService> googleCals) {
		this.googleCalendars = googleCals;
	}

	public List<GoogleService> getGoogleTasks() {
		return this.googleTasks;
	}

	public void setGoogleTasks(List<GoogleService> googleTasks) {
		this.googleTasks = googleTasks;
	}


	public String getRollBook() {
		return rollBook;
	}

	public void setRollBook(String rollBook) {
		this.rollBook = rollBook;
	}

	public String getGradeBook() {
		return gradeBook;
	}

	public void setGradeBook(String gradeBook) {
		this.gradeBook = gradeBook;
	}

	public String getBehaviorReport() {
		return behaviorReport;
	}

	public void setBehaviorReport(String behaviorReport) {
		this.behaviorReport = behaviorReport;
	}


}
