package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class RosterStudent implements Serializable {
	
	@Id
	public String studentAcctId;
	
	@Parent
	@Index
	public transient Key<RosterDetail> rosterKey;
	
	
	public String firstName;
	public String lastName;
	public String imageUrl;
	public List<Integer> negPoints = new ArrayList<>();
	public List<Integer> posPoints = new ArrayList<>();
	
	
	
	public RosterStudent(){}
	public RosterStudent(Long rosId, AppUser user){
		this.rosterKey = Key.create(RosterDetail.class, rosId);
		if(user.firstName != null)
		this.firstName = user.firstName;
		
		if(user.lastName != null)
		this.lastName = user.lastName;
		
		if(user.getImageUrl() !=null)
		this.imageUrl = user.imageUrl; 
		
		this.studentAcctId = user.email;
	}
		
}
