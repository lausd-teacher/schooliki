package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.user.client.ui.Composite;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialFAB;
import net.videmantay.roster.HasRosterDashboardView;


public class ClassroomGrid extends Composite implements HasRosterDashboardView{

	



	private final StudentActionModal stuModal = new StudentActionModal();
	private MaterialFAB fab = new MaterialFAB();
	private MaterialAnchorButton addButton = new MaterialAnchorButton(ButtonType.FLOATING);
	
	
	public ClassroomGrid(){		
		
	
	}
	
	@Override
	public void onLoad(){
		home();
	}
	

	@Override
	public void checkHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void home() {
		//right now home is for clicking on students
		//and generating a dialog for behavior management or
		// seeing more info on that student will calll it student action dialog
//		$(".rosterStudent").click(new Function(){
//			@Override
//			public void f(){
//				
//				Long studentId = Longs.tryParse($(this).id());
//				RosterStudentJson student = null;
//				for(int i = 0; i < roster.getRosterStudents().length(); i++){
//					if(studentId == roster.getRosterStudents().get(i).getId()){
//						student = roster.getRosterStudents().get(i);
//						break;
//					}
//				}
//				
//				JsArray<RosterStudentJson> students = JsArray.createArray().cast();
//				students.push(student);
//				stuModal.loadData(ActionType.SINGLE	, students);
//				stuModal.show();
//			}
//		});
		
	}
	
	

	public MaterialFAB getFab() {
		return this.fab;
	}

	public MaterialAnchorButton getAddButton() {
		return this.addButton;
	}
	
	@Override
	public void unHome(){
		$(".rosterStudent").off("click");
	}

	@Override
	public void arrangeFurniture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneCheckHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneGroups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneTakeRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void donePickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneMultipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeFurniture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeStudents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeStudents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(final String state) {
		// TODO Auto-generated method stub
		
	}
	


	
	
}
