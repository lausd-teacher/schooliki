package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StationPanel extends Composite {

	private static StationPanelUiBinder uiBinder = GWT.create(StationPanelUiBinder.class);

	interface StationPanelUiBinder extends UiBinder<Widget, StationPanel> {
	}

	public StationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
