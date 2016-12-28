package net.videmantay.server.entity;

import java.util.ArrayList;
import java.util.List;

public class ClassTimeConfig {
	
	private List<SeattingChartPosition> seatingPositions;
	private List<StudentGroup> studentGroups;
	private Long classTimeId;
	
	public ClassTimeConfig(Long classTimeId){
		this.classTimeId = classTimeId;
		seatingPositions = new ArrayList<SeattingChartPosition>();
		studentGroups = new ArrayList<StudentGroup>();
	}
	
	
	public List<SeattingChartPosition> getSeatingPositions() {
		return this.seatingPositions;
	}
	public List<StudentGroup> getStudentGroups() {
		return this.studentGroups;
	}
	public Long getClassTimeId() {
		return this.classTimeId;
	}
	public void setSeatingPositions(List<SeattingChartPosition> seatingPositions) {
		this.seatingPositions = seatingPositions;
	}
	public void setStudentGroups(List<StudentGroup> studentGroups) {
		this.studentGroups = studentGroups;
	}
	public void setClassTimeId(Long classTimeId) {
		this.classTimeId = classTimeId;
	}
	
	
	public void addSeattingChartPosition(SeattingChartPosition position){
		seatingPositions.add(position);
		
	}
	
	public void addStudentGroup(StudentGroup group){
		studentGroups.add(group);
	}
	
	
	
	

}
