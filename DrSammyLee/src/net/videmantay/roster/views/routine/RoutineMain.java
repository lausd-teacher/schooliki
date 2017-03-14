package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.routine.json.SeatingChartJson;

import static com.google.gwt.query.client.GQuery.*;

public class RoutineMain extends Composite {

	/* this class is a list of the combined classtime and classtimeconfig
	 * JsArray<FullRoutineJson> will be called here.
	 */
		
	private static RoutineMainUiBinder uiBinder = GWT.create(RoutineMainUiBinder.class);

	interface RoutineMainUiBinder extends UiBinder<Widget, RoutineMain> {
	}

	@UiField
	MaterialRow routineGrid;
	
	@UiField
	MaterialAnchorButton createBtn;
	
	@UiField
	DivElement routineList;
	
	@UiField
	DivElement routineFormWrapper;
	
	@UiField(provided=true)
	 RoutineForm routineForm;
	
	@UiField
	 MaterialModal routineDeleteModal;
	
	@UiField
	 MaterialAnchorButton routineDeleteOkBtn;
	
	@UiField
	 MaterialAnchorButton routineDeleteCancelBtn;
	
	//final click handler
	ClickHandler createBtnHandler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event){
			routineForm.setData(null);
			showForm();
			
		}
	};
	ClickHandler formSubmit = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			routineForm.submit();
			hideForm();
			
		}};
	ClickHandler formCancel = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			routineForm.cancel();
			hideForm();
			
		}};
		
	ClickHandler routineDeleteOkHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			//some ajax for deleting
			routineDeleteModal.closeModal();
		}};
	ClickHandler routineDeleteCancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			routineDeleteModal.closeModal();
		}};
	private void showForm(){
			$(routineList).css("display", "none");
			$(routineFormWrapper).css("display", "block");
		};	
	
	private void hideForm(){
		
			$(routineList).css("display", "block");
			$(routineFormWrapper).css("display", "none");	
		};
		
	final RosterUtils utils;
	public RoutineMain(RosterUtils ru) {
		utils = ru;
		routineForm = new RoutineForm(utils);
		initWidget(uiBinder.createAndBindUi(this));
		RoutineGridItem activeItem = new RoutineGridItem();
		activeItem.setData(utils.getSelectedClassTime());
		activeItem.addStyleName("activeClassTime");
		
		MaterialColumn cardCol = new MaterialColumn();
		cardCol.setGrid("s12");
		cardCol.add(activeItem);
		routineGrid.add(cardCol);
		if(utils.getClassTimes().length() > 1){
		for(int i = 0; i < utils.getClassTimes().length(); i++){
			cardCol = new MaterialColumn();
			cardCol.setGrid("s12");
			RoutineGridItem item = new RoutineGridItem();
			FullRoutineJson fct = FullRoutineJson.createObject().cast();
			fct.setRoutine(utils.getClassTimes().get(i));
			item.setData(fct);
			cardCol.add(item);
			routineGrid.add(cardCol);
		}
		}
		//buttons click handlers///
		createBtn.addClickHandler(this.createBtnHandler);
		routineForm.cancelBtn.addClickHandler(formCancel);
		routineForm.submitBtn.addClickHandler(formSubmit);
		routineDeleteOkBtn.addClickHandler(routineDeleteOkHandler);
		routineDeleteCancelBtn.addClickHandler(routineDeleteCancelHandler);
		
	}
	
	@Override
	public void onLoad(){
		$("a.routineItem-edit").click(new Function(){
			@Override
			public boolean f(Event e){
				e.stopPropagation();
				e.preventDefault();
				FullRoutineJson frj = $(this).closest(".routineItem").data("routine", FullRoutineJson.class);
	
				routineForm.setData(frj);
				showForm();
				return true;
			}
		});
		
		$("a.routineItem-delete").click(new Function(){
			@Override
			public boolean f(Event e){
				e.stopPropagation();
				e.preventDefault();
				FullRoutineJson frj = $(this).closest(".routineItem").data("routine", FullRoutineJson.class);
				$(routineDeleteModal).data("routine", frj);
				routineDeleteModal.openModal();
				return true;
			}
		});
	}

}
