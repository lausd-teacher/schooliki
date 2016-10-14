package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FormPlace extends Place {
	String name;

	public FormPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<FormPlace> {
		@Override
		public String getToken(FormPlace place) {
			return place.getName();
		}

		@Override
		public FormPlace getPlace(String token) {
			return new FormPlace(token);
		}
	}
}
