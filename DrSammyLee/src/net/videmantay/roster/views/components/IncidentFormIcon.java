package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class IncidentFormIcon extends Composite {

	private static IncidentFormIconUiBinder uiBinder = GWT.create(IncidentFormIconUiBinder.class);

	interface IncidentFormIconUiBinder extends UiBinder<Widget, IncidentFormIcon> {
	}
	
	@UiField
	ImageElement incidentIcon;
	
	@UiField
	HTMLPanel container;

	public IncidentFormIcon(String url) {
		initWidget(uiBinder.createAndBindUi(this));
		container.getElement().addClassName("incidentFormIcon");
		incidentIcon.setSrc(url);
	}
	
	public ImageElement getIncidentIcon() {
		return this.incidentIcon;
	}	
	
}
