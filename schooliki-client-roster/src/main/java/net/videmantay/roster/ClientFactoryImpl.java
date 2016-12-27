package net.videmantay.roster;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.AppUserJson;
import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.views.AppLayout;
import net.videmantay.roster.views.ClassRoomGrid;
import net.videmantay.roster.views.RosterDashboardPanel;
import net.videmantay.roster.views.RosterDisplay;
import net.videmantay.roster.views.StudentActionModal;
import net.videmantay.roster.views.UserProfilePage;
import net.videmantay.roster.views.UserProfilePanel;
import net.videmantay.roster.views.assignment.AssignementDashboard;
import net.videmantay.roster.views.assignment.GradedWorkForm;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.calendar.GoogleCalendar;
import net.videmantay.roster.views.classtime.ClassTimeForm;
import net.videmantay.roster.views.classtime.ClassTimeGrid;
import net.videmantay.roster.views.classtime.SeatingChartPanel;
import net.videmantay.roster.views.components.ClassRoomSideNav;
import net.videmantay.roster.views.components.IncidentFormIconInput;
import net.videmantay.roster.views.components.MainRosterNavBar;
import net.videmantay.roster.views.components.MainRosterSideNav;
import net.videmantay.roster.views.draganddrop.UndoRedoManager;
import net.videmantay.roster.views.incident.IncidentForm;
import net.videmantay.roster.views.incident.IncidentMain;
import net.videmantay.roster.views.student.CreateStudentForm;
import net.videmantay.shared.util.GoogleJs;
import static com.google.gwt.query.client.GQuery.*;

public class ClientFactoryImpl implements ClientFactory {
	
	RosterDisplay rosterDisplay = new RosterDisplay();
	EventBus eventBus = new SimpleEventBus();
	RosterDashboardPanel rosterDashBoardPanel = null;
	AppLayout appPanel = new AppLayout();
	PlaceController placeController = new PlaceController(eventBus);
	RosterJson currentRoster = JavaScriptObject.createArray().cast();
	MainRosterSideNav mainRosterSideNav = new MainRosterSideNav();
	MainRosterNavBar mainRosterNavBar = new MainRosterNavBar();
	UserProfilePanel userProfile = null;
	UserProfilePage profilePage = null;
	ClassRoomSideNav classRoomSideNav = new ClassRoomSideNav();
	GradedWorkForm gradedWorkForm = new GradedWorkForm();
	GradedWorkMain gradedWorkMain = new GradedWorkMain(gradedWorkForm);
	CreateStudentForm studentForm = new CreateStudentForm();
	StudentActionModal studentModal = new StudentActionModal(this);
	ClassRoomGrid grid = new ClassRoomGrid(studentForm, studentModal);
	SeatingChartPanel seatingChartPanel = null;
	GoogleCalendar googleCalendar = null;
	 String token = null;
	 ClassTimeJson selectedClassTime = null;
	 ClassTimeGrid classTimeGrid = null;
	 ClassTimeForm classTimeForm = null;
	 boolean isEditMode = false;
	 boolean isRollMode = false;
	 AssignementDashboard assignementDashboard = null;
	 
	 JsArray<IncidentTypeJson> incidentTypeList = null;
	 JsArray<AppUserJson> students = JavaScriptObject.createObject().cast();

	 
	 IncidentFormIconInput incidentFromIconInput = new IncidentFormIconInput();
	 IncidentForm incidentForm = new IncidentForm(incidentFromIconInput);
	 IncidentMain incidentMain = new IncidentMain(incidentForm);
	 UndoRedoManager undoRedoManager;


	@Override
	public RosterDisplay getRosterDisplay() {
		return rosterDisplay;
	}
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public RosterJson getCurrentRoster() {
		return currentRoster;
	}

	@Override
	public void setCurrentRoster(RosterJson current) {
		currentRoster = current;
		//set rosterId to window;
		Properties wnd = window.cast();
		wnd.set("rosterId", currentRoster.getId());
		
	}

	@Override
	public RosterDashboardPanel getRosterDashBoard(){
		if(rosterDashBoardPanel == null ){
			
			rosterDashBoardPanel = new RosterDashboardPanel(this);
		}
		return rosterDashBoardPanel;
	}


	@Override
	public AppLayout getAppPanel() {
		return appPanel;
	}

	@Override
	public MainRosterSideNav getMainRosterSideNav() {
		return mainRosterSideNav;
	}

	@Override
	public MainRosterNavBar gerMainRosterNavBar() {
		return mainRosterNavBar;
	}
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	@Override
	public UserProfilePanel userProfile() {
		if(userProfile == null){
			userProfile = new UserProfilePanel();
		}
		return userProfile;
	}
	
