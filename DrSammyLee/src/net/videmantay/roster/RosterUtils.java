package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.ui.RootPanel;

import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterConfigJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.RoutineConfigJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.routine.json.SeatingChartJson;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.roster.views.ClassroomMain;
import net.videmantay.roster.views.RosterMain;
import net.videmantay.student.json.InfoJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterUtils {

	public final String USER_IMG = "img/user.svg";
	private  final RootPanel root = RootPanel.get();
	private   final RosterMain landingPage;
	private  ClassroomMain classroomPage;
	private final InfoJson info = window.getPropertyJSO("info").cast();
	//too long response for first load ; postpone the iterface
	private boolean classroomFirstLoaded = true;
	
	private  RosterJson currentRoster;
	private  JsArray<RosterJson> rosterList;
	private FullRoutineJson selectedClassTime;
	private JsArray<RoutineJson> classTimes;
	private  boolean isEditMode = false;
	private  boolean isRollMode = false;
	private  JsArray<RosterStudentJson> students;
	private SeatingChartJson seatingChart;
	private JsArray<IncidentJson> incidents;
	
	

	
	public RosterUtils(){
		landingPage = new RosterMain(this);
	}

	
	public boolean isClassFirstLoad(){
		return classroomFirstLoaded;
	}
	
	public void setClassFirstLoad(boolean loaded){
		this.classroomFirstLoaded = loaded;
	}
	public InfoJson getInfo(){
		return this.info;
	}
	public  JsArray<RosterJson> getRosterList(){
		return rosterList;
	}
	
	public  void setRosterList(JsArray<RosterJson> rosList){
		this.rosterList = rosList;
	}
	public   void setCurrentRoster(final RosterJson current) {
		console.log("current roster is set");
		console.log(current);
		this.currentRoster = current;
		//set rosterId to window;
		Properties wnd = window.cast();
		
		wnd.set("roster", this.currentRoster);
		console.log(wnd.get("roster"));
	}
	
	public  RosterJson getCurrentRoster(){
		return currentRoster;
	}
	
	public  void setSelectedClassTime(final FullRoutineJson sct) {
		if(this.selectedClassTime == null){
			this.selectedClassTime = sct;
			return;
		}
		if(this.selectedClassTime != null && this.selectedClassTime.getRoutine() != null 
				&& this.selectedClassTime.getRoutine().getId() != null
				&& this.selectedClassTime.getRoutine().getId() == sct.getRoutine().getId()){
			return;
		}
		// must get the config from server and added it to then window as well
		//if this is just a temporary classtime just skip ajax call
		MaterialLoader.showLoading(true);
		Ajax.get(RosterUrl.classtime(currentRoster.getId(), selectedClassTime.getRoutine().getId()))
		.done(new Function(){
			@Override
			public void f(){
				FullRoutineJson ctc =  JsonUtils.safeEval((String)this.arguments(0)).cast();
				selectedClassTime = ctc;
				$(body).trigger(RosterEvent.updateClassTimeConfig, ctc);
			}
		});

		
	}
	
	public  FullRoutineJson getSelectedClassTime() {
		return selectedClassTime;
	}
	
	public JsArray<RoutineJson> getClassTimes(){
		return classTimes;
	}
	
	public void setClassTimes(JsArray<RoutineJson> times){
		this.classTimes = times;
	}
	
	public  void showLandingPage(){
		
		root.clear();
		root.add(landingPage);
		landingPage.rosters();
	}
	
	public RosterMain getLandingPage(){
		return landingPage;
	}
	
	
	public  void showClassroomPage(){
		root.clear();
		root.add(classroomPage);
	}
	
	public ClassroomMain getClassroomPage(){
		return classroomPage;
	}
	
	public void setClassroomPage(ClassroomMain classroom){
		this.classroomPage = classroom;
	}
	
	public void setStudents(JsArray<RosterStudentJson> stus){
		this.students = stus;
	}
	
	public JsArray<RosterStudentJson> getStudents(){
		return this.students;
	}
	
	public  RosterStudentJson findStudentById(String id){
		if(students == null){
			//get students
		}
		for(int i = 0; i < students.length(); i++){
			if(students.get(i).getId().equals(id)){
				return students.get(i);
			}
		}
		return null;
	}
	
	public void setSeatingChart(SeatingChartJson seatingChart){
		this.seatingChart = seatingChart;
	}
	
	public SeatingChartJson getSeatingChart(){
		return this.seatingChart;
	}
	
	public JsArray<IncidentJson> getIncidents(){
		
		return this.incidents;
	}
	
	public void setIncidents(JsArray<IncidentJson> incidents){
		this.incidents = incidents;
	}
	
	
}
