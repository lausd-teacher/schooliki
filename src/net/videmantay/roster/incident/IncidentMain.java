package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IncidentMain extends Composite {

	private static IncidentMainUiBinder uiBinder = GWT.create(IncidentMainUiBinder.class);

	interface IncidentMainUiBinder extends UiBinder<Widget, IncidentMain> {
	}

	public IncidentMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
