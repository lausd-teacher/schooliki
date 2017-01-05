package net.videmantay.roster.views.classtime;

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
import net.videmantay.roster.classtime.json.ClassTimeJson;

public class ClasstimeGridItem extends Composite {

	private static ClasstimeGridItemUiBinder uiBinder = GWT.create(ClasstimeGridItemUiBinder.class);

	interface ClasstimeGridItemUiBinder extends UiBinder<Widget, ClasstimeGridItem> {
	}

	public ClasstimeGridItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	MaterialCardTitle title;
	
	@UiField
	MaterialLabel description;
	
	@UiField
	Label groupNum;
	
	@UiField
	Label procedureNum;
	
	@UiField
	MaterialAnchorButton groupBtn;
	
	@UiField
	MaterialAnchorButton procedBtn;
	
	private  ClassTimeJson classTime;

	
	public void setClassTime(ClassTimeJson classTime){
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
