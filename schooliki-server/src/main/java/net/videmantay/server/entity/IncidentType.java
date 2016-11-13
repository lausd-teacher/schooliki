package net.videmantay.server.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import net.videmantay.rest.dto.IncidentTypeDTO;

@Entity
public class IncidentType {
	
	@Id
    Long id;
	String name;
	String imageUrl;
	
	public IncidentType() {

	}

	public IncidentType(Long id, String name, String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	public static IncidentType createFromDTO(IncidentTypeDTO incidentTypeDTO) {
		IncidentType incidentType = new IncidentType();
		incidentType.id = incidentTypeDTO.getId();
		incidentType.name = incidentTypeDTO.getName();
		incidentType.imageUrl = incidentTypeDTO.getImageUrl();
		return incidentType;
	}



}
