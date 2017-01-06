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
import org.joda.time.DateTime;

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
	public String startDate;
	
	@NotNull
	public String endDate;
	
	public String color = "red darken-2";
	
	@SafeHtml
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
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getColor(){
		return color;
	}
	
	
}
