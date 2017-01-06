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

	private RosterUtils(){}
	private static RosterJson currentRoster;
	private static JsArray<RosterJson> rosterList;
	private static ClassTimeJson selectedClassTime;
	private static RootPanel root = RootPanel.get();
	private static RosterMain landingPage = new RosterMain();
	private static  ClassroomMain classroomPage = new ClassroomMain();
	private static boolean isEditMode = false;
	private static boolean isRollMode = false;
	private static JsArray<RosterStudentJson> students;
	private static StudentActionModal studentActionModal = new StudentActionModal();
	
	
	public static InfoJson getInfo(){
		InfoJson info = window.getPropertyJSO("info").cast();
		return info;
	}
	public static JsArray<RosterJson> getRosterList(){
		return rosterList;
	}
	
	public static void setRosterList(JsArray<RosterJson> rosList){
		rosterList = rosList;
	}
	public static  void setCurrentRoster(RosterJson current) {
		currentRoster = current;
		//set rosterId to window;
		Properties wnd = window.cast();
		
		wnd.setNumber("rosterId", currentRoster.getId());
		console.log(wnd.getInt("rosterId"));
	}
	
	public static RosterJson getCurrentRoster(){
		return currentRoster;
	}
	
	public static void setSelectedClassTime(ClassTimeJson sct) {
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
	
	public static ClassTimeJson getSelectedClassTime() {
		return selectedClassTime;
	}
	
	public static void showLandingPage(String request){
		
		root.clear();
		root.add(landingPage);
		landingPage.rosters();
		//just in case overlay is still in
		$("#sidenav-overlay, .drag-target").remove();
	}
	
	public static RosterMain getLandingPage(){
		return landingPage;
	}
	
	public static ClassroomMain getClassroomPage(){
		return classroomPage;
	}
	
	public static void showClassroomPage(List<String> request){
		root.clear();
		root.add(classroomPage);
		//just in case overlay is still in
		$("#sidenav-overlay, .drag-target").remove();
	}
	
	public static RosterStudentJson findStudentById(String id){
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
