package net.videmantay.roster.views.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Label;
import net.videmantay.roster.routine.json.RoutineJson;

public class RoutineGridItem extends Composite {

	private static RoutineGridItemUiBinder uiBinder = GWT.create(RoutineGridItemUiBinder.class);

	interface RoutineGridItemUiBinder extends UiBinder<Widget, RoutineGridItem> {
	}

	public RoutineGridItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	private  RoutineJson classTime;

	
	public void setClassTime(RoutineJson classTime){
		this.classTime = classTime;
		draw();
	}
	
	private void draw(){
	/*	title.setText(classTime.getTitle());
		description.setText(classTime.getDescript());
		String groupNumText = "0";
		if(classTime.getGroups() != null){
			groupNumText=classTime.getGroups().length() + "";
		}
		groupNum.setText(groupNumText);
		
		String procedNumText = "0";
		if(classTime.getProcedures() != null){
			procedNumText=classTime.getProcedures().length() + "";
		}
		procedureNum.setText(procedNumText);*/
	}
}
