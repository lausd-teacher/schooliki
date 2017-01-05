package net.videmantay.roster;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.classtime.json.ClassTimeJson;



public class Teacher implements EntryPoint {
	
private final  HistoryMapper mapper = new HistoryMapper();

	@Override
	public void onModuleLoad() {
		
	
	//handle navigation
        History.addValueChangeHandler(mapper);
        if(History.getToken() == null || History.getToken().isEmpty()){
			console.log("History was null so mapper do default called");
			mapper.doDefault();
		}else{
			History.fireCurrentHistoryState();
		}
     //hide the loader
		hideLoader();
	//expose function to iframe
		expose();
		//RootPanel.get().add(new HTMLPanel("<h1>I am a teacher!</h1>"));
		
	}//end module load//////
	
	
	private native void hideLoader()/*-{
		var loader = $wnd.document.getElementById("loader");
	    loader.style.visibility="hidden";
	}-*/;
	
	//need to expose certain methods to outside js
	private void expose(){
		//get window to set props
		Properties wnd = window.cast();
		
		
		//first function to set classtime for lessonPlan
		//the object selectedClassTime is exposed in the factory
		wnd.setFunction("setSelectedClassTime", new Function(){
			@Override
			public void f(){
				ClassTimeJson classTime = JsonUtils.safeEval((String)this.getArgument(0)).cast();
				RosterUtils.setSelectedClassTime(classTime);
				console.log(RosterUtils.getSelectedClassTime() + "called from external JS");
			}
		});
		
		// show lessonplan form
		
		// show student action panel for seatingChart
		
		//show student assignment form -assignment calendar 
		
		
		
		
		
	}//end expose
	

	

}
