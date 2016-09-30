package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import net.videmantay.shared.BehaviorType;


public class Incident implements Serializable {
	
	private static final long serialVersionUID = -2538747210938445430L;
	public int id;
	public String name;
	public String iconUrl;
	public Integer value;
	public BehaviorType type = BehaviorType.INCIDENTAL;
	
}