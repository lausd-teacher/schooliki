package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTitle;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentReportJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}
	
	private JsArray<RosterStudentJson> students = JsArray.createArray().cast();
	private IncidentJson incident;
	private IncidentReportJson incidentReport = JavaScriptObject.createObject().cast();
	
	private ActionType actionType = ActionType.SINGLE;
	
	//html for more button on incident grid
	final String moreHtml = "<div class='moreItems grey lighten-3 center-align' style='cursor:pointer;height:100px; border:2px solid Silver; margin-top:2em' >" +
			"<span style='display:block; font-size:2em;margin-left:auto; margin-right:auto'>More</span>"+
			"<i class='material-icons' style='font-size:3em;margin-left:auto; margin-right:auto;height:3em;color:Silver'>add_circle</i></div>";
	
	
	private Function onIncidentPicked = new Function(){
		@Override
		public boolean f(Event e, Object...in){
			//rpc to update student behavior
			incident = (IncidentJson)in[0];
			console.log("The incident return by on Incident Picked");
			console.log(incident);
			//send ids accross wire only
			String studentIds = new String();
			for(int i = 0; i <students.length(); i++){
				if(i == students.length() - 1){
					studentIds += students.get(i).getId() +"";
				}else{
				studentIds += students.get(i).getId() +",";
				}
			}
			incidentReport.setIncicdent(incident).setStudentIds(studentIds)
			.setRosterId(roster.getId()).setActionType(actionType.toString());
			
			//then send message to body when complete
			console.log("incident report sent to server");
			console.log(JsonUtils.stringify(incidentReport));
			Ajax.post(RosterUrl.REPORT_INCIDENT, $$("incidentReport:" + JsonUtils.stringify(incidentReport)) );
			modal.closeModal();
			return true;
		}
	};
	
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialRow posIncidentRow;
	
	@UiField
	MaterialRow negIncidentRow;
	
	@UiField
	MaterialImage image;
	
	@UiField
	MaterialTitle studentsTitle;
	
	@UiField
	MaterialTab tab;
	
	@UiField
	MaterialAnchorButton posTab;
	
	@UiField
	MaterialAnchorButton negTab;
	
	@UiField
	MaterialAnchorButton cancelStudentActionBtn;
	
	final ClickHandler gotoItems = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			event.preventDefault();
			//taking advantage of the fact that we are in dashboard
			//so just add incidents at end of url
			modal.closeModal();
			String token = History.getToken() +"/incidents";
			History.newItem(token);
			
			
		}};
	final ClickHandler posTabHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			tab.setIndicatorColor("green");
		}};
		
		final ClickHandler negTabHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				tab.setIndicatorColor("red");
			}};
	final ClickHandler cancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			modal.closeModal();
		}};
	
	final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	public StudentActionModal() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//draw incident items from roster

	}
	
	public void loadData(ActionType actiontype,JsArray<RosterStudentJson> students){
		this.actionType = actiontype;
		this.students = students;
		switch(actiontype){
		case SINGLE:single(); break;
		case MULTI: multi();break;
		case WHOLE: whole();break;
		
		}
	}
	
	private void single(){
		image.setType(ImageType.CIRCLE);
		if(students.get(0).getThumbnails() != null){
		image.setUrl(students.get(0).getThumbnails().get(3).getUrl());
		}else{
			image.setUrl("../img/user.svg");
		}
		studentsTitle.setTitle(students.get(0).getFirstName() + " " + students.get(0).getLastName());
	}
	
	private void multi(){
		
	}
	
	private void whole(){
		
	} 
	
	public void show(){
		modal.openModal();
	}
	public void hide(){
		modal.closeModal();
	}

	@Override
	public void onLoad(){
		$(body).on("incidentPicked", onIncidentPicked);
		//add click for tab
		posTab.addClickHandler(posTabHandler);
		negTab.addClickHandler(negTabHandler);
		cancelStudentActionBtn.addClickHandler(cancelHandler);
		//setup incident girds
		for(int i = 0; i < roster.getIncidents().length(); i++){
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s6 m3 l3");
			IncidentItem ii = new IncidentItem();
			ii.setIncident(roster.getIncidents().get(i));
			col.add(ii);
			if(roster.getIncidents().get(i).getValue() >= 0){
			posIncidentRow.add(col);	
			}else{
				negIncidentRow.add(col);
			}
		}
		
		HTMLPanel moreItems = new HTMLPanel(moreHtml);
		MaterialColumn moreCol = new MaterialColumn();
		moreCol.setGrid("s12 m12 l12");
		moreCol.add(moreItems);
		posIncidentRow.add(moreCol);
		moreItems.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String token = History.getToken() + "/incidents";
				History.newItem(token);
				modal.closeModal();
				
			}}, ClickEvent.getType());
		
		HTMLPanel moreItems2 = new HTMLPanel(moreHtml);
		MaterialColumn moreCol2 = new MaterialColumn();
		moreCol2.setGrid("s12 m12 l12");
		moreCol2.add(moreItems2);
		negIncidentRow.add(moreCol2);
		moreItems2.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String token = History.getToken() + "/incidents";
				History.newItem(token);
				modal.closeModal();
				
			}}, ClickEvent.getType());
		
	}

}
