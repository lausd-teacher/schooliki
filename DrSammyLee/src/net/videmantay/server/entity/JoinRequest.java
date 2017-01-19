package net.videmantay.server.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class JoinRequest {

	@Id
	public Long id;
	
	public String email;
	
	public String picUrl;
	
	public RequestStatus status = RequestStatus.PENDING;
	
	public enum RequestStatus{PENDING,REJECTED,ACCEPTED};
	
}
