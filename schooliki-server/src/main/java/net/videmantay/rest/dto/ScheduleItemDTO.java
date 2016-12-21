package net.videmantay.rest.dto;

import net.videmantay.server.entity.ScheduleItem;

public class ScheduleItemDTO {
	public ScheduleItemDTO(){}
	public ScheduleItemDTO(ScheduleItem si) {
		this.id = si.id;
		this.title = si.title;
		this.start = si.start;
		this.end = si.end;
		this.constraint = si.constraint;
		this.bacgroundColor = si.bacgroundColor;
		this.borderColor = si.borderColor;
		this.className = si.className;
		this.textColor = si.textColor;
		this.color = si.color;
	}
	public String id;
	public String title;
	public String start;
	public String end;
	public String constraint;
	
	public String[] className;
	public String color;
	public String textColor;
	
	//optional
	public String bacgroundColor;
	public String borderColor;
}
