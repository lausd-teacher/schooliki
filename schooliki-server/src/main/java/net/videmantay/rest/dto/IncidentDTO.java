package net.videmantay.rest.dto;

import net.videmantay.server.entity.Incident;

public class IncidentDTO {
	
	public Long id;
	public Long rosterId;
	public String name;
	public Integer value;
	public Long incidentTypeId;
	
	
	public IncidentDTO(Incident incident) {
		this.id = incident.id;
		this.rosterId = incident.rosterId;
		this.name = incident.name;
		this.value = incident.value;
		this.incidentTypeId = incident.incidentTypeId;
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
	public Integer getValue() {
		return this.value;
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
	public Long getIncidentTypeId() {
		return this.incidentTypeId;
	}
	public void setIncidentTypeId(Long incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}
}
