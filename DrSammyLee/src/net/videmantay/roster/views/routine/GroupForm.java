package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

public class GroupForm extends Composite {

	private static GroupFormUiBinder uiBinder = GWT.create(GroupFormUiBinder.class);

	interface GroupFormUiBinder extends UiBinder<Widget, GroupForm> {
	}

	@UiField
	public MaterialListBox groupType;
	
	@UiField
	MaterialRow nameRow;
	
	@UiField
	MaterialRow rti2Row;
	
	@UiField
	MaterialRow criteriaRow;
	
	@UiField
	MaterialTextBox rti2Search;
	
	@UiField
	MaterialIntegerBox rti2TestQuantity;
	
	@UiField
	MaterialListBox testType;
	
	@UiField
	MaterialColumn quantityCol;
	
	@UiField
	MaterialColumn searchWrap;
	
	@UiField
	MaterialColumn studentSection;
	
	private final ValueChangeHandler<String> groupTypeHandler = new ValueChangeHandler<String>(){

		@Override
		public  void onValueChange(ValueChangeEvent<String> event) {
			switch(event.getValue()){
			case "name": showNameOpt();break;
			case "rti2": showRTI2Opt(); break;
			case "eldLevel":showELDOpt(); break;
			default: showCustomOpt();break;
			}
		}};
		
	private final ValueChangeHandler<String> testTypeHandler = new ValueChangeHandler<String>(){

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			switch(event.getValue()){
			case "latest": showLatest();break;
			default: showSpecific();
			}
			
		}};
	public GroupForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//add handlers
		groupType.addValueChangeHandler(groupTypeHandler);
		testType.addValueChangeHandler(testTypeHandler);
	}
	
	private void showNameOpt(){
		hideAllOpts();
		nameRow.setVisible(true);
		studentSection.setVisible(false);
	}
	
	private void showRTI2Opt(){
		hideAllOpts();
		rti2Row.setVisible(true);
		studentSection.setVisible(false);
	}
	private void showELDOpt(){
		hideAllOpts();
		studentSection.setVisible(false);
	}
	private void showCustomOpt(){
		hideAllOpts();
		criteriaRow.setVisible(true);
		studentSection.setVisible(true);
	}
	private void hideAllOpts(){
	$(".paramRow").hide();
	}
	
	private void hideTestCriteria(){
		$(".testCriteria").hide();
	}
	private void showLatest(){
		hideTestCriteria();
		quantityCol.setVisible(true);
		
		
	}
	private void showSpecific(){
		hideTestCriteria();
		searchWrap.setVisible(true);
	}
	
	public void clearAll(){
		rti2TestQuantity.clear();
		rti2Search.clear();
		groupType.reset();
		testType.reset();
		hideTestCriteria();
		hideAllOpts();
	}

}
