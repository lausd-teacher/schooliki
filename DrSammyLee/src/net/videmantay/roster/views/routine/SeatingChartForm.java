package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SeatingChartForm extends Composite {

	private static SeatingChartFormUiBinder uiBinder = GWT.create(SeatingChartFormUiBinder.class);

	interface SeatingChartFormUiBinder extends UiBinder<Widget, SeatingChartForm> {
	}

	public SeatingChartForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
