package net.videmantay.roster.views;
import com.google.common.primitives.Longs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
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
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.views.assignment.GradedWorkMain;
import net.videmantay.roster.views.classtime.ClassTimeMain;
import net.videmantay.roster.views.incident.IncidentMain;
import net.videmantay.roster.json.IncidentReportJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.views.student.StudentInfoMain;
import net.videmantay.roster.views.UserProfilePanel;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.query.client.GQuery.*;

public class ClassroomMain extends Composite{

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}
	public interface HasUpdateClassTime{public void updateClassTime();}

	final ClassroomMain $this;
	
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
		//classroom.setId("classroom");
			$this = this;
			//set side nav links/////////
			profilePanel.addDomHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("rosters");
					
				}}, ClickEvent.getType());
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId());
					new Timer(){

						@Override
						public void run() {
							sideNav.hide();
						}}.schedule(250);
				}
				
			});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId() +"/assignments");
					new Timer(){

						@Override
						public void run() {
							sideNav.hide();
						}}.schedule(250); 
					
				}});
			
			incidentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId() +"/incidents");
					new Timer(){

						@Override
						public void run() {
							sideNav.hide();
						}}.schedule(250); 
					
				}});
			
			classTimeLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId() +"/classtime");
					new Timer(){

						@Override
						public void run() {
							sideNav.hide();
						}}.schedule(250); 
					
				}});
		// End set up Side nav Links///////////////////
			
			
	}//end constructor
	

	public void handleRequest(List<String> request){
		
	}
	
	public void setClassroom(final ArrayList<String> token){
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
			Ajax.post(RosterUrl.GET_ROSTER, $$("roster:" + id))
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
		case "students":studentView(path); break;
		case "classtime": classTimeView(path);break;
		case "assignments":assignmentView(path); break;
		case "incidents":incidentView(path); break;
		case "jobs": jobView(path);break;
		case "goals":goalView(path); break;
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
				mainPanel.add(new GradedWorkMain());
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
	
	}
}