package net.videmantay.server.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.rest.dto.ScheduleDTO;
import net.videmantay.rest.dto.ScheduleItemDTO;

@Entity
public class Schedule {
	
	@Id
	public Long id;
	
	@Parent
	public Key<Roster> parent;
	
	@Serialize
	public List<ScheduleItem> items;
	
	public static Schedule fromDTO(ScheduleDTO dto){
		Schedule s = new Schedule();
		s.id = dto.id;
		List<ScheduleItem> sItems = new ArrayList<ScheduleItem>();
		for(ScheduleItemDTO item : dto.items){
			sItems.add(ScheduleItem.fromDTO(item));
		}
		
		s.items = sItems;
		
		return s;
		
	}

}
