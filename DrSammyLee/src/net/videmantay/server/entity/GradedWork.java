package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.rest.dto.GradedWorkDTO;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;

@Entity
public class GradedWork extends Assignment implements Serializable{
	
	
	public GradedWorkType type = GradedWorkType.HOMEWORK;
	
	public Language lang = Language.ENGLISH;
	
	public Double pointsPossible = 0.0;
	
	public String assignedDate;
	
	@Index
	public String dueDate;
	
	public Boolean finishedGrading = false;
	
	public Long rubric;
	
	
	public GradedWorkType getGradedWorkType() {
		return type;
	}


	public void setGradedWorkType(GradedWorkType gradedWorkType) {
		this.type = gradedWorkType;
	}
	
	public Double getPointsPossible(){
		return this.pointsPossible;
	}
	
	public void setPointsPossible(Double pointsPossible){
		this.pointsPossible = pointsPossible;
	}


	public Language getLang() {
		return lang;
	}


	public void setLang(Language lang) {
		this.lang = lang;
	}


	public String getAssignedDate() {
		return assignedDate;
	}


	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getDueDate(){
		return this.dueDate;
	}
	public void setDueDate (String date){
		this.dueDate = date;
	}
	
	public Boolean isFinishedGrading(){
		return finishedGrading;
	}
	
	public void setFinishedGrading(Boolean status){
		this.finishedGrading = status;
	}



	public Long getRubric(){
		return this.rubric;
	}
	
	public void setRubric(Long rubric){
		this.rubric = rubric;
	}
	
	
	public String getGradedbookCol(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getTitle() + "\n" + this.getSubject() + "\n" + this.getId());
		return sb.toString();
	}
	
	public static GradedWork createFromDTO(GradedWorkDTO gradedWorkDTO){
		GradedWork gradedWork = new GradedWork();
		
		gradedWork.id = gradedWorkDTO.id;
		gradedWork.title = gradedWorkDTO.title;
		gradedWork.rosterId = gradedWorkDTO.rosterId;
		gradedWork.standards = gradedWorkDTO.standards;
		gradedWork.gradeLevels = gradedWorkDTO.gradeLevels;
		gradedWork.mediaUrl = gradedWorkDTO.mediaUrl;
		gradedWork.description = gradedWorkDTO.description;
		gradedWork.subject = gradedWorkDTO.subject;
		gradedWork.setLinks(gradedWorkDTO.getLinks());
		gradedWork.type = gradedWorkDTO.type;
		gradedWork.lang = gradedWorkDTO.lang;
		gradedWork.pointsPossible = gradedWorkDTO.pointsPossible;
		gradedWork.assignedDate = gradedWorkDTO.assignedDate;
		gradedWork.dueDate = gradedWorkDTO.dueDate;
		gradedWork.finishedGrading = gradedWorkDTO.finishedGrading;
		gradedWork.rubric = gradedWorkDTO.rubric;
		
		
		return gradedWork;
	}
	
}
