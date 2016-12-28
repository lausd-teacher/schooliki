package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.IncidentTypeJson;
import net.videmantay.roster.views.draganddrop.SelectionManager;

public class IncidentCard extends Composite {

	private static IncidentCardUiBinder uiBinder = GWT.create(IncidentCardUiBinder.class);

	interface IncidentCardUiBinder extends UiBinder<Widget, IncidentCard> {
	}
	
	
	@UiField
	MaterialCard container;
	
	@UiField
	MaterialLabel cardTitle;
	
	@UiField
	MaterialImage incidentImage;
	
	@UiField
	MaterialBadge pointsBadge;
	

	public IncidentCard(IncidentTypeJson incidentTypeJson) {
		initWidget(uiBinder.createAndBindUi(this));
		container.setWidth("100px");
		cardTitle.setText(incidentTypeJson.getName());
		cardTitle.setFontSize(12, Unit.PX);
		incidentImage.setUrl(incidentTypeJson.getImageUrl());
		pointsBadge.setText(String.valueOf(incidentTypeJson.getPoints()));
		
		  if(incidentTypeJson.getPoints() > 0){
			  pointsBadge.getElement().getStyle().setBackgroundColor("green");
		  }else {
			  pointsBadge.getElement().getStyle().setBackgroundColor("red");
		  }
		container.getElement().addClassName("incidentCard");
		container.getElement().setId(incidentTypeJson.getId());   
	}
	
	
	public MaterialCard getContainer() {
		return this.container;
	}
	

}
