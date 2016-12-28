package net.videmantay.roster.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SettingsPlace extends Place {

	String name;

	public SettingsPlace(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<SettingsPlace> {
		@Override
		public String getToken(SettingsPlace place) {
			return place.getName();
		}
		@Override
		public SettingsPlace getPlace(String token) {
			return new SettingsPlace(token);
		}
	}

}
