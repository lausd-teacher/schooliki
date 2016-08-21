package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.IncidentJson;

public class IncidentItem extends Composite{

	private static IncidentItemUiBinder uiBinder = GWT.create(IncidentItemUiBinder.class);

	interface IncidentItemUiBinder extends UiBinder<Widget, IncidentItem> {
	}
	
	@UiField
	MaterialImage icon;
	
	@UiField
	MaterialLabel name;
	
	@UiField
	MaterialBadge value;

	public IncidentItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public IncidentItem(IncidentJson incident){
		this();
		this.incident = incident;
	}
	
	private IncidentJson incident;
	
	public void setIncident(IncidentJson incident){
		this.incident = incident;
		draw();
	}
	
	public IncidentJson getIncident(){
		return this.incident;
	}
	
	public void draw(){
		icon.setUrl(incident.getIconUrl());
		name.setText(incident.getName());
		if(incident.getValue() < 0){
			//badge color red;
		}else{
			//badge color green
		}
		
		value.setText(""+ incident.getValue());
		///set up the badge size and center the text
	}
	
	@Override
	public void onLoad(){
		$(this.getElement()).click(new Function(){
			@Override
			public void f(){
				$(body).trigger("incidentPicked", incident);
			}
		});
	}

}
