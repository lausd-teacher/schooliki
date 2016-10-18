package net.videmantay.roster.views.incident;

import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	@UiField
	MaterialRow posIncidentRow;
	
	@UiField
	MaterialRow negIncidentRow;
	
	@UiField
	HTMLPanel container;
	
	@UiField
	MaterialFAB addIncidentFAB;
	
	IncidentForm incidentForm;
	
	
	public IncidentMain(IncidentForm incidentForm) {
		initWidget(uiBinder.createAndBindUi(this));
		this.incidentForm = incidentForm;
		container.add(incidentForm);
	}
	
	@Override
	public void onLoad(){
		RosterJson roster = window.getPropertyJSO("roster").cast();
		for(int i = 0; i < roster.getIncidents().length(); i++){
			IncidentJson incident = roster.getIncidents().get(i);
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
		
//		addIncidentFAB.addClickHandler(new ClickHandler(){
//			@Override
//			public void onClick(ClickEvent event) {
//				incidentForm.show();
//			}
//		});
	}
	
	public MaterialFAB getAddIncidentFAB() {
		return this.addIncidentFAB;
	}

	public IncidentForm getIncidentForm() {
		return this.incidentForm;
	}

	public interface Presenter{
		void addIncidentFABButtonClickEvent();	
	}

}
