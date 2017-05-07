package net.videmantay.roster.views;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.events.SideNavClosingEvent;
import gwt.material.design.client.events.SideNavClosingEvent.SideNavClosingHandler;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.SeatingChartJson;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.student.json.InfoJson;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentIncidentJson;
import net.videmantay.roster.views.UserProfilePanel;
import net.videmantay.roster.views.assignment.AssignmentMain;
import net.videmantay.roster.views.incident.IncidentMain;
import net.videmantay.roster.views.routine.RoutineMain;
import net.videmantay.roster.views.routine.SeatingChartPanel;

import static com.google.gwt.query.client.GQuery.*;

import com.google.common.primitives.Longs;

public class ClassroomMain extends Composite{

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}
	
	@UiField
	public MaterialContainer mainPanel;
	
	@UiField
	public UserProfilePanel profilePanel;
	
	@UiField
	public MaterialSideNav sideNav;
	//side nav links here ///////
	
	@UiField
	public MaterialLink dashboardLink;
	
	@UiField
	public MaterialLink assignmentLink;
	
	@UiField
	public MaterialLink incidentLink;
	
	@UiField
	public MaterialLink routineLink;
	
	@UiField
	public MaterialNavBar navBar;
	
	@UiField
	public MaterialNavBrand navBrand;
	
	@UiField
	public HTMLPanel classroom;
	
	@UiField(provided=true)
	public StudentActionModal studentActionModal ;

	
	//needs access to widget children
	private  ClassroomDashboardPanel dashboard;
	
	
	private final RosterUtils utils;
	
	//end side nav links/////////
		
	private  RosterJson roster;
	private SideNavClosingHandler sideNavClosed = new SideNavClosingHandler(){

		@Override
		public void onSideNavClosing(SideNavClosingEvent event) {
			new Timer(){
				@Override
				public void run(){
					$("header,main").css("marginLeft", "0px");
					$("div#sidenav-overlay, div.drag-target", body).remove();
				}
			}.schedule(110);
			
		}};
	//create a grid switch handler for click to and from seatingChart
		//function for when the ajax for seating chart finishes
		Function gridFunc = new Function(){
			@Override
			public void f(){
				SeatingChartJson seatingChart = JsonUtils.safeEval((String)this.arguments(0));
				utils.setSeatingChart(seatingChart);
				SeatingChartPanel chartPanel = new SeatingChartPanel(utils);
				dashboard.setDisplayInTab1(chartPanel);
				MaterialLoader.showLoading(false);
				
			}
		};
		//the runasync for the handler no need to create extra
		RunAsyncCallback getNewSeatingChart = new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				MaterialLoader.showLoading(false);
				MaterialToast.fireToast("Unable to retreive your Seating Chart");
				
			}

			@Override
			public void onSuccess() {
				//load from Ajax
				Ajax
				.get(RosterUrl.seatingchart(utils.getCurrentRoster().getId(), utils.getSelectedClassTime().getRoutine().getId()))
				.done(gridFunc);
				
			}};
		ValueChangeHandler<Boolean> gridSwitchHandler = new ValueChangeHandler<Boolean>(){

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				dashboard.editIcon.setVisible(event.getValue());
				console.log("gridswitch clicked and the value is " + dashboard.gridSwitch.getValue());
				if(event.getValue()){
				//so depending on the value
					seatingchart();
				}else{
					showDashboard();
				}
				
			}};
			
		ClickHandler displayEditIconHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dashboard.toolbar.setVisible(false);
				dashboard.getDisplay().edit();
				
			}};
			
		SelectionHandler<Widget> routineSelection = new SelectionHandler<Widget>(){

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				String manageCheck = ((MaterialLink)event.getSelectedItem()).getText();
				if(manageCheck.equalsIgnoreCase("Manage...")){
					//goto incident page
					incident();
					return;
					
				}
				String value = ((MaterialLink)event.getSelectedItem()).getDataAttribute("routineId");
				Long routineId = Longs.tryParse(value);
				if(routineId == utils.getSelectedClassTime().getRoutine().getId()){
					return;
				}
		
				for(int i = 0 ; i < utils.getClassTimes().length(); i++){
					if(routineId == utils.getClassTimes().get(i).getId()){
						FullRoutineJson sct = FullRoutineJson.createObject().cast();
						sct.setRoutine(utils.getClassTimes().get(i));
						utils.setSelectedClassTime(sct);
					}
				}
		
			}};
