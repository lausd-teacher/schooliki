package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StationEditor extends Composite {

	private static StationEditorUiBinder uiBinder = GWT.create(StationEditorUiBinder.class);

	interface StationEditorUiBinder extends UiBinder<Widget, StationEditor> {
	}

	public StationEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
