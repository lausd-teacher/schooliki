package net.videmantay.roster.views.lessonPlan;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

public class LessonPlanMain extends Composite {

	private static LessonPlanMainUiBinder uiBinder = GWT.create(LessonPlanMainUiBinder.class);

	interface LessonPlanMainUiBinder extends UiBinder<Widget, LessonPlanMain> {
	}

	
	
	@UiField
	Frame lessonPlanCal;
	
	@UiField
	MaterialPanel emptyCal;
	
	
	public LessonPlanMain() {
		initWidget(uiBinder.createAndBindUi(this));
		if(hasLessonPlanCalendar()){
			showEmpty();
		}else{
			hideEmpty();
		}
	}
	
	
	
	public boolean hasLessonPlanCalendar(){
		
		return false;
	}
	
	public void showEmpty(){
		//if there is no calendar set
		//hide the frame and show empty cal prompt
	}
	
	public void hideEmpty(){
		// if the empty prompt is present then the frame is hidden
		//hide empty
		//show frame
	}
	
	public void setCalendar(String calendarId){
		
	}
	
	
	
	

}
