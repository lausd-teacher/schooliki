package net.videmantay.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import net.videmantay.server.entity.Schedule;
import net.videmantay.server.entity.ScheduleItem;

@JsonIgnoreProperties(ignoreUnknown = true)
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
