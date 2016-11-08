package net.videmantay.roster.views;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.common.primitives.Ints;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;
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
	MaterialLink manageFurnitureLink;
	
	
	@UiField
	MaterialLink arrangeStudentsLink;
	
	@UiField
	MaterialLink manageStationsLink;
	
	@UiField
	MaterialAnchorButton doneBtn;
	
	@UiField
	MaterialButton smDoneBtn;
	
	@UiField
	MaterialAnchorButton doneBarCancelBtn;
	
	@UiField
	MaterialButton smDoneBarCancelBtn;
	
	@UiField
	MaterialButton undoBtn;
	
	@UiField
	MaterialButton smUndoBtn;
	
//    @UiField
//	Frame calFrame;


	@UiField
	MaterialTab tab;
	
	@UiField
	MaterialTabItem calTab;
	
	@UiField
	MaterialTabItem reportsTab;
	
	@UiField
	MaterialTabItem dashboardTab;
	
//	@UiField
//	MaterialFAB classEventFAB;
	
//	@UiField
//	MaterialAnchorButton classEventAddBtn;
	
	//private RosterJson roster = JavaScriptObject.createObject().cast();
	
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
	
//	private final SelectionHandler<Widget> selectHand = new SelectionHandler<Widget>(){
//
//		@Override
//		public void onSelection(SelectionEvent<Widget> event) {
//			MaterialLink link = (MaterialLink)event.getSelectedItem();
//			if(link.getText().equalsIgnoreCase("Manage...")){
//				History.newItem("roster/"+ roster.getId() +"/classtime");
//			
//			}else{
//				int index =Ints.tryParse(link.getDataAttribute("data-index"));
//				ClassTimeJson classTime = roster.getClassTimes().get(index);
//				window.setPropertyJSO("classtime", classTime);
//				classtimeBtn.setText(classTime.getTitle());
//				//updateClassTime();
//			}	
//		}};
	
	private View viewType = View.GRID;
	private State state = State.DASHBOARD;
	
	private HasRosterDashboardView display;


	//enum for state
	public enum View{GRID,CHART};
	public enum State{DASHBOARD,ROLL, HW,GROUP, MULTIPLE_SELECT,RANDOM, FURNITURE_EDIT, STUDENT_EDIT, STATIONS_EDIT}
	
	
	public RosterDashboardPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		
	
		
