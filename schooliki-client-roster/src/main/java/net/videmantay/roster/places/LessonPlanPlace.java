package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LessonPlanPlace extends Place {

	String name;

	public LessonPlanPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<LessonPlanPlace> {
		@Override
		public String getToken(LessonPlanPlace place) {
			return place.getName();
		}

		@Override
		public LessonPlanPlace getPlace(String token) {
			return new LessonPlanPlace(token);
		}
	}

}
