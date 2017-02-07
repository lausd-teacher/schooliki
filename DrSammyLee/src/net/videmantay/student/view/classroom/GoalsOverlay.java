package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoalsOverlay extends Composite {

	private static GoalsOverlayUiBinder uiBinder = GWT.create(GoalsOverlayUiBinder.class);

	interface GoalsOverlayUiBinder extends UiBinder<Widget, GoalsOverlay> {
	}

	public GoalsOverlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
