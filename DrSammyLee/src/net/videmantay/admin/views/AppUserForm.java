package net.videmantay.admin.views;


import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.html.Label;
import net.videmantay.shared.UserRoles;
import net.videmantay.student.json.AppUserJson;


public class AppUserForm extends Composite  {

	private static AppUserFormUiBinder uiBinder = GWT.create(AppUserFormUiBinder.class);

	interface AppUserFormUiBinder extends UiBinder<Widget, AppUserForm> {
	}	
		
	@UiField
	MaterialInput email;
		
	@UiField
	MaterialCard rolesSelectCard;
	
	
	@UiField
	MaterialCheckBox studentCheck;
	
	@UiField
	MaterialCheckBox teacherCheck;
	
	@UiField
	MaterialCheckBox facultyCheck;
	
	@UiField
	MaterialCheckBox adminCheck;
	
	@UiField
	MaterialAnchorButton submitBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	MaterialListBox listBox;
	
	@UiField
	MaterialModal formContainer;
	
	@UiField
	Label acctIdError;
	
	@UiField
	Label roleError;
	
	Function validateInput = new Function(){
		@Override
		public void f(){
			if($(this).is(":invalid")){
				$(this).next(".errorLabel").show();
				$(this).addClass("inputError");
				submitBtn.setEnabled(false);
			}else{
				$(this).next(".errorLabel").hide();
				submitBtn.setEnabled(true);
			}
			
			
		}
	};
	
	public AppUserForm() {
		initWidget(uiBinder.createAndBindUi(this));
		hide();
	}
	
	@Override
	public void onLoad(){
		$("input").blur(validateInput);
		$(".errorLabel").hide();

		
		studentCheck.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				listBox.setVisible(studentCheck.getValue());
			if(studentCheck.getValue()){
				$(teacherCheck,facultyCheck, adminCheck).hide();
			}else{
				$(".gwt-CheckBox").show();
			}	
			}});
		
		teacherCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(teacherCheck.getValue()){
				    $(studentCheck,facultyCheck).hide();
				}else{
					if(adminCheck.getValue()){
						facultyCheck.setVisible(true);
					}else{
					$(studentCheck,facultyCheck).show();
					}
				}
				
			}});
		
		
		facultyCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(facultyCheck.getValue()){
				$(studentCheck,teacherCheck).hide();
				}else{
					if(adminCheck.getValue()){
						teacherCheck.setVisible(true);
					}else{
					$(studentCheck,teacherCheck).show();
					}
				}	
			}});
		adminCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(adminCheck.getValue()){
				$(studentCheck).hide();
				}else{
					if(teacherCheck.getValue() || facultyCheck.getValue()){
						studentCheck.setVisible(false);
					}else{
					$(studentCheck).show();
					}
				}
				
			}});
	}
	
		public void show(){
			formContainer.openModal();
			
		}
		public void hide(){
			formContainer.closeModal();
			
		}
		
		public boolean hasErrors(){
		     return false;
		}
		
		

     public MaterialAnchorButton getSubmitBtn() {
			return this.submitBtn;
		}






		public MaterialAnchorButton getCancelBtn() {
			return this.cancelBtn;
		}


		public AppUserJson collectFormData(){
			
			AppUserJson newUser = JavaScriptObject.createObject().cast();
			newUser.setFirstLogin(true);
			newUser.setActive(true);
			newUser.setEmail(email.getText());
			
			boolean teacher = teacherCheck.getValue();
			boolean faculty = facultyCheck.getValue();
			boolean student = studentCheck.getValue();
			boolean admin = adminCheck.getValue();
			
			  if(admin){
				  newUser.addRole(UserRoles.ADMIN.toString());
			  }
			  
			  if(teacher){
				  newUser.addRole(UserRoles.TEACHER.toString());
			  }else if(faculty){
				  newUser.addRole(UserRoles.FACULTY.toString());
			  }else if(student){
				  newUser.addRole(UserRoles.STUDENT.toString());
			  }
			  newUser.setGradeLevel(listBox.getValue());
			
			return newUser;
		}


	public interface Presenter{
    	 void saveButtonClickEvent();
    	 void cancelButtonClickEvent();
     }

}
