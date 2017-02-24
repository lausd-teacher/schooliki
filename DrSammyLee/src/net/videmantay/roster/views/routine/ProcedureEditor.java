package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProcedureEditor extends Composite {

	private static ProcedureEditorUiBinder uiBinder = GWT.create(ProcedureEditorUiBinder.class);

	interface ProcedureEditorUiBinder extends UiBinder<Widget, ProcedureEditor> {
	}

	public ProcedureEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
