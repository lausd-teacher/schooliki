package net.videmantay.rest.dto;

import java.util.ArrayList;
import java.util.List;

import net.videmantay.server.entity.Schedule;
import net.videmantay.server.entity.ScheduleItem;

public class ScheduleDTO {
	public Long id;
	public List<ScheduleItemDTO> items;
	
	public ScheduleDTO(){
		
	}
	
	public ScheduleDTO(Schedule schedule){
		this.id = schedule.id;
		this.items = new ArrayList<ScheduleItemDTO>();
		for(ScheduleItem si: schedule.items){
			this.items.add(new ScheduleItemDTO(si));
		}
	}

}
