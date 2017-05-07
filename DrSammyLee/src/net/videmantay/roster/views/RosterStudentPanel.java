package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentAttendanceJson;

public class RosterStudentPanel extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentPanel> {
	}
	
	@UiField
	HTMLPanel rosterStudentPanel;
	
	@UiField
	DivElement badgeRow;
	
	@UiField
	MaterialBadge statusBadge;
	
	@UiField
	MaterialBadge extraStatusBadge;
	
	@UiField
	DivElement checkRow;
	
	@UiField
	MaterialLabel firstName;
	
	@UiField
	MaterialLabel lastName;
	
	@UiField
	HTMLPanel honorsPanel;
	
	@UiField
	MaterialImage studentImage;
	
	@UiField
	MaterialTimePicker timePicker;
	
	@UiField
	MaterialRow attendanceRow;
	
	@UiField
	MaterialIcon attendanceStatusChip;
	
	private final RosterStudentJson rosStudent;

	public RosterStudentPanel(RosterStudentJson student) {
		rosStudent = student;
		initWidget(uiBinder.createAndBindUi(this));
		
		this.getElement().setId(student.getStudentId());
		if(student.getFirstName() == null || student.getFirstName().isEmpty()){
			firstName.setText(student.getStudentId());
		}else{
			firstName.setText(student.getFirstName());
			lastName.setText(student.getLastName());
		}
		if(student.getImageUrl() == null || student.getImageUrl().isEmpty()){
			studentImage.setUrl("img/user.svg");
		}else{
			studentImage.setUrl(student.getImageUrl());
		}
	}
	

	
	public RosterStudentJson getData(){
		return this.rosStudent;
	}
	
	public void gridStyle(){
		clearStyle();
		 $(rosterStudentPanel).addClass("grid", "card", "card-content");
		 $(badgeRow).css("display", "block");
	}
	
	public void clearStyle(){
		$(rosterStudentPanel).removeClass("grid", "chart", "sideNav", "card", "card-content");
	}
	public void chartStyle(){
		clearStyle();
		$(rosterStudentPanel).addClass("chart");
		$(badgeRow).css("display", "none");
	}
	
	public void attendence(final StudentAttendanceJson attendance){
		//initialize
		switch(attendance.getStatus()){
		case "PRESENT":studentPresent(); break;
		case "ABSENT":studentAbsent(); break;
		case "TARDY": studentTardy();break;
		}
		badgeRow.getStyle().setDisplay(Display.NONE);
		checkRow.getStyle().setDisplay(Display.NONE);
		attendanceRow.setVisible(true);
		
		this.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				switch(attendanceStatusChip.getIcon().getIconType()){
				case PERSON:studentAbsent(); break;
				case PERSON_OUTLINE:studentTardy(); break;
				case ALARM_ON: studentPresent();break;
				default:studentPresent();
				}
				
			}}, ClickEvent.getType());
	}
	
	
	private void studentAbsent(){
		attendanceStatusChip.setBackgroundColor("red");
		attendanceStatusChip.setIconType(IconType.PERSON_OUTLINE);
	}
	
	private void studentPresent(){
		timePicker.setVisible(false);
		attendanceStatusChip.setBackgroundColor("green");
		attendanceStatusChip.setIconType(IconType.PERSON);
	}
	
	private void studentTardy(){
		attendanceStatusChip.setBackgroundColor("amber");
		attendanceStatusChip.setIconType(IconType.ALARM_ON);
		timePicker.setVisible(true);
	}
	
	@Override
	public void onLoad(){
		this.getElement().setId(rosStudent.getStudentId());
	}

}
