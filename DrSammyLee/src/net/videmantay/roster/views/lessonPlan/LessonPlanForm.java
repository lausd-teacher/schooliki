package net.videmantay.roster.views.lessonPlan;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class LessonPlanForm extends Composite {

	private static LessonPlanFormUiBinder uiBinder = GWT.create(LessonPlanFormUiBinder.class);

	interface LessonPlanFormUiBinder extends UiBinder<Widget, LessonPlanForm> {
	}

	public LessonPlanForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	MaterialDropDown subjectDropdown;
	
	
	@UiField
	MaterialTextBox newSubjectTextBox;
	
	@UiField
	MaterialDatePicker datePicker;
	
	@UiField
	MaterialTextArea description;
	
	@UiField
	MaterialButton attachment;

	
	@UiField
	MaterialRow sectionRow;
	
	@UiField
	MaterialCard lessonForm;
	
	
	
	
	

	
	

}
