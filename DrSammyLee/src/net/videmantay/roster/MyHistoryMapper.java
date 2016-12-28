package net.videmantay.roster;



import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import net.videmantay.roster.places.*;


@WithTokenizers({ClassRoomPlace.Tokenizer.class, RosterHomePlace.Tokenizer.class, CalendarPlace.Tokenizer.class, LessonsPlace.Tokenizer.class, 
	LibraryPlace.Tokenizer.class, SettingsPlace.Tokenizer.class, AssignementPlace.Tokenizer.class, BookPlace.Tokenizer.class, ClassRoomPlace.Tokenizer.class, ClassTimePlace.Tokenizer.class, 
	FormPlace.Tokenizer.class, GoalPlace.Tokenizer.class, IncidentPlace.Tokenizer.class, JobPlace.Tokenizer.class, LessonPlanPlace.Tokenizer.class, UserProfilePlace.Tokenizer.class })
public interface MyHistoryMapper extends PlaceHistoryMapper  {
	
}

