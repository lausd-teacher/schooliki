package net.videmantay.roster.views.incident;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.ajax.Ajax.Settings;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.Properties;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialModal;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.IncidentJson;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}

	@UiField
	public FormPanel form;
	
	@UiField
	public HTMLPanel formContainer;

	@UiField
	public MaterialModal modal;
	
	@UiField
	public MaterialButton incidentImageBtn;
    
	@UiField
    public MaterialInput nameInput;
	
	@UiField
	public MaterialInput imageName;

	@UiField
	public MaterialIntegerBox valueInput;

	@UiField
	public MaterialButton doneBtn;

	@UiField
	public MaterialButton cancelBtn;
	

	public final IncidentIconGrid iconGrid = new IncidentIconGrid();
	private Function iconChangeFunc = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			String iconName = (String)o[0];
			console.log("incident name is " +iconName);
			
			incidentImageBtn.clear();
			incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML(iconName)));
			return true;
		}
	};


	public IncidentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		
		formContainer.getElement().setId("incidentForm");
		valueInput.setValue(0);
		incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML("doctor")));
		incidentImageBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			$("#iconGrid").show();
				console.log("incident Image button clicked");
				
			}});
	}
	
	public IncidentJson getFormData(){
		IncidentJson newIncident = JavaScriptObject.createObject().cast();
		newIncident.setName(nameInput.getText());
		newIncident.setImageUrl(imageName.getValue());
		
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
		$(body).on("incidentIconChange", iconChangeFunc);
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
	
	public void cancel(){
		form.clear();
	}
	
	public Promise submit(Long id){
		
		return Ajax.ajax(Ajax.createSettings().setContentType("application/json")
				.setData(getFormData()).
	}
}
