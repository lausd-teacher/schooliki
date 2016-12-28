package net.videmantay.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import net.videmantay.server.entity.GoogleService;
import net.videmantay.server.entity.Roster;
import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.shared.GradeLevel;

public class RosterDTO implements Serializable {

	public static final long serialVersionUID = 1L;

	public Long id;

	public String ownerId;

	public String title;

	public String description;

	public String roomNum;


	public String startDate;

	public String endDate;


	public RosterDTO() {

	}

	public RosterDTO(Roster roster) {
		this.id = roster.id;
		this.ownerId = roster.ownerId;
		this.title = roster.title;
		this.description = roster.description;
		this.roomNum = roster.roomNum;
		this.startDate = roster.startDate.toString();
		this.endDate = roster.endDate.toString();
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

}
