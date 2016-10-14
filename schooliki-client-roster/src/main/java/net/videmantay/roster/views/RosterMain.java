package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialTooltip;

public class RosterMain extends Composite {

	
	private static RosterMainUiBinder uiBinder = GWT.create(RosterMainUiBinder.class);

	interface RosterMainUiBinder extends UiBinder<Widget, RosterMain> {
	}
	
	@UiField
	MaterialContainer mainPanel;
	
	@UiField
	MaterialSideNav sideNav;

	@UiField
	MaterialLink rosterLink;
	
	@UiField
	MaterialLink calendarLink;
	
	@UiField
	MaterialLink libraryLink;
	
	@UiField
	MaterialLink lessonsLink;
	
	@UiField
	MaterialTooltip calendarTooltip;
	
	@UiField
	MaterialTooltip todoTooltip;
	
	@UiField
	MaterialTooltip notificationTooltip;

	
	
	public RosterMain(RosterDisplay rosterList) {
		initWidget(uiBinder.createAndBindUi(this));
		mainPanel.clear();
		mainPanel.add(rosterList);
	}
	
	public MaterialContainer getMainPanel() {
		return this.mainPanel;
	}
		
  
}
