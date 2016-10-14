package net.videmantay.roster;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;
import net.videmantay.roster.activities.*;
import net.videmantay.roster.places.*;

public class MyActivityMapper implements ActivityMapper {

	ClientFactory factory;
	static RosterDisplayActivity rosterActivity;
	static ClassRoomActivity classRoomActivity;

	public MyActivityMapper(ClientFactory factory) {
		this.factory = factory;

	}

	@Override
	public Activity getActivity(Place place) {
		
		
		
		if (place instanceof RosterHomePlace || place instanceof CalendarPlace ||  place instanceof LessonsPlace || place instanceof LibraryPlace || place instanceof SettingsPlace) {
		
			if(rosterActivity == null){
				 rosterActivity = new RosterDisplayActivity(factory, place);
			 }else{
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
