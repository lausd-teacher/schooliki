package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.IncidentJson;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	
	@UiField
	HTMLPanel container;
	
	@UiField
	public MaterialFAB addIncidentFAB;
	
	@UiField
	MaterialTab incidentItemPanel;
	
	@UiField
	MaterialTabItem posItemTab;
	
	@UiField
	MaterialTabItem negItemTab;
	
	@UiField
	MaterialRow posItemRow;
	
	@UiField
	MaterialRow negItemRow;
	
	@UiField(provided=true)
	IncidentForm incidentForm;
	
	@UiField
	MaterialModal deleteIncidentModal;
	
	@UiField
	MaterialAnchorButton deleteIncidentModalOk;
	
	@UiField
	MaterialAnchorButton deleteIncidentModalCancel;

	
	//Click Handlers
	ClickHandler fabHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			incidentForm.show();
			
		}};
		
	ClickHandler deleteIncidentBtn = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
		IncidentItem incidentItem = $(event.getSource()).closest(".incidentItem").widget();
		deleteIncidentModal.openModal();
		$(deleteIncidentModal).data("incident", incidentItem.getIncident());
			
		}};
		
	ClickHandler editIncidentBtn = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			IncidentItem item = (IncidentItem)$(event.getSource()).closest(".incidentItem").widget();
			IncidentJson data = item.getIncident();
			incidentForm.setData(data);
			incidentForm.show();
			
		}};
	ClickHandler deleteIncidentModalOKBtn = new ClickHandler(){
		
		@Override
		public void onClick(ClickEvent e){
			IncidentJson data = $(deleteIncidentModal).data("incident", IncidentJson.class);
			Ajax.ajax(Ajax.createSettings()
					.setType("DELETE")
					.setUrl(RosterUrl.incident(utils.getCurrentRoster().getId(), data.getId()))
					).done(new Function(){
						@Override
						public void f(){
							IncidentJson incident = JsonUtils.safeEval((String)this.getArgument(0)).cast();
							JsArray<IncidentJson> newList = JsArray.createArray().cast();
							for(int i = 0; i < utils.getIncidents().length(); i++){
								if(utils.getIncidents().get(i).getId() ==incident.getId()){
									continue;
								}//end if
								newList.push(utils.getIncidents().get(i));
							}//end for
							utils.setIncidents(newList);
							drawGrid();
							MaterialToast.fireToast("Incident Successfully deleted" );
						}
					});
			deleteIncidentModal.closeModal();
		}
	};
	
	ClickHandler deleteIncidentModalCancelBtn = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			deleteIncidentModal.closeModal();
			
		}};
		
		private final RosterUtils utils;
	
	public IncidentMain(RosterUtils ru) {
		utils = ru;
		incidentForm = new IncidentForm(utils);
		console.log(utils.getIncidents());
		initWidget(uiBinder.createAndBindUi(this));
		container.add(incidentForm);
		drawGrid();
		//add click handler to fab
		addIncidentFAB.addClickHandler(fabHandler);
		
		incidentForm.doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			Promise promise = incidentForm.submit();
			promise.done(new Function(){
				@Override
				public void f(){
					console.log("Add incident Promise is called, the argumentj is ");
					console.log(this.arguments(0));
					IncidentJson incident = ((IncidentJson)this.arguments(0)).cast();
					boolean noMatch = true;
					for(int i = 0; i < utils.getIncidents().length(); i++){
						if(utils.getIncidents().get(i).getId() == incident.getId()){
							//just replace values for img and name and points
							IncidentJson oldValues = utils.getIncidents().get(i);
							oldValues.setImageUrl(incident.getImageUrl());
							oldValues.setName(incident.getName());
							oldValues.setPoints(incident.getPoints());
							noMatch=false;
							break;
						}
					}
					if(noMatch){
						utils.getIncidents().push(incident);
					}
					drawGrid();
					MaterialToast.fireToast("incident successfully added");
					
				}
			});
				}});// end incidentform done click handler
		
		incidentForm.cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				incidentForm.cancel();
				incidentForm.form.reset();
				incidentForm.modal.closeModal();
				
			}});
		
		deleteIncidentModalOk.addClickHandler(deleteIncidentModalOKBtn);
		deleteIncidentModalCancel.addClickHandler(deleteIncidentModalCancelBtn);
	}
	
	private void drawGrid(){
		posItemRow.clear();
		negItemRow.clear();
		
		for(int i = 0; i < utils.getIncidents().length(); i++){
			IncidentItem item = new IncidentItem();
			item.deleteIncidentBtn.addClickHandler(deleteIncidentBtn);
			item.editIncidentBtn.addClickHandler(editIncidentBtn);
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s12 m4 l3");
			col.add(item);
			item.setIncident(utils.getIncidents().get(i));
			if(item.getIncident().getPoints() < 0){
				negItemRow.add(col);
			}else{
				posItemRow.add(col);
			}
		}//end for
	}
	
	
}
