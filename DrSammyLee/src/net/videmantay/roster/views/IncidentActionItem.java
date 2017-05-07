package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Div;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.json.IncidentJson;
import net.videmantay.roster.views.incident.IncidentImageUtil;

public class IncidentActionItem extends Composite {

	private static IncidentActionItemUiBinder uiBinder = GWT.create(IncidentActionItemUiBinder.class);

	@UiField
	public Div img;
	
	@UiField
	public MaterialLabel label;
	
	@UiField
	public  MaterialChip value;
	
	interface IncidentActionItemUiBinder extends UiBinder<Widget, IncidentActionItem> {
	}

	public IncidentActionItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(IncidentJson incident){
		$(this).data("incident", incident);
		img.getElement().setInnerHTML(IncidentImageUtil.imageHTML(incident.getImageUrl()));
		label.setText(incident.getName());
		if(incident.getPoints() < 0){
			value.setBackgroundColor("red");
		}
		value.setText(incident.getPoints()+"");
		
	}
	
	public IncidentJson getData(){
		return $(this).data("incident", IncidentJson.class);
	}
	
	

}
