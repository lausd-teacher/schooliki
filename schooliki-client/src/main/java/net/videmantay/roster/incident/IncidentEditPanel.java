package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialPanel;
import net.videmantay.roster.json.IncidentJson;

public class IncidentEditPanel extends Composite {

	private static IncidentEditPanelUiBinder uiBinder = GWT.create(IncidentEditPanelUiBinder.class);

	interface IncidentEditPanelUiBinder extends UiBinder<Widget, IncidentEditPanel> {
	}
	
}