	@Override
	public ClassRoomSideNav getClassRoomSideNav() {
		return classRoomSideNav;
	}
	@Override
	public GradedWorkForm getGradedWorkForm() {
		return gradedWorkForm;
	}
	@Override
	public GradedWorkMain getGradedWorkMain() {
		return gradedWorkMain;
	}
	@Override
	public CreateStudentForm getCreateStudentForm() {
		return studentForm;
	}
	@Override
	public ClassRoomGrid getClassRoomGrid() {
		return grid;
	}
	@Override
	public SeatingChartPanel getSettingChartPanel() {
		
		if(seatingChartPanel == null){
			seatingChartPanel = new SeatingChartPanel(this);
		}
		return seatingChartPanel;
	}
	
	@Override
	public IncidentMain getIncidentMainPage() {
		return incidentMain;
	}
	@Override
	public UserProfilePage getUserProfilePage() {
		if(profilePage == null){
			profilePage = new UserProfilePage(appPanel.getNavBartitle(), userProfile());
		}
		return profilePage;
	}
	@Override
	public String getCurrentUserName() {
		
		
		return profilePage.getProfileFullName();
	}
	@Override
	public String getCurrentUserProfileImageUrl() {
	
		return profilePage.getProfilePictureUrl();
	}
	@Override
	public GoogleCalendar getGoogleCalendar() {
		if(googleCalendar == null){
			
			googleCalendar = new GoogleCalendar(this);
		}
			
		return googleCalendar;
	}
	@Override
	public String getAccessToken() {
		
		 if(token == null){
			 token = GoogleJs.getAccessToken();
		 }
		return token;
	}
	@Override
	public ClassTimeJson getSelectedClassTime() {
		return selectedClassTime;
	}
	@Override
	public void setSelectedClassTime(ClassTimeJson selectedClassTime) {
		this.selectedClassTime = selectedClassTime;
		$(window).prop("selectedClassTime", selectedClassTime);
		//this must also change the classtime button text to the current class time
		
	}
	@Override
	public ClassTimeGrid getClassTimeGrid() {
		  if(classTimeGrid == null){
			  classTimeGrid = new ClassTimeGrid(this);
		  }
		return classTimeGrid;
	}
	@Override
	public ClassTimeForm getClassTimeForm() {
		if(classTimeForm == null){
			classTimeForm = new ClassTimeForm();
		}
		return classTimeForm;
	}
	@Override
	public boolean isEditMode() {
		return isEditMode;
	}
	@Override
	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}
	@Override
	public AssignementDashboard getAssignementDashboard() {
		
		if(assignementDashboard == null){
			assignementDashboard = new AssignementDashboard(gradedWorkMain);
		}
		return assignementDashboard;
	}
	@Override
	public StudentActionModal getStudentActionModal() {
		return studentModal;
	}
	@Override
	public JsArray<IncidentTypeJson> getIncidentTypesList() {
		if(incidentTypeList == null){
			Ajax.get("/incidenttype").done(new Function() {
				@Override
				public void f() {
					incidentTypeList = JsonUtils.safeEval(arguments(0).toString());
					GWT.log("Received Incidents " + arguments(0).toString());
					incidentMain.setIncidentsTypes(incidentTypeList);
					incidentFromIconInput.setIcons(incidentTypeList);
					studentModal.setUpIncidents(incidentTypeList);
						}	
			}).progress(new Function() {
				@Override
				public void f() {

				}

			}).fail(new Function() {
				@Override
				public void f() {
					Window.alert("Incident Types could not be fetched from the server");
				}
			});
		}
		return incidentTypeList;
	}
	
	@Override
	public IncidentTypeJson findIncidentTypeById(String searched) {
		for(int i = 0; i < incidentTypeList.length(); i++){
			 IncidentTypeJson incidentType = incidentTypeList.get(i);
			 String incidentTypeId = incidentType.getId();
			  if(searched.compareTo(incidentTypeId) == 0){
				  return incidentType;
			  }
		}	
		return null;
	}
	@Override
	public IncidentFormIconInput getIncidentFormInput() {
		return incidentFromIconInput;
	}
	@Override
	public JsArray<AppUserJson> getCurrentRosterStudentList() {
		return students;
	}
	@Override
	public void addNewStudent(AppUserJson newStudent) {
		students.push(newStudent);	
	}
	@Override
	public void setCurrentRosterStudentList(JsArray<AppUserJson> studentsList) {
		this.students = studentsList;
		
	}
	@Override
	public AppUserJson findStudentById(String id){
		for(int i = 0; i < students.length(); i++){
			AppUserJson appUser = students.get(i);
			if(appUser.getId().compareTo(id) == 0){
				return appUser;
			}
		}
		return null;
	}
	@Override
	public boolean isRollMode() {
		return isRollMode;
	}
	
	@Override
	public void setRollMode(boolean rollMode) {
		this.isRollMode = rollMode;
	}
	
	
	@Override
	public UndoRedoManager getUndoRedoManager() {
		if(undoRedoManager == null){
			
			undoRedoManager = new UndoRedoManager();
			
			
		}
		
		return undoRedoManager;
	}

	
	

	
}
