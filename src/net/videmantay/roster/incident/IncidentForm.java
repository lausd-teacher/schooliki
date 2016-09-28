package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNumberBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.html.Option;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.BehaviorType;

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
	MaterialTextBox nameInput;
	
	@UiField
	MaterialNumberBox <Integer>valueInput;
	
	@UiField
	MaterialListBox typeListBox;
	
	@UiField
	MaterialPanel iconPanel;
	
	@UiField
	MaterialAnchorButton doneBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	IncidentIconGrid iconGrid;
	
	
				
		final ClickHandler cancelHandler = new ClickHandler(){

					@Override
					public void onClick(ClickEvent event) {
						form.reset();
						incident = null;
						modal.closeModal();
						
					}};	
	
		final ClickHandler iconPanelHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(iconGrid).as(Effects).clipToggle();
			}};
			
	final ClickHandler doneHandler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			submit();
		}
	};
			
	public IncidentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		
		doneBtn.addClickHandler(doneHandler);
		
		cancelBtn.addClickHandler( cancelHandler);
		
		iconPanel.addDomHandler(iconPanelHandler, ClickEvent.getType());
		
		for(int i = 0; i < BehaviorType.values().length; i++){
			String type = BehaviorType.values()[i].name();
			Option o = new Option();
			o.setText(type.toLowerCase());
			o.setValue(type);
			typeListBox.add(o);
		}
		
	}
	
	public void setIncident(IncidentJson incident){
		if(incident == null){
			incident = IncidentJson.createObject().cast();
		}
		this.incident = incident;
		nameInput.setValue(incident.getName());
		valueInput.setValue(incident.getValue());
		typeListBox.setValueSelected(incident.getBehaviorType(), true);
		iconPanel.clear();
		
		//need to fix this so it's in one place
		String html = "<svg viewBox='0 0 150 200' class='incidentIcon' style='width:7em; height:8em' id='"
				+incident.getIconUrl()+"'>"
				+"<use  xmlns:xlink='http://www.w3.org/1999/xlink' xlink:href='../img/allIcons.svg#" 
				+ incident.getIconUrl()
				+"' /></svg>";
		iconPanel.add(new HTML(html));
	}
	
	
	public void show(){
		modal.openModal();
	}
	
	final void submit(){
		MaterialLoader.showLoading(true);
		incident.setIconUrl((String) $(iconPanel).find("svg").id());
		incident.setName(nameInput.getValue());
		incident.setValue((int)valueInput.getValueAsNumber());
		incident.setBehaviorType(typeListBox.getValue());
		
		
		$(body).trigger("redrawIncident", incident);
		form.reset();
		incident = null;
		modal.closeModal();

		RosterJson roster = window.getPropertyJSO("roster").cast();
		Properties prop = Properties.create();
		prop.set("roster", roster.getId());
		prop.set("incidents", JsonUtils.stringify(roster.getIncidents()));
		Ajax.post(RosterUrl.SAVE_INCIDENTS, prop)
		.done(new Function(){
			@Override
			public void f(){
				MaterialLoader.showLoading(false);
				MaterialToast.fireToast( (String)this.arguments(0));	
			}
		});
		
	}
}
