package net.videmantay.server.entity;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class Schedule {
	
	@Id
	public Long id;
	
	@Serialize
	public List<ScheduleItem> items;
	
}
