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

	public MyActivityMapper(ClientFactory factory) {
		this.factory = factory;

	}

	@Override
	public Activity getActivity(Place place) {
		
		
		
		if (place instanceof RosterHomePlace || place instanceof CalendarPlace ||  place instanceof LessonsPlace || place instanceof LibraryPlace || place instanceof SettingsPlace || place instanceof UserProfilePlace) {
		
			if(rosterActivity == null){
				 rosterActivity = new RosterDisplayActivity(factory, place);
			 }else{
				 if(classRoomActivity != null)
				     classRoomActivity.resetRosterDataLists();
				 
				 rosterActivity.setPlace(place);
			 }
			return rosterActivity;
		} else {
			if(classRoomActivity == null){
				classRoomActivity = new ClassRoomActivity(factory, place);
			 }else{
				 
				 classRoomActivity.setPlace(place);
			 }
			return classRoomActivity;

		}
		
	

	}
}
