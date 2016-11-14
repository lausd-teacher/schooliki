package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.body;
import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import net.videmantay.roster.ClientFactory;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.views.classtime.SeatingChartPanel;

public class RosterDashboardPanel extends Composite
{

	private static RosterDashboardPanelUiBinder uiBinder = GWT.create(RosterDashboardPanelUiBinder.class);

	interface RosterDashboardPanelUiBinder extends UiBinder<Widget, RosterDashboardPanel> {
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
	MaterialButton smDoneBtn;
	
	@UiField
	MaterialAnchorButton CancelBtn;
	
	@UiField
	MaterialButton smDoneBarCancelBtn;
	
	@UiField
	MaterialButton undoBtn;
	
	@UiField
	MaterialButton smUndoBtn;
	
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
	
	
	private final Function resizeFunc = new Function(){
		@Override
		public boolean f(Event e){
			console.log("window resized, Body size: " + body.getClientWidth() + " and view state is " + getViewType().name());
			if(body.getClientWidth() < 768 && getViewType() == View.CHART){
				//showDisplay();
			}
			
			if(body.getClientWidth() > 767 && getViewType() == View.CHART){
				console.log("window is greater 767 and view is chart");
			//	showChart();
			}
			return true;
		}
	};
	
	
	private View viewType = View.GRID;
	private State state = State.DASHBOARD;
	
	private HasRosterDashboardView display;
	final ClientFactory factory;


	//enum for state
	public enum View{GRID,CHART};
	public enum State{DASHBOARD,ROLL, HW,GROUP, MULTIPLE_SELECT,RANDOM, FURNITURE_EDIT, STUDENT_EDIT, STATIONS_EDIT}
	
	
	public RosterDashboardPanel(ClientFactory factory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.factory = factory;
		calendarContainer.add(factory.getGoogleCalendar());
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
	}
	
	public void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	public void showToolBar(){
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}

	
	
	public MaterialSwitch getGridSwitch() {
		return this.gridSwitch;
	}

	public MaterialIcon getHwIcon() {
		return this.hwIcon;
	}

	public MaterialIcon getGroupsIcon() {
		return this.groupsIcon;
	}

	public MaterialIcon getRollIcon() {
		return this.rollIcon;
	}

	public MaterialIcon getMultipleIcon() {
		return this.multipleIcon;
	}

	public MaterialIcon getRandomIcon() {
		return this.randomIcon;
	}

	public MaterialIcon getSeatingChartEditIcon() {
		return this.seatingChartEditIcon;
	}

	public HTMLPanel getTab1Main() {
		return this.tab1Main;
	}

	public MaterialRow getToolbar() {
		return this.toolbar;
	}

	public MaterialRow getDoneToolbar() {
		return this.doneToolbar;
	}

	public MaterialAnchorButton getClasstimeBtn() {
		return this.classtimeBtn;
	}

	public MaterialDropDown getClasstimeDropDown() {
		return this.classtimeDrop;
	}


	public MaterialAnchorButton getDoneBtn() {
		return this.doneBtn;
	}

	public MaterialButton getSmDoneBtn() {
		return this.smDoneBtn;
	}

	public MaterialAnchorButton getCancelBtn() {
		return this.CancelBtn;
	}

	public MaterialButton getSmDoneBarCancelBtn() {
		return this.smDoneBarCancelBtn;
	}

	public MaterialButton getUndoBtn() {
		return this.undoBtn;
	}

	public MaterialButton getSmUndoBtn() {
		return this.smUndoBtn;
	}


	public MaterialTab getTab() {
		return this.tab;
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
		  // calFrame.setVisible(false);
		}else if(display instanceof ClassRoomGrid){
			tab1Main.add(display);
			seatingChartEditIcon.setVisible(false);
			//calFrame.setVisible(false);
		}
			
	}
	
	public HasRosterDashboardView getDisplay(){
		return display;
	}


	public MaterialTabItem getCalTab() {
		return this.calTab;
	}

    public void clearDropDown(){
    	     classtimeDrop.clear();
    	     classtimeDrop.add(classDropDownManageLink);
    }


	public MaterialTabItem getReportsTab() {
		return this.reportsTab;
	}

	public MaterialTabItem getDashboardTab() {
		return this.dashboardTab;
	}
	
	public MaterialLink getClassDropDownManageLink() {
		return this.classDropDownManageLink;
	}

	public interface Presenter{
		void gridSwitchClickEvent();
		void homeworkIconClickEvent();
		void tabsClickEvent();
		void manageClassTimeLinkClickEvent();
		void seatingChartEditClickEvent();
		void barDoneButtonClickEvent();
		void barCancelButtonClickEvent();
	}




}
