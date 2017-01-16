package net.videmantay.roster.views;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.events.SideNavClosedEvent;
import gwt.material.design.client.events.SideNavClosedEvent.SideNavClosedHandler;
import gwt.material.design.client.events.SideNavClosingEvent;
import gwt.material.design.client.events.SideNavClosingEvent.SideNavClosingHandler;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.classtime.json.ClassTimeConfigJson;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterConfigJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.InfoJson;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.roster.views.UserProfilePanel;
import net.videmantay.roster.views.classtime.SeatingChartPanel;

import static com.google.gwt.query.client.GQuery.*;

import com.google.api.client.googleapis.util.Utils;

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
	public MaterialLink goalLink;
	
	@UiField
	public MaterialLink classTimeLink;
	
	@UiField
	public MaterialLink lessonPlanLink;
	
	@UiField
	public MaterialLink jobLink;
	
	@UiField
	public MaterialNavBar navBar;
	
	@UiField
	public MaterialNavBrand navBrand;
	
	@UiField
	public HTMLPanel classroom;
	
	//needs access to widget children
	ClassroomDashboardPanel dashboard;
	
	
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
		
///the constructor//////////
	public ClassroomMain(RosterUtils ru) {
		this.utils = ru;	
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
					History.newItem("c/" + utils.getCurrentRoster().getId());	
				}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					History.newItem("c/" + utils.getCurrentRoster().getId() +"/a");
				}});
			
			incidentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					History.newItem("c/" + utils.getCurrentRoster().getId()+"/i");
				}});
			
			classTimeLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.preventDefault();
					event.stopPropagation();
					sideNav.hide();
					History.newItem("c/" + roster.getId() +"/t");
				}});
			
		// End set up Side nav Links///////////////////
	//if we are here we want to mangae students and classtime load them
			//Ajax.get(RosterUrl.st)
	}//end constructor
	
	private void setRoster(){
	Ajax.get(RosterUrl.rosterconfig(utils.getCurrentRoster().getId())).done(new Function(){
		@Override
		public void f(){
			
			RosterConfigJson rosCon = JsonUtils.safeEval((String)this.arguments(0)).cast();
			console.log("rosteconfig is: ");
			console.log(rosCon);
			if(rosCon.getStudents() == null){
				//draw empty grid
			}else{
				//draw student grid
				utils.setStudents(rosCon.getStudents());
			}
			if(rosCon.getClassTimes() == null || rosCon.getClassTimes().length() < 1){
				ClassTimeJson classtime = ClassTimeJson.createObject().cast();
				classtime.setDescript("This is the default classtime. "+
				"Classtime is a way to help teachers transition from one activity to the next" +
				"i.e  'Carpet Time' , 'Reading Buddies'  , 'Lab'");
				classtime.setTitle("Default Time");
				classtime.setRosterId(utils.getCurrentRoster().getId());
				classtime.setIsDefault(true);
				JsArray<ClassTimeJson> times = JsArray.createArray().cast();
				times.push(classtime);
				utils.setClassTimes(times);
				utils.setSelectedClassTime(classtime);
				console.log("utils get classtimes");
				console.log(utils.getClassTimes());
				dashboard.classtimeBtn.setText(utils.getSelectedClassTime().getTitle());
				dashboard.classroomtimeBar.setText(utils.getSelectedClassTime().getTitle());
			}else{
			utils.setClassTimes(rosCon.getClassTimes());
			for(int i = 0; i < utils.getClassTimes().length(); i++){
				MaterialLink link = new MaterialLink();
				link.setText(utils.getClassTimes().get(i).getTitle());
				link.setId(utils.getClassTimes().get(i).getId() + "");
				if(utils.getClassTimes().get(i).getIsDefault()){
					dashboard.classtimeBtn.setText(utils.getClassTimes().get(i).getTitle());
					dashboard.classroomtimeBar.setText(utils.getClassTimes().get(i).getTitle());
					dashboard.classtimeDrop.insert(link, 1);
				}
				dashboard.classtimeDrop.add(link);
			}
				dashboard.classtimeDrop.add(dashboard.classDropDownManageLink);
			}
			if(rosCon.getDefaultTime() == null){
				ClassTimeConfigJson dtime = ClassTimeConfigJson.createObject().cast();
				utils.setClasstimeConfig(dtime);
			}else{
			utils.setClasstimeConfig(rosCon.getDefaultTime());
			}
			
			
			
		}
	});
		
	}
	//methods for navigation
	public void dashboard(){
		
				if(dashboard != null && dashboard.getState().toString().equalsIgnoreCase("dashboard")){
					return;
				}else{
			
				mainPanel.clear();
				dashboard = new ClassroomDashboardPanel();
				mainPanel.add(dashboard);
				dashboard.tab1Main.add(new ClassroomDisplay(utils));
					
				//get students and classtimes for currentroster set default classtime
				}	
			
	}

	//classtime list
	public void classtime(){
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
	//specific classtime
	public void classtime(String id){
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
	
	public void classtimeGroup(){
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
	
	public void classtimeProcedures(){
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
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				mainPanel.clear();
				mainPanel.add(new HTMLPanel("<h1>Error loading please refresh</h1>"));
				
			}

			@Override
			public void onSuccess() {
				dashboard.tab1Main.clear();
				dashboard.tab1Main.add(new SeatingChartPanel(utils));
				
			}});
	}
	
	public void schedule(){
	
	}
	//list assignments
	public void assignment(){
		
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
				// TODO Auto-generated method stub
				
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
		setRoster();
		
	}
	@Override
	public void onUnload(){
		//clean up html picker and tool tips
		$("div.picker, .material-tooltip").remove();
		$(navBar).css("width","0px");
		$(mainPanel).css("width", "0px");
	}
		
}