package net.videmantay.roster;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.views.AppLayout;
import net.videmantay.roster.views.ClassroomGrid;
import net.videmantay.roster.views.RosterDashboardPanel;
import net.videmantay.roster.views.RosterDisplay;
import net.videmantay.roster.views.UserProfilePage;
import net.videmantay.roster.views.UserProfilePanel;
import net.videmantay.roster.views.assignment.GradedWorkForm;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.calendar.GoogleCalendar;
import net.videmantay.roster.views.classtime.SeatingChartPanel;
import net.videmantay.roster.views.components.ClassRoomSideNav;
import net.videmantay.roster.views.components.MainRosterNavBar;
import net.videmantay.roster.views.components.MainRosterSideNav;
import net.videmantay.roster.views.incident.IncidentMain;
import net.videmantay.roster.views.student.CreateStudentForm;

public interface ClientFactory {
    
	RosterDisplay getRosterDisplay();
	PlaceController getPlaceController();
	RosterDashboardPanel getRosterDashBoard();
	RosterJson getCurrentRoster();
	AppLayout getAppPanel();
	MainRosterSideNav getMainRosterSideNav();
	MainRosterNavBar gerMainRosterNavBar();
	void setCurrentRoster(RosterJson json);
	EventBus getEventBus();
	UserProfilePanel userProfile();
	ClassRoomSideNav getClassRoomSideNav();
	GradedWorkForm getGradedWorkForm();
	GradedWorkMain getGradedWorkMain();
	CreateStudentForm getCreateStudentForm();
	ClassroomGrid getClassRoomGrid();
	SeatingChartPanel getSettingChartPanel();
	IncidentMain getIncidentMainPage();
	UserProfilePage getUserProfilePage();
	String getCurrentUserName();
	String getCurrentUserProfileImageUrl();
	GoogleCalendar getGoogleCalendar();
	String getAccessToken();

	

}
