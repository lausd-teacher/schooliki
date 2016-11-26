package net.videmantay.roster;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import net.videmantay.roster.activities.*;
import net.videmantay.roster.places.*;

public class MyActivityMapper implements ActivityMapper {

	ClientFactory factory;
	static RosterDisplayActivity rosterActivity = null;
	static ClassRoomActivity classRoomActivity = null;
	Place currentPlace = null;
	boolean firstPlace = true;

	public MyActivityMapper(ClientFactory factory) {
		this.factory = factory;

	}

	@Override
	public Activity getActivity(Place place) {
		
		
		if (place instanceof RosterHomePlace || place instanceof CalendarPlace ||  place instanceof LessonsPlace || place instanceof LibraryPlace || place instanceof SettingsPlace || place instanceof UserProfilePlace) {
		    
			if(rosterActivity == null){
				 rosterActivity = new RosterDisplayActivity(factory, place);
				 firstPlace = false;
			 }else{
				 if(classRoomActivity != null)
				     classRoomActivity.resetRosterDataLists();
				 
				 rosterActivity.setPlace(place);
			 }
			
			return rosterActivity;
		} else {
			if(classRoomActivity == null){
				if(!firstPlace){
				classRoomActivity = new ClassRoomActivity(factory, place);
				firstPlace = false;
				}else{
					rosterActivity = new RosterDisplayActivity(factory, new RosterHomePlace("rosters"));
					firstPlace = false;
					return rosterActivity;
				}
				
			 }else{
				 classRoomActivity.setPlace(place);
			 }
			
			return classRoomActivity;
		}
		
		
		
		
	

	}
	
		
	
}
