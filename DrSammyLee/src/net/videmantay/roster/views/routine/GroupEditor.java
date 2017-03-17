package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GroupEditor extends Composite {

	private static GroupEditorUiBinder uiBinder = GWT.create(GroupEditorUiBinder.class);

	interface GroupEditorUiBinder extends UiBinder<Widget, GroupEditor> {
	}

	public GroupEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
