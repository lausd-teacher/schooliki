package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.body;
import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import net.videmantay.roster.HasClassroomDashboardView;
import net.videmantay.roster.views.classtime.SeatingChartPanel;

public class ClassroomDashboardPanel extends Composite
{

	private static ClassroomDashboardPanelUiBinder uiBinder = GWT.create(ClassroomDashboardPanelUiBinder.class);

	interface ClassroomDashboardPanelUiBinder extends UiBinder<Widget, ClassroomDashboardPanel> {
	}
	

	@UiField
	public MaterialSwitch gridSwitch;
	
	@UiField
	public MaterialIcon hwIcon;
	
	@UiField
	public MaterialIcon groupsIcon;
	
	@UiField
	public MaterialIcon rollIcon;
	
	@UiField
	public MaterialIcon multipleIcon;
	
	@UiField
	public MaterialIcon randomIcon;
	
	@UiField
	public MaterialIcon seatingChartEditIcon;
	
	@UiField
	public HTMLPanel tab1Main;
	
	@UiField
	public MaterialRow toolbar;
	
	@UiField
	public MaterialRow doneToolbar;
	/////////bar vs button essential the same job but bar 
	//is 100 across the screen and detached from nav panel
	@UiField
	public MaterialAnchorButton classroomtimeBar;
	@UiField
	public MaterialAnchorButton classtimeBtn;
	////////////////////////////////////////////////////////
	@UiField
	public MaterialDropDown classtimeDrop;
	
    @UiField
    public MaterialLink classDropDownManageLink;
	
	@UiField
	public MaterialAnchorButton doneBtn;
	
	@UiField
	public MaterialAnchorButton CancelBtn;
		
	@UiField
	public MaterialButton undoBtn;
	
	@UiField
	public MaterialButton redoBtn;

	@UiField
	public MaterialRow rollToolbar;
	
	@UiField
	public MaterialAnchorButton saveRollBtn;
	
	@UiField
	public MaterialButton cancelRollBtn;
	
	@UiField
	public MaterialTab tab;
	
	@UiField
	public MaterialTabItem calTab;
	
	@UiField
	public MaterialTabItem reportsTab;
	
	@UiField
	public MaterialTabItem dashboardTab;
	
	@UiField
	public HTMLPanel calendarContainer;
		
	
	private State state = State.DASHBOARD;
	
	private HasClassroomDashboardView display;
	


	//enum for state
	public enum State{DASHBOARD,ROLL, HW,GROUP, MULTIPLE_SELECT,RANDOM, FURNITURE_EDIT, STUDENT_EDIT, STATIONS_EDIT}
	
	
	public ClassroomDashboardPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
	}
	
	public void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	public void showToolBar(){
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		rollToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	
	public void showRollBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		rollToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}

	
	
	

	public State getState() {
		return this.state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setDisplayInTab1(HasClassroomDashboardView display){
		tab1Main.clear();
		if(display instanceof SeatingChartPanel){
			tab1Main.add(display);
		    seatingChartEditIcon.setVisible(true);
		
		}else if(display instanceof ClassRoomGrid){
			tab1Main.add(display);
			seatingChartEditIcon.setVisible(false);
		
		}
			
	}
	
	public HasClassroomDashboardView getDisplay(){
		return display;
	}

    public void clearDropDown(){
    	     classtimeDrop.clear();
    	     classtimeDrop.add(classDropDownManageLink);
    }
    
    



	
}
