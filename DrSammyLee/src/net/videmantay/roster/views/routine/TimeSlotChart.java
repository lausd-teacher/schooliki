package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import net.videmantay.roster.routine.json.StationManagerJson;

public class TimeSlotChart extends Composite {

	private static TimeSlotChartUiBinder uiBinder = GWT.create(TimeSlotChartUiBinder.class);

	interface TimeSlotChartUiBinder extends UiBinder<Widget, TimeSlotChart> {
	}

	
	@UiField
	public MaterialCollection stationCollection;
	
	@UiField
	HTMLPanel timeSlotChart;
	

	public TimeSlotChart() {
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	
	
		
		


}
