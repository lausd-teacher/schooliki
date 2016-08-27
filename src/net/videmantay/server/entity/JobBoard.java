package net.videmantay.server.entity;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class JobBoard {

	@Id
	public Long id;
	@Index
	public String startDate;
	public String endDate;
	public List<StudentJob> jobs;
}
