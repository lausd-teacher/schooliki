package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ClassRoomPlace extends Place {

	String name;

	public ClassRoomPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<ClassRoomPlace> {
		@Override
		public String getToken(ClassRoomPlace place) {
			return place.getName();
		}

		@Override
		public ClassRoomPlace getPlace(String token) {
			return new ClassRoomPlace(token);
		}
	}

}
