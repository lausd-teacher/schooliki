package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.RoutineJson;

import static com.google.gwt.query.client.GQuery.*;

public class RoutineMain extends Composite {

	/* this class is a list of the combined classtime and classtimeconfig
	 * JsArray<FullRoutineJson> will be called here.
	 */
	private RosterJson roster = window.getPropertyJSO("roster").cast();
	private RoutineJson curClassTime = window.getPropertyJSO("classtime").cast();
	
	private static RoutineMainUiBinder uiBinder = GWT.create(RoutineMainUiBinder.class);

	interface RoutineMainUiBinder extends UiBinder<Widget, RoutineMain> {
	}

	final RosterUtils utils;
	public RoutineMain(RosterUtils ru) {
		utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		/*RoutineGridItem activeItem = new RoutineGridItem();
		activeItem.setClassTime(curClassTime);
		activeItem.addStyleName("activeClassTime");
		cardCol.add(activeItem);
		if(roster.getClassTimes().length() > 1){
		for(int i = 0; i < roster.getClassTimes().length(); i++){
			RoutineGridItem item = new RoutineGridItem();
			item.setClassTime(roster.getClassTimes().get(i));
			cardCol.add(item);
		}
		}
		*/
		
	}

}
