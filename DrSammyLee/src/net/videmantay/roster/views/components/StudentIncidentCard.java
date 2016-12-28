package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;

public class StudentIncidentCard extends Composite {

	private static StudentIncidentCardUiBinder uiBinder = GWT.create(StudentIncidentCardUiBinder.class);

	interface StudentIncidentCardUiBinder extends UiBinder<Widget, StudentIncidentCard> {
	}
	
	@UiField
    MaterialCard container;
	
	@UiField
    MaterialLabel studentName;
	
	@UiField
    MaterialImage incidentImage;
	
	@UiField
    MaterialLabel incidentName;

	public StudentIncidentCard(String studentName, String incidentImageUrl, String incidentName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.studentName.setText(studentName);
		this.incidentImage.setUrl(incidentImageUrl);
		this.incidentName.setText(incidentName);
		container.setMargin(15);
	}

}
