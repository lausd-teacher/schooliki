package net.videmantay.roster.views.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.IncidentJson;

public class IncidentItem extends Composite{

	private static IncidentItemUiBinder uiBinder = GWT.create(IncidentItemUiBinder.class);

	interface IncidentItemUiBinder extends UiBinder<Widget, IncidentItem> {
	}
	
	@UiField
	DivElement svgImage;
	
	@UiField
	MaterialLabel name;
	
	@UiField
	MaterialChip value;

	public IncidentItem() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	private IncidentJson incident;
	
	public void setIncident(IncidentJson incident){
		this.incident = incident;
		console.log(incident);
		draw();
	}
	
	public IncidentJson getIncident(){
		return this.incident;
		
	}
	
	private void draw(){
		
		name.setText(incident.getName());
		if(incident.getValue() < 0){
			//badge color red;
			value.setBackgroundColor("red");
		}else{
			//badge color green
			value.setBackgroundColor("green");
		}
		
		value.setText(""+ incident.getValue());
		///set up the badge size and center the text
		
		//set up the html
		String html = "<svg viewBox='0 0 150 200' class='incidentIcon' style='width:7em; height:8em'>"
				+"<use  xmlns:xlink='http://www.w3.org/1999/xlink' xlink:href='../img/allIcons.svg#" 
				+"' /></svg>";
		svgImage.setInnerHTML(html);
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
