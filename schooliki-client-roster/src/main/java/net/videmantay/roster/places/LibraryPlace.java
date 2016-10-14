package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LibraryPlace extends Place {

	String name;

	public LibraryPlace(String name) {

		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static class Tokenizer implements PlaceTokenizer<LibraryPlace> {
		@Override
		public String getToken(LibraryPlace place) {
			return place.getName();
		}

		@Override
		public LibraryPlace getPlace(String token) {
			return new LibraryPlace(token);
		}
	}

}
