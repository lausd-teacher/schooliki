package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class StationManager implements Serializable{

	@Id
	public Long id;
	public String stationDuration;
	public String transitionTime;
	@Serialize
	public List<TimeSlot> timeSlots = new ArrayList<>();
	@Serialize
	public List<Station> stations;
	
}
