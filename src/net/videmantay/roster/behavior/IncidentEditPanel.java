package net.videmantay.roster.behavior;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IncidentEditPanel extends Composite {

	private static IncidentEditPanelUiBinder uiBinder = GWT.create(IncidentEditPanelUiBinder.class);

	interface IncidentEditPanelUiBinder extends UiBinder<Widget, IncidentEditPanel> {
	}

	public IncidentEditPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
