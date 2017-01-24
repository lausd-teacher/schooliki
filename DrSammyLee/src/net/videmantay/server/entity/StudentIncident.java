package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class StudentIncident implements Serializable {

	private static final long serialVersionUID = -8576611663190914147L;
	
	@Id
	public Long id;
	
	@Parent
	public Key<RosterStudent> parent;
	
	@Index
	public String studentAcct;
	
	@Index
	public Long incidentId;
	
	@Index
	public String date;
	
	public String summary;

	public String imageUrl;

	public int points;
	
	public Long getId() {
		return this.id;
	}



	public Long getIncidentId() {
		return this.incidentId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}



	public Key<RosterStudent> getParent() {
		return parent;
	}



	public void setParent(Key<RosterStudent> parent) {
		this.parent = parent;
	}



	public String getStudentAcct() {
		return studentAcct;
	}



	public void setStudentAcct(String studentAcct) {
		this.studentAcct = studentAcct;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
