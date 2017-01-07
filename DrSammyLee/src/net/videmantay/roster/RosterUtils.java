package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;

import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
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
	private  final RosterMain landingPage;
	private   final ClassroomMain classroomPage;
	private final InfoJson info = window.getPropertyJSO("info").cast();
	
	private  RosterJson currentRoster;
	private  JsArray<RosterJson> rosterList;
	private  ClassTimeJson selectedClassTime;
	private  boolean isEditMode = false;
	private  boolean isRollMode = false;
	private  JsArray<RosterStudentJson> students;
	private  StudentActionModal studentActionModal;
	
	
	RosterUtils(){
		landingPage = new RosterMain(this);
		classroomPage = new ClassroomMain(this);
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
		Ajax.get(RosterUrl.classtimeconfig(currentRoster.getId(), selectedClassTime.getId()))
		.done(new Function(){
			@Override
			public void f(){
				ClassTimeConfigJson ctc =  JsonUtils.safeEval((String)this.arguments(0)).cast();
				//fire ctc change event
				$(body).trigger(RosterEvent.updateClassTimeConfig, ctc);
			}
		});
		//this must also change the classtime button text to the current class time	
	}
	
	public  ClassTimeJson getSelectedClassTime() {
		return selectedClassTime;
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
