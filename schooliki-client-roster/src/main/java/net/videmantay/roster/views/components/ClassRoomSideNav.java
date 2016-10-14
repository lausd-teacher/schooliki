package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;

public class ClassRoomSideNav extends Composite {

	private static ClassRoomSideNavUiBinder uiBinder = GWT.create(ClassRoomSideNavUiBinder.class);

	interface ClassRoomSideNavUiBinder extends UiBinder<Widget, ClassRoomSideNav> {
	}
	
	@UiField
	MaterialLink dashboardLink;
	
	@UiField
	MaterialLink assignmentLink;
	
	@UiField
	MaterialLink lessonPlanLink;
	
	@UiField
	MaterialLink incidentLink;
	
	@UiField
	MaterialLink goalLink;
	
	@UiField
	MaterialLink jobLink;
	
	@UiField
	MaterialLink bookLink;
	
	@UiField
	MaterialLink classTimeLink;
	
	@UiField
	MaterialLink formLink;
	
	@UiField
	MaterialLink logoutLink;
	

	public ClassRoomSideNav() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MaterialLink getDashboardLink() {
		return this.dashboardLink;
	}

	public MaterialLink getAssignmentLink() {
		return this.assignmentLink;
	}

	public MaterialLink getLessonPlanLink() {
		return this.lessonPlanLink;
	}

	public MaterialLink getIncidentLink() {
		return this.incidentLink;
	}

	public MaterialLink getGoalLink() {
		return this.goalLink;
	}

	public MaterialLink getJobLink() {
		return this.jobLink;
	}

	public MaterialLink getBookLink() {
		return this.bookLink;
	}

	public MaterialLink getClassTimeLink() {
		return this.classTimeLink;
	}

	public MaterialLink getFormLink() {
		return this.formLink;
	}

	public MaterialLink getLogoutLink() {
		return this.logoutLink;
	}
	
	public interface Presenter{
		void dashboardLinkClickEvent();
		void assignmentLinkClickEvent();
		void lessonPlanLinkClickEvent();
		void incidentLinkClickEvent();
		void goalLinkClickEvent();
		void jobLinkClickEvent();
		void bookLinkClickEvent();
		void classTimeClickEvent();
		void formClickEvent();
		void logoutLinkClickEvent();
	}
}
