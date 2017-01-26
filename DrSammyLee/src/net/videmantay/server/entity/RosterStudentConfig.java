package net.videmantay.server.entity;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class RosterStudentConfig {
	@Id
	public String studentAcct;
	
	public String eDate;
	public Boolean glasses;
	public String eldLevel;
	public String readingLevel;
	public String homeLang;
	public Set<String> modifications = new HashSet<>();
	public Boolean IEP;
	public String summary;
	
}
