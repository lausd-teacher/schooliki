package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialToast;
import gwtquery.plugins.ui.interactions.Draggable;
import net.videmantay.student.json.RosterStudentJson;

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
	
	private final RosterStudentJson rosStudent;

	public RosterStudentPanel(RosterStudentJson student) {
		rosStudent = student;
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	
	public void setData(RosterStudentJson student){
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
	
	public RosterStudentJson getValue(){
		return this.rosStudent;
	}
	
	@Override
	public void onLoad(){
		setData(rosStudent);
	}
	
	public void gridStyle(){
		$(statusBadge).addClass("studentBadge", "studentBadgeLeft");
		$(extraStatusBadge).addClass("studentBadge", "studentBadgeRight");
		 $(rosterStudentPanel).addClass("grid", "card", "card-content");
	}
	
	private void clearStyle(){
		$(rosterStudentPanel).removeClass("grid", "card", "card-content");
		$(statusBadge).removeClass("studentBadge", "studentBadgeLeft");
		$(extraStatusBadge).removeClass("studentBadge", "studentBadgeRight");
	}
	

}
