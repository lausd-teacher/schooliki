package net.videmantay.server.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.rest.dto.ScheduleItemDTO;
import net.videmantay.server.user.Roster;


public class ScheduleItem {

	@NotNull
	@SafeHtml
	public String id;
	
	@NotNull
	@SafeHtml
	public String title;
	
	//verify date string
	
	@SafeHtml
	public String start;
	
	//verify date string
	
	@SafeHtml
	public String end;
	
	@SafeHtml
	public String constraint;
	
	@SafeHtml
	public String[] className;
	
	@SafeHtml
	public String color;
	
	@SafeHtml
	public String textColor;
	
	//optional
	@SafeHtml
	public String bacgroundColor;
	
	@SafeHtml
	public String borderColor;
	

	public static ScheduleItem fromDTO(ScheduleItemDTO dto){
		ScheduleItem si = new ScheduleItem();
		si.bacgroundColor = dto.bacgroundColor;
		si.borderColor = dto.borderColor;
		si.className = dto.className;
		si.color = dto.color;
		si.constraint = dto.constraint;
		si.end = dto.end;
		si.start = dto.start;
		si.textColor = dto.textColor;
		si.title = dto.title;
		
		return si;
	}
}
