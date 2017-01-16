package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Promise;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialFAB;
import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.RosterUtils;

public class ClassroomDisplay extends Composite implements HasClassroomDashboardView{

	private static ClassroomDisplayUiBinder uiBinder = GWT.create(ClassroomDisplayUiBinder.class);

	interface ClassroomDisplayUiBinder extends UiBinder<Widget, ClassroomDisplay> {
	}

	@UiField
	public ClassRoomGrid  classroomGrid;
	
	@UiField
	ClassroomForm classForm;
	
	@UiField
	public MaterialFAB fab;
	
	private final RosterUtils utils;
	
	public ClassroomDisplay(RosterUtils ru) {
		initWidget(uiBinder.createAndBindUi(this));
		utils = ru;
		classroomGrid.setData(utils);
		
		//addclick handlers
		fab.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				classroomGrid.setVisible(false);
				classForm.setVisible(true);
				
			}});
		
		classForm.cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			classForm.cancel();
			classForm.setVisible(false);
			classroomGrid.setVisible(true);
				
			}});
		
		classForm.submitBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			Promise promise = 	classForm.submit(utils);
				
			}});
	}//end construct

	@Override
	public void checkHW() {
		classroomGrid.checkHW();
		
	}

	@Override
	public void groups() {
		classroomGrid.groups();
		
	}

	@Override
	public void takeRoll() {
		classroomGrid.takeRoll();
		
	}

	@Override
	public void pickRandom() {
		classroomGrid.pickRandom();
		
	}

	@Override
	public void selectAll() {
		classroomGrid.selectAll();
		
	}

	@Override
	public void multipleSelect() {
		classroomGrid.multipleSelect();
		
	}

	@Override
	public void home() {
		classroomGrid.home();
		
	}

	@Override
	public void unHome() {
		classroomGrid.unHome();
		
	}

	@Override
	public void arrangeFurniture() {
	classroomGrid.arrangeFurniture();
		
	}

	@Override
	public void arrangeStudents() {
		classroomGrid.arrangeStudents();
		
	}

	@Override
	public void manageStations() {
		classroomGrid.manageStations();
		
	}

	@Override
	public void doneCheckHW() {
		 classroomGrid.doneCheckHW();
		
	}

	@Override
	public void doneGroups() {
		classroomGrid.doneGroups();
		
	}

	@Override
	public void doneTakeRoll() {
		classroomGrid.doneTakeRoll();
		
	}

	@Override
	public void donePickRandom() {
		classroomGrid.donePickRandom();
		
	}

	@Override
	public void doneSelectAll() {
		classroomGrid.doneSelectAll();
		
	}

	@Override
	public void undo() {
		classroomGrid.undo();
		
	}

	@Override
	public void cancel(String state) {
		classroomGrid.cancel(state);
		
	}

	@Override
	public void deselectAll() {
	classroomGrid.deselectAll();
		
	}

	@Override
	public void doneMultipleSelect() {
		classroomGrid.doneMultipleSelect();
		
	}

	@Override
	public void doneArrangeFurniture() {
		classroomGrid.doneArrangeFurniture();
		
	}

	@Override
	public void doneArrangeStudents() {
		classroomGrid.doneArrangeFurniture();
		
	}

	@Override
	public void doneManageStations() {
		classroomGrid.doneManageStations();
		
	}

}
