package net.videmantay.roster.views;

import java.util.ArrayList;
import java.util.Collections;

import com.google.common.collect.Ordering;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.views.incident.IncidentItem;
import net.videmantay.roster.views.incident.IncidentValueCompare;
import net.videmantay.student.json.RosterStudentJson;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}

	@UiField
	public MaterialModal modal;
	@UiField
	public MaterialPanel posIncidents;
	@UiField
	public MaterialPanel negIncidents;
	
	public StudentActionModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(JsArray<RosterStudentJson> students){
		if(students.length() > 1){
			//do multi students and hide footer
			return;
		}
		if(students.length() < 1){
			return;
		}
		
		//default is one student
		
	}
	
	public void drawIncidentGrid(JsArray<IncidentJson> incidents){
		ArrayList<IncidentJson> incidentList = new ArrayList<>();
		for(int i = 0; i < incidents.length(); i++){
			incidentList.add(incidents.get(i));
		}
		
		Collections.sort(incidentList, new IncidentValueCompare());
		IncidentItem item;
		for(IncidentJson incident: incidentList){
			item = new IncidentItem();
			item.setIncident(incident);
			if(incident.getPoints() < 0){
				negIncidents.add(item);
			}else{
				posIncidents.add(item);
			}
		}
	}
	
	

}
