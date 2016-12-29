package net.videmantay.roster;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

import gwt.material.design.client.ui.MaterialToast;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.places.RosterHomePlace;
import net.videmantay.shared.util.GoogleJs;


public class Teacher implements EntryPoint {

	private RosterHomePlace homePlace = new RosterHomePlace("rosters");
	private SimplePanel appWidget = new SimplePanel();
	final ClientFactory clientFactory = GWT.create(ClientFactory.class);
	final PlaceController controller = clientFactory.getPlaceController();

	@Override
	public void onModuleLoad() {
        
		EventBus bus = clientFactory.getEventBus();
		ActivityMapper activityMapper = new MyActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, bus);
		activityManager.setDisplay(appWidget);
    
		MyHistoryMapper historyMapper = GWT.create(MyHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(controller, bus, homePlace);

		RootPanel.get().add(appWidget);


		historyHandler.handleCurrentHistory();
		GoogleJs.InitializeSignedInListener();
		hideLoader();
		
		//expose function to iframe
		expose();
	}
	
	
	private native void hideLoader()/*-{
		var loader = $wnd.document.getElementById("loader");
	    loader.style.visibility="hidden";
	}-*/;
	
	//need to expose certain methods to outside js
	private void expose(){
		//get window to set props
		Properties wnd = window.cast();
		
		//this is a test and Yeah it actually worked! 12/15/16
		wnd.setFunction("testThis", new Function(){
			@Override
			public void f(){
				MaterialToast.fireToast("Yeah, it worked!");
			}
		});
		//first function to set classtime for lessonPlan
		//the object selectedClassTime is exposed in the factory
		wnd.setFunction("setSelectedClassTime", new Function(){
			@Override
			public void f(){
				ClassTimeJson classTime = JsonUtils.safeEval((String)this.getArgument(0)).cast();
				clientFactory.setSelectedClassTime(classTime);
				console.log(clientFactory.getSelectedClassTime() + "called from external JS");
			}
		});
		
		// show lessonplan form
		
		// show student action panel for seatingChart
		
		//show student assignment form -assignment calendar 
		
		
		
		
		
	}//end expose
	

	

}