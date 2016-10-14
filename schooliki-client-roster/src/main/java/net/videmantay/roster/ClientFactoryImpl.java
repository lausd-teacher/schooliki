package net.videmantay.roster;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.views.AppLayout;
import net.videmantay.roster.views.ClassroomGrid;
import net.videmantay.roster.views.RosterDashboardPanel;
import net.videmantay.roster.views.RosterDisplay;
import net.videmantay.roster.views.UserProfilePanel;
import net.videmantay.roster.views.assignment.GradedWorkForm;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.classtime.SeatingChartPanel;
import net.videmantay.roster.views.components.ClassRoomSideNav;
import net.videmantay.roster.views.components.MainRosterNavBar;
import net.videmantay.roster.views.components.MainRosterSideNav;
import net.videmantay.roster.views.student.CreateStudentForm;

public class ClientFactoryImpl implements ClientFactory {
	
	RosterDisplay rosterDisplay = new RosterDisplay();
	EventBus eventBus = new SimpleEventBus();
	RosterDashboardPanel rosterDashBoardPanel = new RosterDashboardPanel();
	AppLayout appPanel = new AppLayout();
	PlaceController placeController = new PlaceController(eventBus);
	RosterJson currentRoster = JavaScriptObject.createArray().cast();
	MainRosterSideNav mainRosterSideNav = new MainRosterSideNav();
	MainRosterNavBar mainRosterNavBar = new MainRosterNavBar();
	UserProfilePanel userProfile = new UserProfilePanel();
	ClassRoomSideNav classRoomSideNav = new ClassRoomSideNav();
	GradedWorkForm gradedWorkForm = new GradedWorkForm();
	GradedWorkMain gradedWorkMain = new GradedWorkMain(gradedWorkForm);
	CreateStudentForm studentFrom = new CreateStudentForm();
	ClassroomGrid grid = new ClassroomGrid();
	SeatingChartPanel seatingChartPanel = new SeatingChartPanel();

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
	}

	@Override
	public RosterDashboardPanel getRosterDashBoard(){
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
		return studentFrom;
	}
	@Override
	public ClassroomGrid getClassRoomGrid() {
		return grid;
	}
	@Override
	public SeatingChartPanel getSettingChartPanel() {
		return seatingChartPanel;
	}
	
}
