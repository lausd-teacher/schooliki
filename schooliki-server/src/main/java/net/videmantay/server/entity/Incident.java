package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import net.videmantay.shared.BehaviorType;

@Cache
@Entity
public class Incident implements Serializable {

	private static final long serialVersionUID = -2538747210938445430L;

	@Id
	public int id;
	public int rosterId;
	public String name;
	public String iconUrl;
	public Integer value;
	public BehaviorType type = BehaviorType.INCIDENTAL;
	
	
	public Incident() {
		
	}
	public int getId() {
		return this.id;
	}
	public int getRosterId() {
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
	public void setId(int id) {
		this.id = id;
	}
	public void setRosterId(int rosterId) {
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
