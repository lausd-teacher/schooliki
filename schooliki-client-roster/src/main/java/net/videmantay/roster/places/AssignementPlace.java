package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AssignementPlace extends Place {
	  
	
	String name;

	public AssignementPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<AssignementPlace> {
		@Override
		public String getToken(AssignementPlace place) {
			return place.getName();
		}

		@Override
		public AssignementPlace getPlace(String token) {
			return new AssignementPlace(token);
		}
	}

}
