package net.videmantay.roster.classtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import net.videmantay.roster.classtime.json.ClassTimeJson;
import net.videmantay.roster.json.RosterJson;

import static com.google.gwt.query.client.GQuery.*;

public class ClassTimeMain extends Composite {

	@UiField
	MaterialColumn cardCol;
	
	private RosterJson roster = window.getPropertyJSO("roster").cast();
	private ClassTimeJson curClassTime = window.getPropertyJSO("classtime").cast();
	
	private static ClassTimeMainUiBinder uiBinder = GWT.create(ClassTimeMainUiBinder.class);

	interface ClassTimeMainUiBinder extends UiBinder<Widget, ClassTimeMain> {
	}

	
	public ClassTimeMain() {
		initWidget(uiBinder.createAndBindUi(this));
		ClasstimeGridItem activeItem = new ClasstimeGridItem();
		activeItem.setClassTime(curClassTime);
		activeItem.addStyleName("activeClassTime");
		cardCol.add(activeItem);
		if(roster.getClassTimes().length() > 1){
		for(int i = 0; i < roster.getClassTimes().length(); i++){
			ClasstimeGridItem item = new ClasstimeGridItem();
			item.setClassTime(roster.getClassTimes().get(i));
			cardCol.add(item);
		}
		}
		
		
	}

}
