package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

public class StudentInfoMain extends Composite {

	private static StudentInfoMainUiBinder uiBinder = GWT.create(StudentInfoMainUiBinder.class);

	interface StudentInfoMainUiBinder extends UiBinder<Widget, StudentInfoMain> {
	}

	private  RosterStudentJson student = null;
	private final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	@UiField
	StudentPage studentPage;
	
	public StudentInfoMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public StudentInfoMain(Long id){
		this();
		this.setStudent(id);
	}
	
	public StudentInfoMain setStudent(Long studentId){
		for(int i = 0; i < roster.getRosterStudents().length(); i++){
			if(studentId == roster.getRosterStudents().get(i).getId()){
				student = roster.getRosterStudents().get(i);
				break;
			}
		}
		
		return this;
	}
	
	@Override
	public void onLoad(){
		console.log("Student for student info is ");
		console.log(student);
		drawStudent();
	}
	
	private void drawStudent(){
		studentPage.setStudent(student);
		
	}

}
