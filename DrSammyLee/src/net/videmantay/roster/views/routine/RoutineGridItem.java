package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.routine.json.FullRoutineJson;

public class RoutineGridItem extends Composite {

	private static RoutineGridItemUiBinder uiBinder = GWT.create(RoutineGridItemUiBinder.class);

	interface RoutineGridItemUiBinder extends UiBinder<Widget, RoutineGridItem> {
	}

	@UiField
	public HTMLPanel routineItem;
	
	@UiField
	public MaterialCardTitle title;
	
	@UiField
	public MaterialLabel description; 
	
	@UiField
	public MaterialBadge procedureBadge;
	
	@UiField
	public MaterialBadge groupsBadge;
	
	@UiField
	public MaterialBadge stationBadge;
	
	
	public RoutineGridItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	private  FullRoutineJson routine;

	
	public void setData(FullRoutineJson routine){
		this.routine = routine;
		$(routineItem).data("routine", this.routine);
		draw();
	}
	
	private void draw(){
		title.setText(routine.getRoutine().getTitle());
		description.setText(routine.getRoutine().getDescript());
		String groupNumText = "0";
		if(routine.getRoutine().getGroup() != null){
			groupNumText=routine.getRoutine().getGroup().length() + "";
		}
		groupsBadge.setText(groupNumText);
		
		String procedNumText = "0";
		if(routine.getRoutine().getProcedures() != null){
			procedNumText=routine.getRoutine().getProcedures().length() + "";
		}
		procedureBadge.setText(procedNumText);
	}
}
