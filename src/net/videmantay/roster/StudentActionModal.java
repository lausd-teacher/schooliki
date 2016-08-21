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

import gwt.material.design.client.ui.MaterialModal;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}
	
	private JsArray<RosterStudentJson> students = JsArray.createArray().cast();
	private IncidentJson incident;
	private IncidentReportJson incidentReport = JavaScriptObject.createObject().cast();
	
	public enum ActionType{SINGLE, MULTI, WHOLE};
	
	private ActionType actionType = ActionType.SINGLE;
	
	private Function incidentRecorded = new Function(){
		@Override
		public void f(){
			//get the student //and incident send it to toast
			IncidentReportJson inr = JsonUtils.safeEval((String)this.arguments(0)).cast();
			$(body).trigger("incidentrecorded", inr, actionType);
		}
	};
	private Function onIncidentPicked = new Function(){
		@Override
		public boolean f(Event e, Object...in){
			//rpc to update student behavior
			incident = (IncidentJson)in[0];
			incidentReport.setIncicdent(incident).setStudents(students);
			
			//then send message to body when complete
			Ajax.post("/teacher/saveincidents", $$("incidentReport:" + incidentReport) )
			.done(incidentRecorded);
			modal.closeModal();
			return true;
		}
	};
	
	@UiField
	MaterialModal modal;
	
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
	
	@Override
	public void onLoad(){
		$(body).on("incidentPicked", onIncidentPicked);
	}

}
