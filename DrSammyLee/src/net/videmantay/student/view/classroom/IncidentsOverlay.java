package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IncidentsOverlay extends Composite {

	private static IncidentsOverlayUiBinder uiBinder = GWT.create(IncidentsOverlayUiBinder.class);

	interface IncidentsOverlayUiBinder extends UiBinder<Widget, IncidentsOverlay> {
	}

	public IncidentsOverlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