///the constructor//////////
	public ClassroomMain(RosterUtils ru) {
		this.utils = ru;	
		studentActionModal = new StudentActionModal(utils);
		
		this.initWidget(uiBinder.createAndBindUi(this));
			//set side nav links/////////
		profilePanel.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				sideNav.hide();
				History.newItem("roster");
				
			}
			
			
		}, ClickEvent.getType());
		
		sideNav.addClosingHandler(sideNavClosed);
			
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					sideNav.hide();
					//History.newItem("c/" + utils.getCurrentRoster().getId());	
					showDashboard();
				}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					//History.newItem("c/" + utils.getCurrentRoster().getId() +"/a");
					assignment();
				}});
			
			incidentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					incident();
					
				}});
			
			routineLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					//History.newItem("c/" + roster.getId() +"/t");
					routine();
				}});
			
			
		
	}//end constructor
	
	
	//methods for navigation
	public void showDashboard(){
		console.log("showing dashboard");
				dashboard = new ClassroomDashboardPanel(utils);
				
			
				mainPanel.clear();
				mainPanel.add(dashboard);
				
				
				//seating chart edit icon click
				dashboard.gridSwitch.addValueChangeHandler(gridSwitchHandler);
				//seating chart edit icon click
				dashboard.editIcon.addClickHandler(this.displayEditIconHandler);
				
				//add ondropdown handler
				dashboard.classtimeDrop.addSelectionHandler(routineSelection);
				dashboard.classtimeDrop2.addSelectionHandler(routineSelection);
				
				//set up classtime buttons and links
				if(utils.getSelectedClassTime().getRoutine() != null){
				dashboard.classtimeBtn.setText(utils.getSelectedClassTime().getRoutine().getTitle());
				dashboard.classroomtimeBar.setText(utils.getSelectedClassTime().getRoutine().getTitle());
				
				for(int i = 0; i < utils.getClassTimes().length(); i++){
					MaterialLink link = new MaterialLink();
					MaterialLink link2 = new MaterialLink();
					link.setText(utils.getClassTimes().get(i).getTitle());
					link.setTextAlign(TextAlign.CENTER);
					link.setFontSize("1.1em");
					link.setTextColor("red");
					link.setId(utils.getClassTimes().get(i).getId() + "");
					link.setPadding(15);
					
					
					link2.setText(utils.getClassTimes().get(i).getTitle());
					link2.setTextAlign(TextAlign.CENTER);
					link2.setFontSize("1.1em");
					link2.setTextColor("red");
					link2.setId(utils.getClassTimes().get(i).getId() + "");
					link2.setPadding(15);
					
					if(utils.getClassTimes().get(i).getIsDefault()){
						dashboard.classtimeBtn.setText(utils.getClassTimes().get(i).getTitle());
						dashboard.classroomtimeBar.setText(utils.getClassTimes().get(i).getTitle());
						dashboard.classtimeDrop.insert(link, 0);
						dashboard.classtimeDrop2.insert(link2,0);
						continue;
					}
					dashboard.classtimeDrop.insert(link, dashboard.classtimeDrop.getItems().size()-1);
					dashboard.classtimeDrop2.insert(link2, dashboard.classtimeDrop2.getItems().size()-1);
					
					
				}
					//set up gridswitch click
					ClassroomDisplay display =new ClassroomDisplay(utils);

					dashboard.setDisplayInTab1(display);
					display.drawGrid();
				
				}else{
					dashboard.classtimeBtn.setText("Loading...");
					dashboard.classroomtimeBar.setText("Loading...");
				}		
			
	}
	
	
	//classtime list
	public void routine(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				//add classtimeMain to this main panel
				mainPanel.clear();
				mainPanel.add(new RoutineMain(utils));
			}});
	}
	//specific classtime
	public void routine(String id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void routineGroup(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void routineProcedures(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void seatingchart(){
		if(utils.getSeatingChart() == null || utils.getSeatingChart().getId() != utils.getSelectedClassTime().getRoutine().getId()){
			MaterialLoader.showLoading(true);
			GWT.runAsync(getNewSeatingChart);
			}else{
				SeatingChartPanel chartPanel = new SeatingChartPanel(utils);
				dashboard.setDisplayInTab1(chartPanel);
				chartPanel.drawGrid();
			}//end if seatinchart is null or equat to classtime
	}
	
	public void schedule(){
	
	}
	//list assignments
	public void assignment(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new AssignmentMain(utils));
				
			}});
	}
	//specific assignment
	public void assignment(String id){
		
	}
	//specific assignment -view(cal| chart)
	public void assignment(String id, String view){
		
	}
	public void lesson(){
		
	}
	public void lesson(String id){
		
	}
	public void lesson(String id, String view){
		
	}
	public void incident(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new IncidentMain(utils));
				
			}});
	}
	public void incident(String id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void incident(String id, String view){
		
	}
	public void goal(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void goal(String id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void goal(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void book(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void book(String id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void book(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void job(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void job(String id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void job(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void form(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void form(String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void form(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	//student pages //////
	public void student(String id){
		
	}
	public void student(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentwork(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentwork(Long id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void studentwork(String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void studentwork(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentgoal(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentgoal(Long id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void studentgoal(String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void studentgoal(String id, String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentincident(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public void studentincident(Long id){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	public void studentincident(String view){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}});
	}
	
	@Override
	public void onLoad(){
		console.log("classroom main onload called");
		final InfoJson info = utils.getInfo();
		roster = utils.getCurrentRoster();
		navBar.setBackgroundColor(roster.getColor());
		navBrand.setText(roster.getTitle());
		profilePanel.setProfileInfo(info);
		this.showDashboard();
		
		//this action trigged in ClassroomDisplay and SeatingChart
		$(body).on("studentAction", new Function(){
			@Override
			public boolean f(Event e, Object...o){
				e.stopPropagation();
				RosterStudentJson student = (RosterStudentJson)o[0];
			
						JsArray<RosterStudentJson> students = JsArray.createArray().cast();
						students.push(student);
						studentActionModal.setData(students);
					
				studentActionModal.modal.openModal();
				return true;
			}
		});
		
		$(body).on("incidentResponse", new Function(){
			@Override
			public boolean f(Event e , Object...o){
				e.stopPropagation();
				console.log(o[0]);
				final JsArray<StudentIncidentJson> stuIncs = (JsArray<StudentIncidentJson>) o[0];
				for(int i = 0; i < stuIncs.length(); i++){
					for(int j = 0 ; j < utils.getStudents().length();j++){
						if(utils.getStudents().get(j).getStudentId().equals(stuIncs.get(i))){
							if(stuIncs.get(i).getPoints() < 0){
								utils.getStudents().get(j).negPoint( 
										utils.getStudents().get(j).posPoint() + stuIncs.get(i).getPoints()
										);
							}else{
							utils.getStudents().get(j).negPoint( 
									utils.getStudents().get(j).negPoint() + stuIncs.get(i).getPoints()
									);
							}//end else pos neg
							break;
						}//if
					}//inner for
				}//for
		
			
						MaterialToast.fireToast(stuIncs.length() + " student were award " + stuIncs.get(0).getPoints());
						//redrew the grid
				
				return true;
			}
		});
		
		if(utils.getIncidents() == null){
			console.log("incident is empty in Classroom main ajax call here");
				//Ajax call
				Ajax.get("/roster/"+utils.getCurrentRoster().getId() + "/incident").done(new Function(){
					@Override
					public void f(){
						String data = (String)this.arguments(0);
						JsArray<IncidentJson> incidents = JsonUtils.safeEval(data).cast();
						utils.setIncidents(incidents);
						studentActionModal.drawIncidentGrid(incidents);
					}
				});
			
		}else{
		console.log("incident is not null  in roster main incident entity is ");
		console.log(utils.getIncidents());
		}
		
	}
	@Override
	public void onUnload(){
		//clean up html picker and tool tips
		$("div.picker, .material-tooltip").remove();
		$(navBar).css("width","0px");
		$(mainPanel).css("width", "0px");
		
	}
		
}