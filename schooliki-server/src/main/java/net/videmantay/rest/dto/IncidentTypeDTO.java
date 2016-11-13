package net.videmantay.rest.dto;

import net.videmantay.server.entity.IncidentType;

public class IncidentTypeDTO {
	
	Long id;
	String name;
	String imageUrl;
	
	public IncidentTypeDTO(IncidentType type) {
		this.id = type.getId();
		this.name = type.getName();
		this.imageUrl = type.getImageUrl();
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
	
	
}
