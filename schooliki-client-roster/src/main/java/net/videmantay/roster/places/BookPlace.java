package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BookPlace extends Place {

	
	String name;

	public BookPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<BookPlace> {
		@Override
		public String getToken(BookPlace place) {
			return place.getName();
		}

		@Override
		public BookPlace getPlace(String token) {
			return new BookPlace(token);
		}
	}
}
