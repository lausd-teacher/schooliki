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
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.classtime.SeatingChartPanel;

public class ClassroomDashboardPanel extends Composite
{

	private static ClassroomDashboardPanelUiBinder uiBinder = GWT.create(ClassroomDashboardPanelUiBinder.class);

	interface ClassroomDashboardPanelUiBinder extends UiBinder<Widget, ClassroomDashboardPanel> {
	}
	

	@UiField
	MaterialSwitch gridSwitch;
	
	@UiField
	MaterialIcon hwIcon;
	
	@UiField
	MaterialIcon groupsIcon;
	
	@UiField
	MaterialIcon rollIcon;
	
	@UiField
	MaterialIcon multipleIcon;
	
	@UiField
	MaterialIcon randomIcon;
	
	@UiField
	MaterialIcon seatingChartEditIcon;
	
	@UiField
	HTMLPanel tab1Main;
	
	@UiField
	MaterialRow toolbar;
	
	@UiField
	MaterialRow doneToolbar;
	
	@UiField
	MaterialAnchorButton classtimeBtn;
	
	@UiField
	MaterialDropDown classtimeDrop;
	
    @UiField
	MaterialLink classDropDownManageLink;
	
	@UiField
	MaterialAnchorButton doneBtn;
	
	@UiField
	MaterialAnchorButton CancelBtn;
		
	@UiField
	MaterialButton undoBtn;
	
	@UiField
	MaterialButton redoBtn;

	@UiField
	MaterialRow rollToolbar;
	
	@UiField
	MaterialAnchorButton saveRollBtn;
	
	@UiField
	MaterialButton cancelRollBtn;
	
	@UiField
	MaterialTab tab;
	
	@UiField
	MaterialTabItem calTab;
	
	@UiField
	MaterialTabItem reportsTab;
	
	@UiField
	MaterialTabItem dashboardTab;
	
	@UiField
	HTMLPanel calendarContainer;
		
	
	private View viewType = View.GRID;
	private State state = State.DASHBOARD;
	
	private HasRosterDashboardView display;
	


	//enum for state
	public enum View{GRID,CHART};
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


	public View getViewType() {
		return this.viewType;
	}

	public void setViewType(View viewType) {
		this.viewType = viewType;
	}
	
	
	public void setDisplayInTab1(HasRosterDashboardView display){
		tab1Main.clear();
		if(display instanceof SeatingChartPanel){
			tab1Main.add(display);
		    seatingChartEditIcon.setVisible(true);
		
		}else if(display instanceof ClassRoomGrid){
			tab1Main.add(display);
			seatingChartEditIcon.setVisible(false);
		
		}
			
	}
	
	public HasRosterDashboardView getDisplay(){
		return display;
	}

    public void clearDropDown(){
    	     classtimeDrop.clear();
    	     classtimeDrop.add(classDropDownManageLink);
    }
    
    



	
}
