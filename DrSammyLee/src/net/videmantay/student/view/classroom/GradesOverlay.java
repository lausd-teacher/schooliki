package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GradesOverlay extends Composite {

	private static GradesOverlayUiBinder uiBinder = GWT.create(GradesOverlayUiBinder.class);

	interface GradesOverlayUiBinder extends UiBinder<Widget, GradesOverlay> {
	}

	public GradesOverlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
