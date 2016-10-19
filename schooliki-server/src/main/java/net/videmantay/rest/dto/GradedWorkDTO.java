package net.videmantay.rest.dto;

import java.util.HashSet;
import java.util.Set;

import net.videmantay.server.entity.EducationalLink;
import net.videmantay.server.entity.GradedWork;
import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;
import net.videmantay.shared.SubjectType;

public class GradedWorkDTO {

	public Long id;

	public String title;

	public Set<String> standards = new HashSet<String>();

	public Set<GradeLevel> gradeLevels;

	public Long rosterId;

	public String mediaUrl;

	public String description;

	public SubjectType subject;

	private Set<EducationalLink> links;

	public GradedWorkType type = GradedWorkType.HOMEWORK;

	public Language lang = Language.ENGLISH;

	public Double pointsPossible;

	public String assignedDate;

	public String dueDate;

	public Boolean finishedGrading = false;

	public Long rubric;

	public GradedWorkDTO(GradedWork gradedWork) {
		this.id = gradedWork.id;
		this.title = gradedWork.title;
		this.rosterId = gradedWork.rosterId;
		this.standards = gradedWork.standards;
		this.gradeLevels = gradedWork.gradeLevels;
		this.mediaUrl = gradedWork.mediaUrl;
		this.description = gradedWork.description;
		this.subject = gradedWork.subject;
		this.links = gradedWork.getLinks();
		this.type = gradedWork.type;
		this.lang = gradedWork.lang;
		this.pointsPossible = gradedWork.pointsPossible;
		this.assignedDate = gradedWork.assignedDate;
		this.dueDate = gradedWork.dueDate;
		this.finishedGrading = gradedWork.finishedGrading;
		this.rubric = gradedWork.rubric;
	}

	public GradedWorkDTO() {

	}

	public GradedWorkType getGradedWorkType() {
		return type;
	}

	public void setGradedWorkType(GradedWorkType gradedWorkType) {
		this.type = gradedWorkType;
	}

	public Double getPointsPossible() {
		return this.pointsPossible;
	}

	public void setPointsPossible(Double pointsPossible) {
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

	public String getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(String date) {
		this.dueDate = date;
	}

	public Boolean isFinishedGrading() {
		return finishedGrading;
	}

	public void setFinishedGrading(Boolean status) {
		this.finishedGrading = status;
	}

	public Long getRubric() {
		return this.rubric;
	}

	public void setRubric(Long rubric) {
		this.rubric = rubric;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getStandards() {
		return standards;
	}

	public void setStandards(Set<String> standards) {
		this.standards = standards;
	}

	public Set<GradeLevel> getGradeLevels() {
		return gradeLevels;
	}

	public void setGradeLevels(Set<GradeLevel> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SubjectType getSubject() {
		return subject;
	}

	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}

	public Set<EducationalLink> getLinks() {
		return links;
	}

	public void setLinks(Set<EducationalLink> links) {
		this.links = links;
	}

	public Long getRosterId() {
		return this.rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

}
