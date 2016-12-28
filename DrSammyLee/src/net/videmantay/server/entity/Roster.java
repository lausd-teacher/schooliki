package net.videmantay.server.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import net.videmantay.rest.dto.RosterDTO;
import net.videmantay.server.validation.ValidDateFormat;
import net.videmantay.server.validation.ValidDateRange;
import net.videmantay.shared.GradeLevel;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Serialize;

@Cache
@Entity
@ValidDateRange
public class Roster implements Serializable{
	
	@Id
	public Long id;
	
	@Index
	public transient String ownerId;
	
	@NotNull
	@SafeHtml
	public String title;
	
	@SafeHtml
	public String description;
	
	@SafeHtml
	public String roomNum;
	
	@NotNull
	public Date startDate;
	
	@NotNull
	public Date endDate;
	

	public Roster(){
		
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
	
	public static Roster createFromDTO(RosterDTO dto) throws ParseException{
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("YYYY-mm-dd");
		
		Roster roster = new Roster();
		
		roster.id = dto.id;
		roster.ownerId = dto.ownerId;
		roster.title = dto.title;
		roster.description = dto.description;
		roster.roomNum = dto.roomNum;
		roster.startDate = sDateFormat.parse(dto.startDate);
		roster.endDate = sDateFormat.parse(dto.endDate);
		
		 return roster;
	}


	
}
