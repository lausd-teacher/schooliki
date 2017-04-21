package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.*;

import gwt.material.design.addins.client.richeditor.MaterialRichEditor;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRow;

public class ProcedureForm extends Composite {

	private static ProcedureFormUiBinder uiBinder = GWT.create(ProcedureFormUiBinder.class);

	interface ProcedureFormUiBinder extends UiBinder<Widget, ProcedureForm> {
	}
	
	@UiField
	public HTMLPanel procedureList;
	@UiField
	public MaterialButton newProcBtn;
	

	//click handler
	ClickHandler deleteProcHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
		GQuery $deleteIcon = $(event.getSource());
		GQuery $li = $deleteIcon.closest(".procedureItem");
		//need access to the  array or send a trigger//
		$li.remove();
		
		GQuery $items = $(".procedureItem",procedureList);
		for(int i = 0; i < $items.length();i++){
			ProcedureItem item = $( $items.get(i)).widget();
			item.numChip.setText((1+ i) + "");
		}
		
		}};
		
	ClickHandler addProcHandler = new ClickHandler(){
		

		@Override
		public void onClick(ClickEvent event) {
			addProcItem(null);
			
		}
	};

	public ProcedureForm() {
		initWidget(uiBinder.createAndBindUi(this));
		newProcBtn.addClickHandler(addProcHandler);
		
	}
	

	
	public void addProcItem(String proc){
		ProcedureItem item = new ProcedureItem();
		//get the number
		int num = $(".procedureItem").length();
		
		if(proc == null || proc.isEmpty()){
			item.editor.setPlaceholder("describe the next step in the process");
		}else{
			item.editor.setHTML(proc);
		}
		item.numChip.setText((num + 1) +"");
		item.deleteIcon.addClickHandler(deleteProcHandler);
		procedureList.add(item);
	}

}
