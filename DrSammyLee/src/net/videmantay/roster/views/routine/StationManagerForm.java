package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StationManagerForm extends Composite {

	private static StationManagerFormUiBinder uiBinder = GWT.create(StationManagerFormUiBinder.class);

	interface StationManagerFormUiBinder extends UiBinder<Widget, StationManagerForm> {
	}

	public StationManagerForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
