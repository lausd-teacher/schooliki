package net.videmantay.roster.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProcedureEditorItem extends Composite {

	private static ProcedureEditorItemUiBinder uiBinder = GWT.create(ProcedureEditorItemUiBinder.class);

	interface ProcedureEditorItemUiBinder extends UiBinder<Widget, ProcedureEditorItem> {
	}

	public ProcedureEditorItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
