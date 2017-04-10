package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import gwt.material.design.client.ui.MaterialToast;
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
	
	//Click Handlers
	ClickHandler fabHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			incidentForm.show();
			
		}};
		
	
	
	final IncidentForm incidentForm = new IncidentForm();
	
	private final RosterUtils utils;
	
	public IncidentMain(RosterUtils ru) {
		utils = ru;
		console.log(utils.getIncidents());
		initWidget(uiBinder.createAndBindUi(this));
		container.add(incidentForm);
		drawGrid();
		//add click handler to fab
		addIncidentFAB.addClickHandler(fabHandler);
		
		incidentForm.doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			Promise promise = incidentForm.submit(utils.getCurrentRoster().getId());
			promise.done(new Function(){
				@Override
				public boolean f(Event e, Object... o){
					incidentForm.form.reset();
					incidentForm.modal.closeModal();
					drawGrid();
					MaterialToast.fireToast("incident successfully added");
					return true;
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
	}
	
	private void drawGrid(){
		posItemRow.clear();
		negItemRow.clear();
		
		for(int i = 0; i < utils.getIncidents().length(); i++){
			IncidentItem item = new IncidentItem();
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
