package net.videmantay.roster.views.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class AssignementDashboard extends Composite {

	private static AssignementDashboardUiBinder uiBinder = GWT.create(AssignementDashboardUiBinder.class);

	interface AssignementDashboardUiBinder extends UiBinder<Widget, AssignementDashboard> {
	}
	
	@UiField
	HTMLPanel assignementsTabContent;

	public AssignementDashboard(GradedWorkMain gradedWork) {
		initWidget(uiBinder.createAndBindUi(this));
		assignementsTabContent.add(gradedWork);
	}

}
