package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;

public class StationChart extends Composite {

	private static StationChartUiBinder uiBinder = GWT.create(StationChartUiBinder.class);

	interface StationChartUiBinder extends UiBinder<Widget, StationChart> {
	}

	public StationChart() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialButton addTimeslotBtn;
}
