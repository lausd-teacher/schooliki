package net.videmantay.roster.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GroupItem extends Composite {

	private static GroupItemUiBinder uiBinder = GWT.create(GroupItemUiBinder.class);

	interface GroupItemUiBinder extends UiBinder<Widget, GroupItem> {
	}

	public GroupItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
