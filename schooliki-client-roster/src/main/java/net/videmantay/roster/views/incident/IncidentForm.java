package net.videmantay.roster.views.incident;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.components.IncidentCard;
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
	MaterialListBox nameInput;

	@UiField
	MaterialIntegerBox valueInput;


	@UiField
	MaterialIcon doneBtn;

	@UiField
	MaterialIcon cancelBtn;
	
	@UiField
	MaterialContainer incidentsTypeContainer;

	public final IncidentIconGrid iconGrid = new IncidentIconGrid();

	public IncidentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("incidentFrom");
		valueInput.setValue(0);
	}
	
	public IncidentJson getFormData(){
		
		IncidentJson newIncident = JavaScriptObject.createObject().cast();
		newIncident.setName(nameInput.getSelectedItemText());
		newIncident.setValue(valueInput.getValue());
		newIncident.setIncidentTypeId(SelectionManager.getSelectedIncidentCard().getElement().getId());
		
		return newIncident;
	}
	
	public void setIncidentsTypes(JsArray<IncidentTypeJson> incidentTypes){
		MaterialRow incidentRow = new MaterialRow();
		for(int i = 0; i < incidentTypes.length(); i++){
			IncidentTypeJson incidentType = incidentTypes.get(i);
			IncidentCard card = new IncidentCard(incidentType.getName(), incidentType.getImageUrl(), incidentType.getId());
			
			MaterialColumn column = new MaterialColumn();
			column.add(card);
			
			incidentRow.add(column);
			//always select the first incident
			if(i == 0)
				 SelectionManager.selectIncidentCard(card.getContainer());
		}
		incidentsTypeContainer.add(incidentRow);
		
	}

	@Override
	public void onLoad() {
		$("#incidentFrom input").blur(getValidationFunction());
		$(".errorLabel").hide();
	}
	
	public void populateStudentsNamesList(JsArray<AppUserJson> students){
		nameInput.clear();
		for(int i = 0; i < students.length(); i++){
			AppUserJson student = students.get(i);
			nameInput.addItem(student.getName());
		}
		
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
			public void f() {
				GWT.log("event" + $(this).id());
				if ($(this).is(":invalid")) {
					$(this).next(".errorLabel").show();
					$(this).addClass("inputError");
				} else {
					$(this).next(".errorLabel").hide();
				}
			}
		};

	}
	
	
	public MaterialIcon getDoneBtn() {
		return this.doneBtn;
	}

	public MaterialIcon getCancelBtn() {
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
