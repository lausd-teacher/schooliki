package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;

public class IncidentEditPanel extends Composite {

	private static IncidentEditPanelUiBinder uiBinder = GWT.create(IncidentEditPanelUiBinder.class);

	interface IncidentEditPanelUiBinder extends UiBinder<Widget, IncidentEditPanel> {
	}
	
	public IncidentEditPanel(){
		uiBinder.createAndBindUi(this);
		
	}
}