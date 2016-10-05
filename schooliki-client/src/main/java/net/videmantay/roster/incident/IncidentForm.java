package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNumberBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}

	private IncidentJson incident;

	@UiField
	MaterialModal modal;

	@UiField
	FormPanel form;

	@UiField
	MaterialInput nameInput;

	@UiField
	MaterialNumberBox<Integer> valueInput;

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

	final ClickHandler doneHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			incident.setIconUrl((String) $(iconPanel).data("icon"));
			incident.setName(nameInput.getValue());
			incident.setValue(valueInput.getValue());
			incident.setBehaviorType(typeListBox.getValue());
			RosterJson roster = window.getPropertyJSO("roster").cast();
			Ajax.post(RosterUrl.SAVE_INCIDENTS, $$("roster:" + JsonUtils.stringify(roster))).done(new Function() {
				@Override
				public void f() {
					MaterialToast.fireToast("Incident saved");
				}
			});
			form.reset();
			incident = null;
			modal.closeModal();

		}
	};

	final ClickHandler cancelHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			form.reset();
			incident = null;
			modal.closeModal();

		}
	};

	public IncidentForm() {

		initWidget(uiBinder.createAndBindUi(this));

		doneBtn.addClickHandler(doneHandler);

		cancelBtn.addClickHandler(cancelHandler);

		form.getElement().setId("incidentFrom");

	}

	@Override
	public void onLoad() {
		$("#incidentFrom input").blur(getValidationFunction());

		$(".errorLabel").hide();

	}

	public void setIncident(IncidentJson incident) {
		if (incident == null) {
			incident = IncidentJson.createObject().cast();
		}
		this.incident = incident;
		nameInput.setValue(incident.getName());
		valueInput.setValue(incident.getValue());
		typeListBox.setValueSelected(incident.getBehaviorType(), true);
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
}
