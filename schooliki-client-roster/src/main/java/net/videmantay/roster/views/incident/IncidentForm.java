package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNumberBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.url.RosterUrl;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}


	@UiField
	HTMLPanel formContainer;

	@UiField
	MaterialModal modal;

	@UiField
	MaterialInput nameInput;

	@UiField
	MaterialIntegerBox valueInput;

	@UiField
	MaterialListBox typeListBox;

	@UiField
	MaterialPanel iconPanel;

	@UiField
	MaterialIcon doneBtn;

	@UiField
	MaterialIcon cancelBtn;

	@UiField
	MaterialDropDown dropDown;

	public final IncidentIconGrid iconGrid = new IncidentIconGrid();

	public IncidentForm() {

		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("incidentFrom");

	}
	
	public IncidentJson getFormData(){
		
		IncidentJson newIncident = JavaScriptObject.createObject().cast();
		newIncident.setIconUrl("");
		newIncident.setName(nameInput.getValue());
		newIncident.setValue(valueInput.getValue());
		newIncident.setBehaviorType(typeListBox.getValue());
		
		
		return newIncident;
	}

	@Override
	public void onLoad() {
		$("#incidentFrom input").blur(getValidationFunction());

		$(".errorLabel").hide();

	}

	public void setIncident(IncidentJson incident) {
		
		iconPanel.clear();

		// need to fix this so it's in one place
		String html = "<svg viewBox='0 0 150 200' class='incidentIcon' style='width:7em; height:8em' id='"
				+ incident.getIconUrl() + "'>"
				+ "<use  xmlns:xlink='http://www.w3.org/1999/xlink' xlink:href='../img/allIcons.svg#"
				+ incident.getIconUrl() + "' /></svg>";
		iconPanel.add(new HTML(html));
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


	public MaterialInput getNameInput() {
		return this.nameInput;
	}

	public MaterialIntegerBox getValueInput() {
		return this.valueInput;
	}

	public MaterialListBox getTypeListBox() {
		return this.typeListBox;
	}


	public interface Presenter{
		void doneButtonClickEvent();
		void cancelButtonClickEvent();
	}
}
