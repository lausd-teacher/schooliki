package net.videmantay.roster.views;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.InfoJson;
import net.videmantay.roster.views.UserProfilePanel;

import static com.google.gwt.query.client.GQuery.*;

public class ClassroomMain extends Composite{

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}
	
	@UiField
	MaterialContainer mainPanel;
	
	@UiField
	MaterialNavBrand rosterTitle;
	
	@UiField
	UserProfilePanel profilePanel;
	
	@UiField
	MaterialSideNav sideNav;
	//side nav links here ///////
	
	@UiField
	MaterialLink dashboardLink;
	
	@UiField
	MaterialLink assignmentLink;
	
	@UiField
	MaterialLink incidentLink;
	
	@UiField
	MaterialLink goalLink;
	
	@UiField
	MaterialLink classTimeLink;
	
	@UiField
	MaterialLink lessonPlanLink;
	
	@UiField
	MaterialLink jobLink;
	
	//end side nav links/////////
		
	private  RosterJson classRoster;
	
	
	public ClassroomMain() {
		this.initWidget(uiBinder.createAndBindUi(this));
	
		final InfoJson info = RosterUtils.getInfo();
		profilePanel.setProfileInfo(info);
			//set side nav links/////////
			profilePanel.addDomHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					History.newItem("roster");
				}}, ClickEvent.getType());
			
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					History.newItem("c/" + RosterUtils.getCurrentRoster().getId());
					$("#sidenav-overlay, .drag-target").remove();
				}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					History.newItem("c/" + RosterUtils.getCurrentRoster().getId() +"/a");
					$("#sidenav-overlay, .drag-target").remove();
				}});
			
			incidentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					History.newItem("c/" + RosterUtils.getCurrentRoster().getId()+"/i");
					$("#sidenav-overlay, .drag-target").remove();
					
				}});
			
			classTimeLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					History.newItem("c/" + classRoster.getId() +"/t");
					$("#sidenav-overlay, .drag-target").remove();
				}});
		// End set up Side nav Links///////////////////
		
			
	}//end constructor
	//methods for navigation
	public void dashboard(){
		
	}
	public void dashboard(String id){
		
	}
	public void dashboard(String id, String view){
		
	}
	public void classtime(){
		
	}
	public void classtime(String id){
		
	}
	
	public void classtimeGroup(){
		
	}
	
	public void classtimeProcedures(){
		
	}
	
	public void seatingchart(){
		
	}
	
	public void schedule(){
		
	}
	
	public void assignment(){
		
	}
	public void assignment(String id){
		
	}
	public void assignment(String id, String view){
		
	}
	public void lesson(){
		
	}
	public void lesson(String id){
		
	}
	public void lesson(String id, String view){
		
	}
	public void incident(){
		
	}
	public void incident(String id){
		
	}
	public void incident(String id, String view){
		
	}
	public void goal(){
		
	}
	public void goal(String id){
		
	}
	public void goal(String id, String view){
		
	}
	public void book(){
		
	}
	public void book(String id){
		
	}
	public void book(String id, String view){
		
	}
	public void job(){
		
	}
	public void job(String id){
		
	}
	public void job(String id, String view){
		
	}
	
	public void form(){
		
	}
	public void form(String view){
		
	}
	public void form(String id, String view){
		
	}
	
	//student pages //////
	public void student(String id){
		
	}
	public void student(String id, String view){
		
	}
	
	public void studentwork(){
		
	}
	
	public void studentwork(Long id){
		
	}
	public void studentwork(String view){
		
	}
	public void studentwork(String id, String view){
		
	}
	
	public void studentgoal(){
		
	}
	
	public void studentgoal(Long id){
		
	}
	public void studentgoal(String view){
		
	}
	public void studentgoal(String id, String view){
		
	}
	
	public void studentincident(){
		
	}
	
	public void studentincident(Long id){
		
	}
	public void studentincident(String view){
		
	}
	
	
	

	/*public void handleRequest(List<String> request){
		
	}
	
	public void setClassroom(){
		console.log("set classroom called");
		//need this to hold path
		final ArrayList<String> path = new ArrayList<String>();
		//parse the path to get the roster id
		Long id = Longs.tryParse(token.get(1));
		console.log("The roster's id is " + id);
		if(id == null){
			History.back();
		}
		
		if($this.getRosterId() == null ||id != $this.getRosterId()){
			console.log("Roster ajax called made from classroom main");
			//Asyncall to get my roster with id of id
			//setView must be called after roster is set
			Ajax.post(RosterUrl.roster(id), $$("roster:" + id))
			.done( new Function(){
					@Override
					public void f(){
						classRoster = JsonUtils.safeEval((String) this.arguments(0)).cast();
						rosterTitle.setText(classRoster.getTitle());
						window.setPropertyJSO("roster", classRoster);
						//do a classtime check here to see if it is set and if it isn't
						if(window.getPropertyJSO("classtime") == null){
							//all roster have a default
							window.setPropertyJSO("classtime", classRoster.getDefaultClassTime());
						}
				
							if(token.size()>= 3){
							 path.addAll(token.subList(2, token.size()));

							 setView(path);
							}else{dashboardView();}
						
					}
			});}else{
					
						if(token.size()>= 3){
					 path.addAll(token.subList(2, token.size()));
					 //classtime may have been set
					 
					 setView(path);
					}else{dashboardView();}
						}// end else roster will be here
		
	}
	
	private void setView(List<String> path){	
		switch(path.get(0)){
		case "s":studentView(path); break;
		case "t": classTimeView(path);break;
		case "a":assignmentView(path); break;
		case "i":incidentView(path); break;
		case "j": jobView(path);break;
		case "g":goalView(path); break;
		default: dashboardView();
		}
	}
	
	private void dashboardView(){
		console.log("classmain dashboard called");
		if(mainPanel.getWidgetCount() >= 1 && mainPanel.getWidget(0) instanceof RosterDashboardPanel){
			console.log("Main panel had instance of roster dashboard already");
			return;
		}
		mainPanel.clear();
		mainPanel.add(new RosterDashboardPanel());
		rosterTitle.setText(classRoster.getTitle());
		sideNav.hide();
		console.log(classRoster);
	}
	private void studentView(final List<String>path){
		
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				
				
			}

			@Override
			public void onSuccess() {
				
				
				switch(path.size()){
				
				//rosterStudent main must handle the view of students
				case 2: mainPanel.clear();
				mainPanel.getElement().removeAllChildren();
				mainPanel.add(new StudentInfoMain().setStudent(Longs.tryParse(path.get(1))));
				rosterTitle.setText("Student Info");
				break;
				}
				
			}});
		
	}
	private void assignmentView(List<String> path){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				rosterTitle.setText("Assignments");
				
			}
			
		});
	}
	
	private void classTimeView(List<String> path){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new ClassTimeMain());
				rosterTitle.setText("Class Time");
				
			}});
		
	}
	
	private void incidentView(List<String> path){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new IncidentMain());
				rosterTitle.setText("Incidents");
				
			}});
}
	private void jobView(List<String> path){
		
}
	private void goalView(List<String> path){
	
}	
	public Long getRosterId(){
		if(classRoster == null){
			return null;
		}
			return classRoster.getId();
			
		
	}
	
	@Override
	public void onLoad(){
	
	}*/
}