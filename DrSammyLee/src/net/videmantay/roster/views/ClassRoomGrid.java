package net.videmantay.roster.views;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.FirstNameCompare;
import net.videmantay.roster.LastNameCompare;
import net.videmantay.roster.RosterUtils;

import java.util.Comparator;

import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Collections;

import com.google.common.primitives.Longs;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;


public class ClassRoomGrid extends Composite implements HasClassroomDashboardView{

	private  RosterUtils utils;
	private final MaterialPanel mainPanel = new MaterialPanel();
	private  RosterJson roster;
	private  JsArray<RosterStudentJson> students;
	private final ArrayList<RosterStudentJson> studentList = new ArrayList<RosterStudentJson>();
	//private final StudentActionModal stuModal ;
	private boolean sortByFirst=true;
	
	
	public ClassRoomGrid(){	
		this.initWidget(mainPanel);
	}
	
	public void setData(RosterUtils ru){
		utils = ru;
		this.roster = utils.getCurrentRoster();
		students = utils.getStudents();
		//stuModal = new StudentActionModal(utils);
		if(students ==null ||students.length() <= 0){
			showEmpty();
		}else{
		for(int i = 0; i < students.length(); i++){
			studentList.add(students.get(i));
		}
		drawGrid(sortByFirst);
		}//end if else	
	}
	
	
	public void showEmpty(){
		HTMLPanel empty = new HTMLPanel("<h3>Your student list appears to be empty...</h3>"+
	                                     "<p>To manager you students just open the side menu"+
				                       "and click on students<p><p><a href='#students'>Just click here</a></p>");
		empty.setStylePrimaryName("emptyClassroom");
		$(empty).css($$("margin:2em, color:DimGray"));
		mainPanel.clear();
		mainPanel.add(empty);
	}
	
	public void drawGrid(){
		mainPanel.clear();
		MaterialRow row = new MaterialRow();
		mainPanel.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
		if(sortByFirst){
			Collections.sort(studentList, new FirstNameCompare());
		}else{
			Collections.sort(studentList, new LastNameCompare());
		}
		
		console.log(studentList);
			int i = 0;
			console.log("student 0 is:");
			console.log(studentList.get(0));
				do{
					 c = new MaterialColumn();
					 rsp = new RosterStudentPanel(studentList.get(i));
					 rsp.addStyleName("grid");
					 c.add(rsp);
					 ++i;
					 row.add(c);
				}while(i < studentList.size());
			
		
	}
	
	public void drawGrid(boolean firstName){
		sortByFirst = firstName;
		drawGrid();
	}
	
	public void reverse(){
		Collections.reverse(studentList);
		MaterialRow row = new MaterialRow();
		mainPanel.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
		int i = 0;
		do{
			 c = new MaterialColumn();
			 rsp = new RosterStudentPanel(studentList.get(i));
			 c.add(rsp);
			 ++i;
			 if(i%6==0){
				 row = new MaterialRow();
				 mainPanel.add(row);
			 }
			 row.add(c);
		}while(i < studentList.size());
		
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
		$(".rosterStudent").click(new Function(){
			@Override
			public void f(){
				
				
				RosterStudentJson student = null;
				for(int i = 0; i < students.length(); i++){
					if($(this).id().equals(students.get(i).getId())){
						student =students.get(i);
						break;
					}
				}
				
				JsArray<RosterStudentJson> students = JsArray.createArray().cast();
				students.push(student);
			}
		});
		
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