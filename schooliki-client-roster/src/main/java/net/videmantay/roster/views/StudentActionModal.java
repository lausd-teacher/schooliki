package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialRow incidentsContainer;
	
	@UiField
	MaterialButton studentActionModalOkButton;
	
	public StudentActionModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void show(){
		modal.openModal();
	}
	public void hide(){
		modal.closeModal();
	}

	public MaterialRow getIncidentsContainer() {
		return this.incidentsContainer;
	}
	
	
	public MaterialButton getStudentActionModalOkButton() {
		return this.studentActionModalOkButton;
	}


	public interface Presenter{
		
		public void studentActionModalOkButtonClickEvent();
	}



}
