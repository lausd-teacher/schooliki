package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.RosterUtils;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.json.RoutineJson;

import static com.google.gwt.query.client.GQuery.*;

public class RoutineMain extends Composite {

	/* this class is a list of the combined classtime and classtimeconfig
	 * JsArray<FullRoutineJson> will be called here.
	 */
		
	private static RoutineMainUiBinder uiBinder = GWT.create(RoutineMainUiBinder.class);

	interface RoutineMainUiBinder extends UiBinder<Widget, RoutineMain> {
	}

	@UiField
	MaterialRow routineGrid;
	
	final RosterUtils utils;
	public RoutineMain(RosterUtils ru) {
		utils = ru;
		initWidget(uiBinder.createAndBindUi(this));
		RoutineGridItem activeItem = new RoutineGridItem();
		activeItem.setClassTime(utils.getSelectedClassTime().getRoutine());
		activeItem.addStyleName("activeClassTime");
		
		MaterialColumn cardCol = new MaterialColumn();
		cardCol.setGrid("s12");
		cardCol.add(activeItem);
		routineGrid.add(cardCol);
		if(utils.getClassTimes().length() > 1){
		for(int i = 0; i < utils.getClassTimes().length(); i++){
			cardCol = new MaterialColumn();
			cardCol.setGrid("s12");
			RoutineGridItem item = new RoutineGridItem();
			item.setClassTime(utils.getClassTimes().get(i));
			cardCol.add(item);
			routineGrid.add(cardCol);
		}
		}
		
		
	}

}
