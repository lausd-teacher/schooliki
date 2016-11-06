package net.videmantay.roster.views.classtime;

import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;

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
	
	
	public interface Presenter{
		void createClassTimeFormSubmitButton();
		void createClassTimeFormCancelButton();
	}

}
