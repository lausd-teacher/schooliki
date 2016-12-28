package net.videmantay.admin;



import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import net.videmantay.admin.places.MainAdminPlace;



@WithTokenizers({MainAdminPlace.Tokenizer.class })
public interface MyHistoryMapper extends PlaceHistoryMapper  {

}

