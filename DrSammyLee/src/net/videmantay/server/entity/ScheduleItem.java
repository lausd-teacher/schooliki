package net.videmantay.server.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

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
	
}
