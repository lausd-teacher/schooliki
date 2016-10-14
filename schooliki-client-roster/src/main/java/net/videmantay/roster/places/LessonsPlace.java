package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LessonsPlace extends Place {

	String name;

	public LessonsPlace(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static class Tokenizer implements PlaceTokenizer<LessonsPlace> {
		@Override
		public String getToken(LessonsPlace place) {
			return place.getName();
		}

		@Override
		public LessonsPlace getPlace(String token) {
			return new LessonsPlace(token);
		}
	}

}
