package net.videmantay.roster.job;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JobGrid extends Composite {

	private static JobGridUiBinder uiBinder = GWT.create(JobGridUiBinder.class);

	interface JobGridUiBinder extends UiBinder<Widget, JobGrid> {
	}

	public JobGrid() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
