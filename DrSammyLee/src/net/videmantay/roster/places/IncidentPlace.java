package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class IncidentPlace extends Place {

	String name;

	public IncidentPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<IncidentPlace> {
		@Override
		public String getToken(IncidentPlace place) {
			return place.getName();
		}

		@Override
		public IncidentPlace getPlace(String token) {
			return new IncidentPlace(token);
		}
	}
}
