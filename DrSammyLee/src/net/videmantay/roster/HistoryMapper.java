package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.roster.json.RosterConfigJson;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.RoutineConfigJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.routine.json.FullRoutineJson;
import net.videmantay.roster.views.ClassroomMain;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import com.google.common.base.Splitter;
import com.google.common.primitives.Longs;

import java.util.List;

/*
 * parse the history token so that it is more restful
 * go by size to limit possibilites size of 1
 *    roster (default -more)/ allCalendars/profile/ allSettings/
 *   size of two size two or more can only mean roster so do roster
 *   	view - +cal | +chart
 *   'c' - classroom (roster) /must follow c/{id}/(possible)view
 *   ________________________________________________________________
 *                    3rd level
 *   'i' - incident / must follow c/{id}/i/(possible){id}/view
 *   'l' -lessonPlans/must follow c/{id}/l/(possible){id}
 *   'g' - goals/must follow c/{id}/g/(possible){id}/view
 *   'a' -assignments/must follow c/{id}/a/(possible){id}/view
 *   's' - student/must follow c/{id}/s/(required){id} /view
 *   'j' - jobs/ must follow c/{id}/j/(possible){id}/view
 *   ____________________________________________________________________
 *                       5th level
 *   //as student list is handled on dashboard the student page must have id ////
 *   'sw' - studentWork /must follow c/{id}/s/{id}/sw/ (possible){id}
 *   'sg' -studentGoal must follow c/{id}/s/{id}/sg/ (possible){id}
 *   'si' -studentIncidents must follow c/{id}/s/{id}/si/ (possible){id}
 *   'sj' -studentJobs/ must follow c/{id}/s/{id}/sj/ (possible){id}
 *   
 */
public class HistoryMapper implements ValueChangeHandler<String>{
	
	

	private final RosterUtils utils;
	final private HTMLPanel html = new HTMLPanel("<div>There was an error loading the page!\n "+
			"Please refresh <i class='material-icons'>refresh</i></div>");
	final HistoryToken historyToken = new HistoryToken();
	
	

	//for loading async pages

			
		RunAsyncCallback classroomGet =new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				RootPanel.get().clear();
				RootPanel.get().add(html);
			}

			@Override
			public void onSuccess() {
				if(!historyToken.getToken().get(0).equals("c")){
					doDefault();
					return;
				}
				
				console.log("token is 2 or more");
				
			
					//this is a classroom request make sure the first item is 'c' for classroom
					
				//here we need to get the long id and set the current roster to the id
					//if no match is found back to the dashboard
				//set the current roster as id
				//gwt and long don't mix compare strings
				String id = historyToken.getToken().get(1);
				console.log("The current roster id is " + id);
				//if the current roster is the one being called just return
				if(utils.getCurrentRoster() != null && utils.getCurrentRoster().getId() == Longs.tryParse(id)){
					console.log("roster is loaded just skip the AJAX");
					utils.setClassroomPage(new ClassroomMain(utils));
					utils.showClassroomPage();
					return;
				}
				//shuffle throug roste list for id
				boolean noMatch = true;
				JsArray<RosterJson> rosList = utils.getRosterList();
				for(int i = 0; i < rosList.length(); i++){
					console.log("cycled through roster list " + (i + 1) + " times and roster id is " + rosList.get(i).getId());
					String rosId = "" +rosList.get(i).getId();
					if(id.equals(rosId)){
						utils.setCurrentRoster(rosList.get(i));
						noMatch = false;
						break;
					}
				}//end for
					//we cycled through and there was no match
				if(noMatch){
					console.log("no match back to the drawing board");
					History.newItem("roster");
				}else{
					//need to load the rosterconfig
					Ajax.get(RosterUrl.roster(id))
					.done(new Function(){
						@Override
						public void f(){
							console.log("Ajax call to get roster object returned is: ");
							RosterConfigJson rcj =  JsonUtils.safeEval((String)this.arguments(0)).cast();
							console.log(rcj);
							if(rcj != null){
							utils.setStudents(rcj.getStudents());
							
							
								utils.setClassTimes(rcj.getClassTimes());
								
								for(int i=0; i < rcj.getClassTimes().length(); i++){
									if(utils.getClassTimes().get(i).getIsDefault()){
										FullRoutineJson sr = FullRoutineJson.createObject().cast();
										sr.setRoutine(utils.getClassTimes().get(i));
										sr.setRoutineConfig(rcj.getDefaultTime());
										utils.setSelectedClassTime(sr);
										break;
									}
								}
							//now that everything is in place we init the classmain
							utils.setClassroomPage(new ClassroomMain(utils));
							utils.showClassroomPage();
						}//end if ros config not null
					}
					});
					
					
						//at this point there is a three
						
				
				}//end else noMatch
				
				
				}//end success
				
		};

	
	public HistoryMapper(RosterUtils ru){
		this.utils = ru;
	}

	@Override
	public  void onValueChange( ValueChangeEvent<String> event) {
		
		historyToken.setToken(Splitter.on("/").splitToList(event.getValue()));
		
		if(historyToken.getToken() == null || historyToken.getToken().size() <= 0 ){
			doDefault();
		}
		
		if(historyToken.getToken().size() == 1){// this means you on just the landing page show landing page 
			 doLandingPage();
		}//end if  equals 1 /////
		
		if(historyToken.getToken().size() >= 2){
			doClassroomPage();
		}//end if  2 or more/////
		
		return;
	}
		
		
	public void doDefault(){
		History.replaceItem("roster");
		utils.showLandingPage();
		utils.getLandingPage().rosters();
	}
	
	private void doLandingPage(){
		if(utils.isClassFirstLoad()){
			this.doLandingFirstLoad();
			return;
		}
		utils.showLandingPage();
		switch(historyToken.getToken().get(0)){
		case "calendar": utils.getLandingPage().calendar();break;
		default: utils.getLandingPage().rosters();
		}
		return;
	}
	private void doClassroomPage(){
		GWT.runAsync(ClassroomMain.class, classroomGet);
	}
	
	private class HistoryToken{
		List<String> token;
		public List<String> getToken(){
			return this.token;
		}
		public void setToken(List<String> token){
			this.token = token;
		}
	}
	private void doLandingFirstLoad(){
		utils.showLandingPage();
		MaterialLoader.showLoading(true);
		utils.setClassFirstLoad(false);
		new Timer(){
			@Override
			public void run(){
				History.replaceItem("roster");
				utils.getLandingPage().rosters();
				MaterialLoader.showLoading(false);
			}
		}.schedule(100);
	}

		
}	


		

	