//		classtimeDrop.addSelectionHandler(selectHand);
//		classtimeDrop2.addSelectionHandler(selectHand);
		/////////////////seatingChartEditLinks events
		
		
		manageFurnitureLink.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
			 display.arrangeFurniture();
				state = State.FURNITURE_EDIT;
				showDoneBar();
				display.arrangeFurniture();
			}});
		arrangeStudentsLink.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				$(".seatingChart").trigger("arrangeStudents");
				state= State.STUDENT_EDIT;
				showDoneBar();
				display.arrangeStudents();
			}});
		manageStationsLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(".seatingChart").trigger("manageStations");
				state = State.STATIONS_EDIT;
				showDoneBar();
				display.manageStations();
			}});
		
		////// toolbar buttons events//////////////////////
		hwIcon.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.checkHW();
				state = State.HW;
				showDoneBar();
			}});
		rollIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.takeRoll();
				state = State.ROLL;
				showDoneBar();
			}});
		groupsIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.groups();
				state = State.GROUP;
				showDoneBar();
			}});
		randomIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.pickRandom();
				state = State.RANDOM;
				
			}});
		multipleIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.multipleSelect();
				state = State.MULTIPLE_SELECT;
			
			}});
		////////////
		doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				switch(state){
				case FURNITURE_EDIT:display.doneArrangeFurniture(); break;
				case STUDENT_EDIT: display.doneArrangeStudents(); break;
				case STATIONS_EDIT: display.doneManageStations();break;
				case HW: display.doneCheckHW();break;
				case ROLL: display.doneTakeRoll();break;
				case GROUP: display.doneGroups();break;
				case RANDOM: display.donePickRandom(); break;
				case MULTIPLE_SELECT: display.doneMultipleSelect(); break;
				default: display.home();
				}
				display.home();
				showToolBar();
			}});
		smDoneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				switch(state){
				case FURNITURE_EDIT:display.doneArrangeFurniture(); break;
				case STUDENT_EDIT: display.doneArrangeStudents(); break;
				case STATIONS_EDIT: display.doneManageStations();break;
				case HW: display.doneCheckHW();break;
				case ROLL: display.doneTakeRoll();break;
				case GROUP: display.doneGroups();break;
				case RANDOM: display.donePickRandom(); break;
				case MULTIPLE_SELECT: display.doneMultipleSelect(); break;
				default: display.home();
				}
				display.home();
				showToolBar();
			}});
		
		undoBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.undo();
				
			}});
		
		smUndoBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.undo();
				
			}});
		doneBarCancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.cancel(state.name());
				display.home();
				showToolBar();
			}});
		
		smDoneBarCancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.cancel(state.name());
				display.home();
				showToolBar();
			}});
		//add handler to div to render cal on resize
		
		
	}
	
	private void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	private void showToolBar(){
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
//	private void showChart(){
//		display = new SeatingChartPanel();
//		tab1Main.clear();
//		tab1Main.add(display);
//		seatingChartEditIcon.setVisible(true);
//	}
//	private void showDisplay(){
//		display = new ClassroomGrid();
//		tab1Main.clear();
//		tab1Main.add(display); 
//		seatingChartEditIcon.setVisible(false);
//	}

	
	@Override
	public void onLoad(){
	
		
		//JsArray<ClassTimeJson> classTimes = roster.getClassTimes();
		
		//set up classtimeDrop list
//		classtimeDrop.clear();
//		classtimeDrop2.clear();
//		//set up first link as manager
//		MaterialLink manageLink = new MaterialLink();
//		manageLink.setText("Manage...");
//		classtimeDrop.add(manageLink);
//		for(int i = 0; i< classTimes.length(); i++){
//			MaterialLink link = new MaterialLink();
//			link.setText(classTimes.get(i).getTitle());
//			link.setDataAttribute("data-index", ""+i);
//			classtimeDrop.add(link);
//			if(classTimes.get(i).getIsDefault()){
//			classtimeBtn.setText(classTimes.get(i).getTitle());
//			window.setPropertyJSO("classtime", classTimes.get(i));
//			console.log("... window called from rosterdassh bord panel for classtime is .... ");
//			console.log(window.getPropertyJSO("classtime"));
//			}
//		}//end for
//		MaterialLink manageLink2 = new MaterialLink();
//		manageLink2.setText("Manage...");
//		classtimeDrop2.add(manageLink2);
//		for(int i = 0; i< classTimes.length(); i++){
//			MaterialLink link = new MaterialLink();
//			link.setText(classTimes.get(i).getTitle());
//			link.setDataAttribute("data-index", ""+i);
//			classtimeDrop2.add(link);
//			if(classTimes.get(i).getIsDefault()){
//			classtimeBtn.setText(classTimes.get(i).getTitle());
//			window.setPropertyJSO("classtime", classTimes.get(i));
//			console.log("... window called from rosterdassh bord panel for classtime is .... ");
//			console.log(window.getPropertyJSO("classtime"));
//			}
		}//end for
		
		//fullcalendar needs a delay to render correctly so here is the check
//	$(".tab").as(Ui).click(new Function(){
//				@Override
//				public void f(){
//					if( $(this).find("a").attr("href").equalsIgnoreCase(".tab2")){
//						console.log("the table length is : " + $(tab2).find("table").length());
//						if( $(tab2).find("table").length() < 1){
//							console.log("No table found in tab2");
//							new Timer(){
//
//								@Override
//								public void run() {
//									calFrame.setVisible(true);
//									classEventFAB.setVisible(true);
//								}}.schedule(100);;
//						}
//					}else{calFrame.setVisible(false);
//						classEventFAB.setVisible(false);
//					}
//				}
//	});
	
	//$(window).resize(resizeFunc);
	
	
	
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

	public MaterialLink getManageFurnitureLink() {
		return this.manageFurnitureLink;
	}

	public MaterialLink getArrangeStudentsLink() {
		return this.arrangeStudentsLink;
	}

	public MaterialLink getManageStationsLink() {
		return this.manageStationsLink;
	}

	public MaterialAnchorButton getDoneBtn() {
		return this.doneBtn;
	}

	public MaterialButton getSmDoneBtn() {
		return this.smDoneBtn;
	}

	public MaterialAnchorButton getDoneBarCancelBtn() {
		return this.doneBarCancelBtn;
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

//	public MaterialFAB getClassEventFAB() {
//		return this.classEventFAB;
//	}

//	public MaterialAnchorButton getClassEventAddBtn() {
//		return this.classEventAddBtn;
//	}

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
	}




}
