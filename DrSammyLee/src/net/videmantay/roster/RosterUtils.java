package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;

import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import net.videmantay.roster.classtime.json.ClassTimeConfigJson;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.views.ClassroomMain;
import net.videmantay.roster.views.RosterMain;
import net.videmantay.student.json.InfoJson;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.roster.views.StudentActionModal;

public class RosterUtils {

	private  final RootPanel root = RootPanel.get();
	private   final RosterMain landingPage;
	private  ClassroomMain classroomPage;
	private final InfoJson info = window.getPropertyJSO("info").cast();
	//too long response for first load ; postpone the iterface
	private boolean classroomFirstLoaded = true;
	
	private  RosterJson currentRoster;
	private  JsArray<RosterJson> rosterList;
	private  ClassTimeJson selectedClassTime;
	private ClassTimeConfigJson classtimeConfig;
	private JsArray<ClassTimeJson> classTimes;
	private  boolean isEditMode = false;
	private  boolean isRollMode = false;
	private  JsArray<RosterStudentJson> students;
	private  StudentActionModal studentActionModal;
	private ClassTimeConfigJson defaultTime;
	
	

	
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
		//make a copy of the roster /// geeze loise
		//this.currentRoster = JsonUtils.safeEval(JsonUtils.stringify(current)).cast();
		this.currentRoster = current;
		//set rosterId to window;
		Properties wnd = window.cast();
		
		wnd.set("roster", this.currentRoster);
		console.log(wnd.get("roster"));
	}
	
	public  RosterJson getCurrentRoster(){
		return currentRoster;
	}
	
	public  void setSelectedClassTime(ClassTimeJson sct) {
		if(selectedClassTime == sct){
			return;
		}
		selectedClassTime = sct;
		$(window).prop("selectedClassTime", selectedClassTime);
		// must get the config from server and added it to then window as well
		//if this is just a temporary classtime just skip ajax call
		if(selectedClassTime.getId() != null){
		Ajax.get(RosterUrl.classtimeconfig(currentRoster.getId(), selectedClassTime.getId()))
		.done(new Function(){
			@Override
			public void f(){
				ClassTimeConfigJson ctc =  JsonUtils.safeEval((String)this.arguments(0)).cast();
				//fire ctc change event
				$(body).trigger(RosterEvent.updateClassTimeConfig, ctc);
			}
		});
		}//end if no id
		
	}
	
	public  ClassTimeJson getSelectedClassTime() {
		return selectedClassTime;
	}
	
	public JsArray<ClassTimeJson> getClassTimes(){
		return classTimes;
	}
	
	public void setClassTimes(JsArray<ClassTimeJson> times){
		this.classTimes = times;
	}
	
	public ClassTimeConfigJson getClasstimeConfig(){
		return this.classtimeConfig;
	}
	
	public void setClasstimeConfig(ClassTimeConfigJson classConfig){
		this.classtimeConfig = classConfig;
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
	

	
	
}
