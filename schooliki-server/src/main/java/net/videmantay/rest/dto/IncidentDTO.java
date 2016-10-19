package net.videmantay.rest.dto;

import net.videmantay.server.entity.Incident;
import net.videmantay.shared.BehaviorType;

public class IncidentDTO {
	
	public Long id;
	public Long rosterId;
	public String name;
	public String iconUrl;
	public Integer value;
	public BehaviorType type = BehaviorType.INCIDENTAL;
	
	
	public IncidentDTO(Incident incident) {
		this.id = incident.id;
		this.rosterId = incident.rosterId;
		this.name = incident.name;
		this.iconUrl = incident.iconUrl;
		this.value = incident.value;
		this.type = incident.type;
	}
	
	public IncidentDTO() {
		
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
	public String getIconUrl() {
		return this.iconUrl;
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
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public void setType(BehaviorType type) {
		this.type = type;
	}
}
