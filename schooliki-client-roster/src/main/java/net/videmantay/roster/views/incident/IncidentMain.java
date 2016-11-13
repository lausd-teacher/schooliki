package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialRow;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	
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
