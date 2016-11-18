package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.components.IncidentCard;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialRow positiveIncidentsTypeContainer;
	
	@UiField
	MaterialRow negativeIncidentsTypeContainer;
	
	@UiField
	MaterialButton studentActionModalOkButton;
	
	final ClientFactory factory;
	
	public StudentActionModal(ClientFactory clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.factory = clientFactory;
	}
	
	
	public void addnewIncidentType(IncidentTypeJson incidentType){
		
		IncidentCard card = new IncidentCard(incidentType);
		MaterialColumn column = new MaterialColumn();
		column.add(card);
		
		if(incidentType.getPoints() >= 0){
			positiveIncidentsTypeContainer.add(column);
			
		}else{
			negativeIncidentsTypeContainer.add(column);
			
		}
		
	}
	
	
	
	public void setUpIncidents(JsArray<IncidentTypeJson> incidentTypes){
		 for(int i = 0; i <incidentTypes.length(); i++){
			 final IncidentTypeJson incidentType = incidentTypes.get(i);
			 final IncidentCard card = new IncidentCard(incidentType);
			 
			 card.getContainer().addDomHandler(new ClickHandler(){
					@Override
					public void onClick(ClickEvent event) {
						GWT.log("clicking on card in student action modal");
						SelectionManager.unSelectCurrentSelectedIncidentCard();
						SelectionManager.selectIncidentCard(card.getContainer());
						Long currentRosterId = factory.getCurrentRoster().getId();
						final IncidentJson newIncident = JavaScriptObject.createObject().cast();
						newIncident.setIncidentTypeId(incidentType.getId());
						newIncident.setName(incidentType.getName());
						newIncident.setRosterId(currentRosterId);
						newIncident.setValue(incidentType.getPoints());
						
						GQuery.ajax("/roster/" +factory.getCurrentRoster().getId() +"/student/"+SelectionManager.getSelectedStudentId()+"/incident",
								Ajax.createSettings().setData(newIncident).setType("POST").setDataType("json"))
								.done(new Function() {
									@Override
									public void f() {
										String id = arguments(0).toString();
										newIncident.setId(id);
										MaterialLoader.showLoading(false);
										hide();
									}
								}).progress(new Function() {
									@Override
									public void f() {
										MaterialLoader.showLoading(true);
									}
								}).fail(new Function() {
									@Override
									public void f() {
										MaterialLoader.showLoading(false);
										Window.alert("failed to create incident");
									}
								});
						
					}
				   }, ClickEvent.getType());
				
				MaterialColumn column = new MaterialColumn();
				column.add(card);
				
				if(incidentType.getPoints() >= 0){
					positiveIncidentsTypeContainer.add(column);
					
				}else{
					negativeIncidentsTypeContainer.add(column);
					
				}
			 
			 
		 }
	}
	
	public void show(){
		modal.openModal();
	}
	public void hide(){
		modal.closeModal();
	}

	
	
	public MaterialButton getStudentActionModalOkButton() {
		return this.studentActionModalOkButton;
	}


	public interface Presenter{
		
		public void studentActionModalOkButtonClickEvent();
	}



}
