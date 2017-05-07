package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import java.util.List;

import com.google.gwt.query.client.Promise;
import  com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.JoinRequestJson;
import net.videmantay.roster.json.JoinStatus;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentAttendanceJson;

public class ClassroomDisplay extends Composite implements HasClassroomDashboardView{

	private static ClassroomDisplayUiBinder uiBinder = GWT.create(ClassroomDisplayUiBinder.class);

	interface ClassroomDisplayUiBinder extends UiBinder<Widget, ClassroomDisplay> {
	}

	private final RosterUtils utils; 
	
	@UiField
	public MaterialPanel  classroomGrid;
	
	@UiField(provided=true)
	ClassroomForm classForm;
	
	@UiField
	public MaterialFAB fab;
	
	
	
	public ClassroomDisplay(RosterUtils ru) {
		utils = ru;
		classForm = new ClassroomForm(utils);
		initWidget(uiBinder.createAndBindUi(this));
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
				console.log("class form submit called");
			Promise promise = 	classForm.submit();
			classForm.setVisible(false);
			if(promise == null){
				console.log("promise was null just return");
				return;
			}
			final JsArray<RosterStudentJson> oldStudentList = utils.getStudents();
			promise.done(new Function(){
				@Override
				public  void f(){
				final JsArray<RosterStudentJson> students = this.arguments(0);
				//check for content
				if(students.length() < 1){
					return;
				}else if(oldStudentList.length() == students.length()){//check for change
					//no change means attempt again/// when to stop
					new Timer(){
						@Override
						public void run(){
							utils.setStudents(students);
							drawGrid();
							MaterialLoader.showLoading(false);
						}
					}.schedule(2000);
					MaterialLoader.showLoading(true);
					return;
				}else{//now we can really do something
					utils.setStudents(students);
					drawGrid();
					
				}
				}
			});
				
			}});
		
		//populate with fake join request//
		JsArray<JoinRequestJson> jrList = JsArray.createArray().cast();
		for(int i = 0; i < 6; i++){
			JoinRequestJson jr = JoinRequestJson.createObject().cast();
			jr.setEmail("student_" + (i*11)+"@email.com");
			jr.setStatus(JoinStatus.PENDING);
			jrList.push(jr);
		}
		classForm.populateJoinRequests(jrList);	
		drawGrid();
	}//end construct

	@Override
	public void drawGrid(){
		JsArray<RosterStudentJson> studs = utils.getStudents();
		if(studs == null ||studs.length() < 1){
			showEmptyGrid();
			console.log("Show empty students");
			return;
		}
		console.log("drawing classroom grid");
		classroomGrid.clear();
		MaterialRow row = new MaterialRow();
		
		
		classroomGrid.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
			int i = 0;
			console.log("student 0 is:");
			console.log(studs);
			if(studs.length() >0){
				do{
					 c = new MaterialColumn();
					 c.setGrid("s6 m3 l2");
					 rsp = new RosterStudentPanel(studs.get(i));
					 c.add(rsp);
					 ++i;
					 row.add(c);
					 rsp.gridStyle();
				}while(i < studs.length());
			}
		
	}
	
	public void showEmptyGrid(){
		HTMLPanel empty = new HTMLPanel("<h3>Your student list appears to be empty...</h3>"+
                "<p>To manager your students just click the plus button");
			empty.setStylePrimaryName("emptyClassroom");
			$(empty).css($$("margin:2em, color:DimGray"));
			classroomGrid.clear();
			classroomGrid.add(empty);
	}


	
	@Override
	public void checkHW() {
		
		
	}


	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void takeRoll() {
		List<Widget> rosPanels = $(".rosterStudent", classroomGrid).widgets();
		console.log(rosPanels);
		if(!utils.getCurrentAttendance().getCompleted()){
			JsArray<StudentAttendanceJson> studentAttendance = JsArray.createArray().cast();
			
			for( int i =0; i< utils.getStudents().length(); i++){
				studentAttendance.push(StudentAttendanceJson.today(utils.getStudents().get(i).getStudentId()));
			}
			console.log(studentAttendance);
			//set new studentAttendance
			utils.getCurrentAttendance().setStudentAttendance(studentAttendance);
		}
		
		//noew the student should be set
		for(Widget rsp : rosPanels){
			//cycle through attendance
			JsArray<StudentAttendanceJson> stuAtt =utils.getCurrentAttendance().getStudentAttendance();
			for(int i= 0; i < stuAtt.length(); i++){
				if($(rsp).id().equalsIgnoreCase(stuAtt.get(i).getStudentId())){
					((RosterStudentPanel)rsp).attendence(stuAtt.get(i));
						break;
				}//end if
			}//end inner for
		}//end for
		
		
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
	public void home(){
		$(".rosterStudent", classroomGrid).click(new Function(){
			@Override
			public void f(){
				//This is handled in Classroom main in onLoad
				$(body).trigger("studentAction", ((RosterStudentPanel)$(this).widget()).getData());
				
			}
		});
	}


	@Override
	public void unHome() {
		$(".rosterStudent", classroomGrid).off("click");
		
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
	public void undo() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cancel(String state) {
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
	public void onLoad(){
		new Timer(){
			@Override
			public void run(){home();}
		}.schedule(2000);
	}

	@Override
	public boolean isEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doneEditing() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void edit(){
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(State state) {
		// TODO Auto-generated method stub
		
	}
	
}
