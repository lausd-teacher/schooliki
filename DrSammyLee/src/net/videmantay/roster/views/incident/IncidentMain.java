package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.components.IncidentCard;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	
	@UiField
	HTMLPanel container;
	
	@UiField
	MaterialFAB addIncidentFAB;
	
	IncidentForm incidentForm = new IncidentForm();
	private final RosterUtils utils;
	
	public IncidentMain(RosterUtils ru) {
		utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		container.add(incidentForm);
	}
	
	public void setIncidentsTypes(JsArray<IncidentTypeJson> incidentTypes){
	
		for(int i = 0; i < incidentTypes.length(); i++){
			IncidentTypeJson incidentType = incidentTypes.get(i);
			final IncidentCard card = new IncidentCard(incidentType);
			card.getContainer().addDomHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					GWT.log("clicking on card in main");
					SelectionManager.unSelectCurrentSelectedIncidentCard();
					SelectionManager.selectIncidentCard(card.getContainer());
					
				}
			   }, ClickEvent.getType());
			
			MaterialColumn column = new MaterialColumn();
			column.add(card);
			
		}
		
		
	}
	
	
	public void addIncidentType(IncidentTypeJson incidentType){
		IncidentCard card = new IncidentCard(incidentType);
		
		MaterialColumn column = new MaterialColumn();
		column.add(card);
	
		
	}
	
	public MaterialFAB getAddIncidentFAB() {
		return this.addIncidentFAB;
	}

	public IncidentForm getIncidentForm() {
		return this.incidentForm;
	}



}
