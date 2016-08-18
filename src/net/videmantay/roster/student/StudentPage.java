package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;
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
	
	private  RosterStudentJson student;
	
	public StudentPage() {
		initWidget(uiBinder.createAndBindUi(this));

	}
	
	public StudentPage(RosterStudentJson student){
		this();
		this.setStudent(student);
	}
	
	public StudentPage setStudent(RosterStudentJson student){
		this.student = student;
		
		if(student == null){
			showError();
		}else{
		stuImage.setUrl(student.getThumbnails().get(1).getUrl());
		firstName.setInnerText(student.getFirstName());
		lastName.setInnerText(student.getLastName());
		if(student.getExtName() != null || student.getExtName().isEmpty()){
			extName.setInnerText(student.getExtName());
		}else{
			extName.setAttribute("display", "none");
		}
		DOB.setInnerText(student.getDOB());
		email.setInnerText(student.getAcctId());
		}
		return this;
	}
	
	@Override
	public void onLoad(){
		
	}
	
	private void showError(){
		
	}

}
