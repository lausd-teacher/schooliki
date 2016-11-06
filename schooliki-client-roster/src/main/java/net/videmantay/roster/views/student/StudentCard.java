package net.videmantay.roster.views.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;

public class StudentCard extends Composite {

	private static StudentCardUiBinder uiBinder = GWT.create(StudentCardUiBinder.class);

	interface StudentCardUiBinder extends UiBinder<Widget, StudentCard> {
	}
	
    @UiField
	MaterialIcon removeButton;

	@UiField
	MaterialImage studentProfileImage;
    
    @UiField
	MaterialLabel studentNameLabel;
    
    @UiField
	MaterialLabel studentEmailLabel;
    
    private Long userId;
	
	public StudentCard(String studentProfileImageUrl, String studentName, String studentEmail, Long id) {
		initWidget(uiBinder.createAndBindUi(this));
		studentProfileImage.setUrl(studentProfileImageUrl);
		studentNameLabel.setText(studentName);
		studentNameLabel.getElement().addClassName("studentCardLabel");
		studentEmailLabel.setText(studentEmail);
		studentEmailLabel.getElement().addClassName("studentCardLabel");
		//**************************************
		
		removeButton.setDisplay(Display.NONE);
		this.userId = id;
	}
	
	public MaterialIcon getRemoveButton() {
		return this.removeButton;
	}

	public Long getUserId() {
		return this.userId;
	}

	public MaterialLabel getStudentNameLabel() {
		return this.studentNameLabel;
	}

	public MaterialImage getStudentProfileImage() {
		return this.studentProfileImage;
	}

}
