package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import net.videmantay.roster.routine.json.StationManagerJson;

public class StationForm extends Composite {

	
	interface StationFormtUiBinder extends UiBinder<Widget, StationForm> {
	}
	
	private static StationFormtUiBinder uiBinder = GWT.create(StationFormtUiBinder.class);

	

	
	
	

	public StationForm() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	
	
		
		


}
