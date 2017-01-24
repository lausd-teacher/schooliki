package net.videmantay.rest.dto;

import net.videmantay.server.entity.Incident;

public class IncidentTypeDTO {
	
	Long id;
	String name;
	String imageUrl;
	int points;
	
	public IncidentTypeDTO(Incident type) {
		this.id = type.getId();
		this.name = type.getName();
		this.imageUrl = type.getImageUrl();
		this.points = type.getPoints();
	}
	
	public IncidentTypeDTO() {

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

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
