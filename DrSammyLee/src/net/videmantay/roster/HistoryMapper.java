package net.videmantay.roster;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.common.base.Splitter;
import java.util.List;

/*
 * parse the history token so that it is more restful
 * go by size to limit possibilites size of 1
 *    roster (drfault -more)/ allCalendars/profile/ allSettings/
 *   size of two size two or more con only mean roster so do roster
 *   	
 */
public class HistoryMapper implements ValueChangeHandler<String>{

	public HistoryMapper(){}

	@Override
	public  void onValueChange(ValueChangeEvent<String> event) {
		List<String> request= Splitter.on("/").splitToList(event.getValue());
		
		if(request == null || request.size() <= 0 ){
			doDefault();
		}
		
		if(request.size() == 1){// this means you on just the landing page show landing page 
			RosterUtils.showLandingPage(request.get(0));
		}//end if 1
		
		if(request.size() >= 2){
			//this is a classroom request make sure the first item is 'c' for classroom
			if(!request.get(0).equals("c")){
				doDefault();
				return;
			}
		RosterUtils.showClassroomPage(request);
			
		}
	}
	
	public void doDefault(){
		History.newItem("roster");
		History.fireCurrentHistoryState();
		RosterUtils.showLandingPage("roster");
	}
	
}
