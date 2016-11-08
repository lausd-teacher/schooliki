package net.videmantay.roster.views.classtime;

import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.classtime.json.ClassTimeJson;

public class ClassTimeForm extends Composite {

	private static ClassTimeFormUiBinder uiBinder = GWT.create(ClassTimeFormUiBinder.class);

	interface ClassTimeFormUiBinder extends UiBinder<Widget, ClassTimeForm> {
	}
	
	@UiField
	MaterialButton submitBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialTextBox title;
	
	@UiField
	MaterialTextArea description;
	
	@UiField
	MaterialCheckBox  isDefault;
		
	public ClassTimeForm() {
		console.log("ClassTime Form constructor");
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	public MaterialButton getSubmitBtn() {
		return this.submitBtn;
	}


	public MaterialButton getCancelBtn() {
		return this.cancelBtn;
	}


	public void show(){
		  modal.openModal();
	}
	
	public void hide(){
		modal.closeModal();
	}
	
	
	public ClassTimeJson getFormData(){
		ClassTimeJson formData = JavaScriptObject.createObject().cast();
		
		formData.setDescript(description.getText());
		formData.setTitle(title.getText());
		formData.setIsDefault(isDefault.getValue());
		
		return formData;
	}
	
	
	public MaterialTextBox getClassTimeTitle() {
		return this.title;
	}


	public MaterialTextArea getDescription() {
		return this.description;
	}


	public interface Presenter{
		void createClassTimeFormSubmitButton();
		void createClassTimeFormCancelButton();
	}

}
