package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.*;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	@UiField
	MaterialRow posIncidentRow;
	
	@UiField
	MaterialRow negIncidentRow;
	
	@UiField
	IncidentForm incidentForm;
	
	@UiField
	MaterialFAB addIncidentFAB;
	
	Function openForm = new Function(){
		@Override
		public void f(){
			incidentForm.show();
		}
	};
	
	Function openDeleteModal = new Function(){
		@Override
		public void f(){
			//deleteModal.show();
		}
	};
	
	
	public IncidentMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}//end constr.
	
	@Override
	public void onLoad(){
		RosterJson roster = window.getPropertyJSO("roster").cast();
		for(int i = 0; i < roster.getIncidents().length(); i++){
			IncidentJson incident = roster.getIncidents().get(i);
			//EditIncidentPanel ip = new EditIncidentPanel().setIncident(incident);
			//console.log(ip);
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s2 m4 l2");
			if(incident.getValue() < 0){
				negIncidentRow.add(col);
			}else{
				posIncidentRow.add(col);
			}//end else
			
			//col.add(ip);
		}//end for
		
		//$(body).on("editIncident", openForm);
		//$(body).on("deleteIncident", openDeleteModal);
		
		addIncidentFAB.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				incidentForm.show();
			}
		});
	}

}
