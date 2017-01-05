package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AllCalendarsPanel extends Composite {

	private static AllCalendarsPanelUiBinder uiBinder = GWT.create(AllCalendarsPanelUiBinder.class);

	interface AllCalendarsPanelUiBinder extends UiBinder<Widget, AllCalendarsPanel> {
	}

	public AllCalendarsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
