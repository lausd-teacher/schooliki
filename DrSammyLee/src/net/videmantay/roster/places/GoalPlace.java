package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GoalPlace extends Place {

	String name;

	public GoalPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<GoalPlace> {
		@Override
		public String getToken(GoalPlace place) {
			return place.getName();
		}

		@Override
		public GoalPlace getPlace(String token) {
			return new GoalPlace(token);
		}
	}

}
