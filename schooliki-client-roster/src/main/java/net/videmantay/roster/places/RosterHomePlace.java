package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RosterHomePlace extends Place {
	String name;

	public RosterHomePlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<RosterHomePlace> {
		@Override
		public String getToken(RosterHomePlace place) {
			return place.getName();
		}

		@Override
		public RosterHomePlace getPlace(String token) {
			return new RosterHomePlace(token);
		}
	}

}
