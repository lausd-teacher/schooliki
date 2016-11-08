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

	public ClasstimeGridItem(String title, String description, String classTimeId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title.setText(title);
		this.description.setText(description);
		this.classTimeId = classTimeId;
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
	
	String classTimeId;

	
}
