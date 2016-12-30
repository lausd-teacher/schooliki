package net.videmantay.roster;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.RootPanel;


import gwt.material.design.client.ui.MaterialToast;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.places.RosterHomePlace;


public class Teacher implements EntryPoint {

	@Override
	public void onModuleLoad() {
        
		
		
		hideLoader();
		
		//expose function to iframe
		expose();
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
