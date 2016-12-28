package net.videmantay.roster.views.incident;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.components.IncidentFormIconInput;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}


	@UiField
	HTMLPanel formContainer;

	@UiField
	MaterialModal modal;

	@UiField
	HTMLPanel iconInputContainer;
    
	@UiField
    MaterialInput nameInput;

	@UiField
	MaterialIntegerBox valueInput;

	@UiField
	MaterialButton doneBtn;

	@UiField
	MaterialButton cancelBtn;
	
	final IncidentFormIconInput incidentFormIconInput;
	

	public final IncidentIconGrid iconGrid = new IncidentIconGrid();

	public IncidentForm(IncidentFormIconInput iconInput) {
		initWidget(uiBinder.createAndBindUi(this));
		this.incidentFormIconInput = iconInput;
		iconInputContainer.add(incidentFormIconInput);
		formContainer.getElement().setId("incidentForm");
		valueInput.setValue(0);
	}
	
	public IncidentTypeJson getFormData(){
		IncidentTypeJson newIncident = JavaScriptObject.createObject().cast();
		newIncident.setName(nameInput.getText());
		newIncident.setImageUrl(incidentFormIconInput.getSelectedIcon().getSrc());
		
		if(valueInput.getText().isEmpty() || valueInput.getText() == null )
		      newIncident.setPoints(0);
		else
			newIncident.setPoints(valueInput.getValue());
		
		return newIncident;
	}
	
	

	@Override
	public void onLoad() {
		$("#incidentForm input").blur(getValidationFunction());
		$(".errorLabel").hide();
	}


	public void show() {
		modal.openModal();
	}
	
	public void hide() {
		modal.closeModal();
	}

	private Function getValidationFunction() {

		return new Function() {
			@Override
			public boolean f(Event e, Object...o) {
				NativeEvent nEvent = e.cast();
				EventTarget target = nEvent.getEventTarget();
				
				if ($(target).is(":invalid")) {
					$(target).next(".errorLabel").show();
					$(target).addClass("inputError");
					doneBtn.setEnabled(false);
				} else {
					$(target).next(".errorLabel").hide();
					doneBtn.setEnabled(true);
				}
				
				return true;
			}
		};

	}
	
	
	public MaterialButton getDoneBtn() {
		return this.doneBtn;
	}

	public MaterialButton getCancelBtn() {
		return this.cancelBtn;
	}



	public MaterialIntegerBox getValueInput() {
		return this.valueInput;
	}



	public interface Presenter{
		void doneButtonClickEvent();
		void cancelButtonClickEvent();
	}
}
