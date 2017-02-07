package net.videmantay.student.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JobsOverlay extends Composite {

	private static JobsOverlayUiBinder uiBinder = GWT.create(JobsOverlayUiBinder.class);

	interface JobsOverlayUiBinder extends UiBinder<Widget, JobsOverlay> {
	}

	public JobsOverlay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
