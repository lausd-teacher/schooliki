package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;

public class ProcedureEditorItem extends Composite {

	private static ProcedureEditorItemUiBinder uiBinder = GWT.create(ProcedureEditorItemUiBinder.class);

	interface ProcedureEditorItemUiBinder extends UiBinder<Widget, ProcedureEditorItem> {
	}

	public ProcedureEditorItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	MaterialCardTitle procedureName;

}
