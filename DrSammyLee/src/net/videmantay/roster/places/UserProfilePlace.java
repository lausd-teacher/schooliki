package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserProfilePlace extends Place {

	String name;

	public UserProfilePlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<UserProfilePlace> {
		@Override
		public String getToken(UserProfilePlace place) {
			return place.getName();
		}

		@Override
		public UserProfilePlace getPlace(String token) {
			return new UserProfilePlace(token);
		}
	}
}
