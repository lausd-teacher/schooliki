package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.shared.GradeLevel;

@Cache
@Entity
public class RosterDetail implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = -8560762171107782047L;

	@Id
	public transient Long id;
		
	public String title;
	
	public String description;

	public TeacherInfo teacherInfo;
	
	public List<String> gradeLevel;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public List<String> getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(List<String> gradeLevels) {
		this.gradeLevel = gradeLevels;
	}
	
}
