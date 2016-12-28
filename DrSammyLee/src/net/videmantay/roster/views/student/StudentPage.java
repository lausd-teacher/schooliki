package net.videmantay.roster.views.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import net.videmantay.student.json.RosterStudentJson;

public class StudentPage extends Composite {

	private static StudentPageUiBinder uiBinder = GWT.create(StudentPageUiBinder.class);

	interface StudentPageUiBinder extends UiBinder<Widget, StudentPage> {
	}

	@UiField
	SpanElement firstName;
	
	@UiField
	SpanElement lastName;
	
	@UiField
	SpanElement extName;
	
	@UiField
	SpanElement DOB;
	
	@UiField
	SpanElement email;
	
	@UiField
	MaterialImage stuImage;
	
	
	public StudentPage() {
		initWidget(uiBinder.createAndBindUi(this));

	}
	
	public StudentPage(RosterStudentJson student){
		this();
		this.setStudent(student);
	}
	
	public StudentPage setStudent(RosterStudentJson student){

		return this;
	}
	
	@Override
	public void onLoad(){
		
	}


}
