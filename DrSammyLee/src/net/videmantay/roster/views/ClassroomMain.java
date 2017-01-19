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

import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.events.SideNavClosedEvent;
import gwt.material.design.client.events.SideNavClosedEvent.SideNavClosedHandler;
import gwt.material.design.client.events.SideNavClosingEvent;
import gwt.material.design.client.events.SideNavClosingEvent.SideNavClosingHandler;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
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
			
			
		
	}//end constructor
	
	
	//methods for navigation
	public void showDashboard(){
		console.log("showing dashboard");
				dashboard = new ClassroomDashboardPanel(utils);
				
			
				mainPanel.clear();
				mainPanel.add(dashboard);
				dashboard.classtimeBtn.setText(utils.getSelectedClassTime().getTitle());
				dashboard.classroomtimeBar.setText(utils.getSelectedClassTime().getTitle());
			
			for(int i = 0; i < utils.getClassTimes().length(); i++){
				MaterialLink link = new MaterialLink();
				MaterialLink link2 = new MaterialLink();
				link.setText(utils.getClassTimes().get(i).getTitle());
				link.setTextAlign(TextAlign.CENTER);
				link.setFontSize("1.1em");
				link.setTextColor("red");
				link.setPadding(10);
				link.setId(utils.getClassTimes().get(i).getId() + "");
				
				
				link2.setText(utils.getClassTimes().get(i).getTitle());
				link2.setTextAlign(TextAlign.CENTER);
				link2.setFontSize("1.1em");
				link2.setTextColor("red");
				link2.setPadding(5);
				link2.setId(utils.getClassTimes().get(i).getId() + "");
				
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
				ClassroomDisplay display =new ClassroomDisplay(utils);
				
				dashboard.setDisplayInTab1(display);
				display.drawGrid(utils.getStudents());
					
			
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
		this.showDashboard();
		
	}
	@Override
	public void onUnload(){
		//clean up html picker and tool tips
		$("div.picker, .material-tooltip").remove();
		$(navBar).css("width","0px");
		$(mainPanel).css("width", "0px");
		
	}
		
}