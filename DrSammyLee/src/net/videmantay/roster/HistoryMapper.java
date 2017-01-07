package net.videmantay.roster;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.*;
import com.google.common.base.Splitter;
import com.google.common.primitives.Longs;

import java.util.List;

/*
 * parse the history token so that it is more restful
 * go by size to limit possibilites size of 1
 *    roster (default -more)/ allCalendars/profile/ allSettings/
 *   size of two size two or more con only mean roster so do roster
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
	
	public HistoryMapper(RosterUtils ru){
		this.utils = ru;
	}

	@Override
	public  void onValueChange(ValueChangeEvent<String> event) {
		
		List<String> request= Splitter.on("/").splitToList(event.getValue());
		
		if(request == null || request.size() <= 0 ){
			doDefault();
		}
		
		if(request.size() == 1){// this means you on just the landing page show landing page 
			console.log("token size is 1");
			utils.showLandingPage();
			switch(request.get(0)){
			case "calendar": utils.getLandingPage().calendar();break;
			default: utils.getLandingPage().rosters();
			}
			return;
		}//end if  equals 1 /////
		
		if(request.size() >= 2){
			console.log("token is 2 or more");
			//this is a classroom request make sure the first item is 'c' for classroom
			if(!request.get(0).equals("c")){
				doDefault();
				return;
			}
		//here we need to get the long id and set the current roster to the id
			//if no match is found back to the dashboard
		//set the current roster as id
		//gwt and long don't mix compare strings
		String id = request.get(1);
		console.log("The current roster id is " + id);
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
			utils.showClassroomPage();
		}
		if(request.size() == 2){
			utils.getClassroomPage().dashboard();
		}
		
		}//end if  2 or more/////
		
		/*if(request.size() >=3){
			//all views will start with a plus sign
			if(request.get(2).startsWith("+")){
			//TODO:	RosterUtils.getClassroomPage().dashboardChart();
			//or RosterUtils.getClassroomPage().dashboardChal();
					if(request.get(2).equalsIgnoreCase("+cal")){
					
					}else{
						
					}
					
			}else{// no plus it another page and not a view
				switch(request.get(2)){
				case "a": break;
				case "g": break;
				case "i": break;
				case "j": break;
				case "l": break;
				case "s": break;
				
				}//end switch
			}//end else its another page /////////////
		}//end if 3 or more //////
		
		if(request.size() >= 5){
			//check for view
			if(request.get(4).startsWith("+")){//it's a view
				
			}
		}*/
	}
	
	
	public void doDefault(){
		History.replaceItem("roster");
		utils.showLandingPage();
		utils.getLandingPage().rosters();
	}
	
}
