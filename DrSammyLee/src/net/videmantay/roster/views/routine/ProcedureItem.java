package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;

public class ProcedureItem extends Composite {

	private static ProcedureItemUiBinder uiBinder = GWT.create(ProcedureItemUiBinder.class);

	interface ProcedureItemUiBinder extends UiBinder<Widget, ProcedureItem> {
	}

	public ProcedureItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	public MaterialChip numChip;
	
	@UiField
	public MaterialRichEditor editor;
	
	@UiField
	public MaterialIcon deleteIcon;

}
