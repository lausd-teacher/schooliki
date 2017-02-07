package net.videmantay.roster;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.History;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.RoutineJson;
import net.videmantay.roster.views.RosterMain;



public class Teacher implements EntryPoint {
	

private final RosterUtils utils;
private final  HistoryMapper mapper;


	Teacher(){
		utils = new RosterUtils();
		mapper = new HistoryMapper(utils);
	}

	@Override
	public void onModuleLoad() {
		//seems first thing to is to make an ajax call  
		//load the roster and then show roster main.
		History.addValueChangeHandler(mapper);
		Ajax.get(RosterUrl.roster()).done(new Function(){
			@Override
			public void f(){
				JsArray<RosterJson> rosters = JsonUtils.safeEval((String)this.getArgument(0)).cast();
				//set the roster list in rosterUtils
				utils.setRosterList(rosters);
				console.log("ON MODULE LOAD: rosters loaded");
				console.log(rosters);
				 //hide the loader
				hideLoader();
				History.newItem("roster");
				History.fireCurrentHistoryState();	
			}
		});
	}//end module load//////
		
	private native void hideLoader()/*-{
		var loader = $wnd.document.getElementById("loader");
	    loader.style.visibility="hidden";
	}-*/;
	
}
