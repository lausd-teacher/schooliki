package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.*;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}
	
	@UiField
	MaterialAnchorButton addIncidentBtn;

	@UiField
	MaterialRow incidentGridPanel;
	
	@UiField
	MaterialRow posIncidentRow;
	
	@UiField
	MaterialRow negIncidentRow;
	
	@UiField
	DeleteIncidentModal deleteModal;
	
	@UiField
	IncidentForm incidentForm;
	
	@UiField
	MaterialTab tab;
	
	@UiField
	MaterialAnchorButton posTab;
	
	@UiField
	MaterialAnchorButton negTab;
	
	Function openForm = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			IncidentJson incident = (IncidentJson)o[0];
			if(incident == null){
				incident = IncidentJson.createObject().cast();
				incident.setName("incident").setValue(0).setIconUrl("doctor");
			}
			incidentForm.setIncident(incident);
			incidentForm.show();
			return true;
		}
	};
	
	Function openDeleteModal = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			IncidentJson incident = (IncidentJson)o[0];
			deleteModal.setIncident(incident);
			deleteModal.show();
			return true;
		}
	};
	
	Function callDraw = new Function(){
		@Override
		public boolean  f(Event e){
			//stop event from bubbling
			e.stopPropagation();
			e.preventDefault();
			//just draw selected Panel
			IncidentJson incident = (IncidentJson)this.arguments(0);
			EditIncidentPanel panel = $(".incidentEditor.editing").as(Widgets).widget(0);
			if(panel == null){
				//incident wasn't push to roster must do so now
				RosterJson roster = window.getPropertyJSO("roster").cast();
				roster.getIncidents().push(incident);
				panel = new EditIncidentPanel();
				panel.setIncident(incident);
				MaterialColumn col = new MaterialColumn();
				col.setGrid("s6 m4 l3");
				col.add(panel);
				posIncidentRow.add(col);
			}
			//need to see if it should be replaced
			if(panel.incident.getValue() < 0){
				if($(panel).parent().parent().parent().id().equals("posIncidentGrid")){
					MaterialColumn col = $(panel).parent().as(Widgets).widget(0);
					posIncidentRow.remove(col);
					negIncidentRow.add(col);
					tab.selectTab("negIncidentGrid");
					tab.setIndicatorColor("red darken-3");
				}
			}
			
			if(panel.incident.getValue() >= 0){
				if($(panel).parent().parent().parent().id().equals("negIncidentGrid")){
					MaterialColumn col = $(panel).parent().as(Widgets).widget(0);
					negIncidentRow.remove(col);
					posIncidentRow.add(col);
					tab.selectTab("posIncidentGrid");
					tab.setIndicatorColor("green darken-3");
				}
			}
			
			panel.iconPanel.clear();
			panel.setIncident(incident);
			$(panel).removeClass("editing");
			//must return a boolean
			return true;
		}
	};
	
	final ClickHandler posTabHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			tab.setIndicatorColor("green darken-3");
		}};
		
		final ClickHandler negTabHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				tab.setIndicatorColor("red darken-3");
			}}; 
			
		final ClickHandler fabHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(body).trigger("editIncident");
				
			}};
	
	
	public IncidentMain() {
		initWidget(uiBinder.createAndBindUi(this));
		negTab.addClickHandler(negTabHandler);
		posTab.addClickHandler(posTabHandler);
		addIncidentBtn.addClickHandler(fabHandler);
	}//end constr.
	
	@Override
	public void onLoad(){
		draw();
		$(body).on("editIncident", openForm);
		$(body).on("deleteIncident", openDeleteModal);
		$(body).on("redrawIncident", callDraw);
	}
	
	private void draw(){
		RosterJson roster = window.getPropertyJSO("roster").cast();
		for(int i = 0; i < roster.getIncidents().length(); i++){
			IncidentJson incident = roster.getIncidents().get(i);
			EditIncidentPanel ip = new EditIncidentPanel().setIncident(incident);
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s6 m4 l2");
			if(incident.getValue() < 0){
				negIncidentRow.add(col);
			}else{
				posIncidentRow.add(col);
			}//end else
			
			col.add(ip);
		}//end for
	}

}
