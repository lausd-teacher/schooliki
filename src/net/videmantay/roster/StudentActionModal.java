package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
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
	
	public enum ActionType{SINGLE("Single"), MULTI("Multi"), WHOLE("Whole");
		final String type;           
		ActionType(String type){
			this.type = type;
		};
		@Override
		public String toString(){
			return type;
		}
		           }
	
	private ActionType actionType = ActionType.SINGLE;
	
	
	private Function onIncidentPicked = new Function(){
		@Override
		public boolean f(Event e, Object...in){
			//rpc to update student behavior
			incident = (IncidentJson)in[0];
			console.log("The incident return by on Incident Picked");
			console.log(incident);
			incidentReport.setIncicdent(incident).setStudents(students)
			.setRosterId(roster.getId()).setActionType(actionType.toString());
			
			//then send message to body when complete
			Ajax.post(RosterUrl.REPORT_INCIDENT, $$("incidentReport:" + JsonUtils.stringify(incidentReport)) );
			modal.closeModal();
			return true;
		}
	};
	
	@UiField
	MaterialModal modal;
	
	@UiField
	MaterialRow posIncidentRow;
	
	final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	public StudentActionModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void init(ActionType actiontype,JsArray<RosterStudentJson> students){
		this.actionType = actiontype;
		this.students = students;
		switch(actiontype){
		case SINGLE:single(); break;
		case MULTI: multi();break;
		case WHOLE: whole();break;
		
		}
	}
	
	private void single(){
		
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
		for(int i = 0; i < roster.getIncidents().length(); i++){
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s6 m3 l3");
			col.add(new IncidentItem(roster.getIncidents().get(i)));
			posIncidentRow.add(col);
		}
	}

}
