package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CalendarPlace extends Place {

	String name;

	public CalendarPlace(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static class Tokenizer implements PlaceTokenizer<CalendarPlace> {
		@Override
		public String getToken(CalendarPlace place) {
			return place.getName();
		}

		@Override
		public CalendarPlace getPlace(String token) {
			return new CalendarPlace(token);
		}
	}

}
