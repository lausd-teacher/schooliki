package net.videmantay.admin;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import net.videmantay.admin.activities.MainPageActivity;


public class MyActivityMapper implements ActivityMapper {

	ClientFactory factory;
	static MainPageActivity adminActivity;

	public MyActivityMapper(ClientFactory factory) {
		this.factory = factory;

	}

	@Override
	public Activity getActivity(Place place) {
		
		
		
			if(adminActivity == null){
				 adminActivity = new MainPageActivity(factory, place);
			 }else{
				 adminActivity.setPlace(place);
			 }
			return adminActivity;
		
			
			
			
		
		
	

	}
}
