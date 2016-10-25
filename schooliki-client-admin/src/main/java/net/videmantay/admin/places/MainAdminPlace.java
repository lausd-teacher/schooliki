package net.videmantay.admin.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainAdminPlace extends Place {
	
	String name;

	public MainAdminPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<MainAdminPlace> {
		@Override
		public String getToken(MainAdminPlace place) {
			return place.getName();
		}

		@Override
		public MainAdminPlace getPlace(String token) {
			return new MainAdminPlace(token);
		}
	}
}
