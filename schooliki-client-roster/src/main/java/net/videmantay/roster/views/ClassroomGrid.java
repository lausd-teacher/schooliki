package net.videmantay.roster.views;

import com.google.gwt.dom.client.Style.Unit;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialFAB;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.student.CreateStudentForm;


public class ClassroomGrid extends MaterialContainer implements HasRosterDashboardView{


	private final StudentActionModal stuModal;
	private final CreateStudentForm createStudentFrom;
	private MaterialFAB fab = new MaterialFAB();
	private MaterialAnchorButton addButton = new MaterialAnchorButton(ButtonType.FLOATING);
	
	
	public ClassroomGrid(CreateStudentForm form, StudentActionModal modal){	
		this.stuModal = modal;
		this.createStudentFrom = form;
		addButton.setIconType(IconType.ADD);
		addButton.setIconSize(IconSize.LARGE);
		addButton.setIconFontSize(2, Unit.EM);
		fab.add(addButton);
		this.add(fab);
		this.add(createStudentFrom);
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
	
	public StudentActionModal getStuModal() {
		return this.stuModal;
	}

	public CreateStudentForm getCreateStudentFrom() {
		return this.createStudentFrom;
	}
	
	@Override
	public void unHome(){

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
	
	
	public interface Presenter{
		void addStudentButtonClickEvent();
	}
	


	
	
}
