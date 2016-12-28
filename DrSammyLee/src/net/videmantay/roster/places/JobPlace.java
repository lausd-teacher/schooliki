package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class JobPlace extends Place {

	String name;

	public JobPlace(String placeName) {
		this.name = placeName;
	}

	public String getName() {
		return name;
	}

	public static class Tokenizer implements PlaceTokenizer<JobPlace> {
		@Override
		public String getToken(JobPlace place) {
			return place.getName();
		}

		@Override
		public JobPlace getPlace(String token) {
			return new JobPlace(token);
		}
	}

}
