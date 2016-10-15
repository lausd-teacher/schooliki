package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTooltip;

public class MainRosterNavBar extends Composite {



	private static MainRosterNavBarUiBinder uiBinder = GWT.create(MainRosterNavBarUiBinder.class);

	interface MainRosterNavBarUiBinder extends UiBinder<Widget, MainRosterNavBar> {
	}
	
	@UiField
	MaterialTooltip calendarTooltip;
	
	@UiField
	MaterialTooltip todoTooltip;
	
	@UiField
	MaterialTooltip notificationTooltip;

	public MainRosterNavBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MaterialTooltip getCalendarTooltip() {
		return this.calendarTooltip;
	}

	public MaterialTooltip getTodoTooltip() {
		return this.todoTooltip;
	}

	public MaterialTooltip getNotificationTooltip() {
		return this.notificationTooltip;
	}

}
