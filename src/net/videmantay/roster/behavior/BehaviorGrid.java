package net.videmantay.roster.behavior;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BehaviorGrid extends Composite {

	private static BehaviorGridUiBinder uiBinder = GWT.create(BehaviorGridUiBinder.class);

	interface BehaviorGridUiBinder extends UiBinder<Widget, BehaviorGrid> {
	}

	public BehaviorGrid() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
