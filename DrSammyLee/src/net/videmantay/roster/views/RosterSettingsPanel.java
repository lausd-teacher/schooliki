package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RosterSettingsPanel extends Composite {

	private static RosterSettingsPanelUiBinder uiBinder = GWT.create(RosterSettingsPanelUiBinder.class);

	interface RosterSettingsPanelUiBinder extends UiBinder<Widget, RosterSettingsPanel> {
	}

	public RosterSettingsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
