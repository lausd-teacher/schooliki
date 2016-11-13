package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.IncidentDTO;
import net.videmantay.shared.BehaviorType;

@Cache
@Entity
public class Incident implements Serializable {

	private static final long serialVersionUID = -2538747210938445430L;

	@Id
	public Long id;
	
	@Index
	public Long rosterId;
	
	@Index
	public Long incidentTypeId;
	
	public String name;

	public Integer value;
	
	public BehaviorType type = BehaviorType.INCIDENTAL;
	
	
	public Incident() {
		
	}
	public Long getId() {
		return this.id;
	}
	public Long getRosterId() {
		return this.rosterId;
	}
	public String getName() {
		return this.name;
	}
	public Integer getValue() {
		return this.value;
	}
	public BehaviorType getType() {
		return this.type;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	public void setType(BehaviorType type) {
		this.type = type;
	}
	public Long getIncidentTypeId() {
		return this.incidentTypeId;
	}
	public void setIncidentTypeId(Long incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}
	public static Incident createFromDTO(IncidentDTO incidentDTO) {
		Incident incident = new Incident();
		incident.id = incidentDTO.id;
		incident.rosterId = incidentDTO.rosterId;
		incident.name = incidentDTO.name;
		incident.value = incidentDTO.value;
		incident.type = incidentDTO.type;
		
		return incident;
	}
}
