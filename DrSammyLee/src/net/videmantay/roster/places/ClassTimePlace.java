package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ClassTimePlace extends Place {

	String name;

	public ClassTimePlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<ClassTimePlace> {
		@Override
		public String getToken(ClassTimePlace place) {
			return place.getName();
		}

		@Override
		public ClassTimePlace getPlace(String token) {
			return new ClassTimePlace(token);
		}
	}

}
