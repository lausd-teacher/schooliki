package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
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
	MaterialLabel studentName;





	public IncidentCard(String incidentName, String imageUrl, String typeId, String studentName) {
		initWidget(uiBinder.createAndBindUi(this));
		container.setWidth("100px");
		cardTitle.setText(incidentName);
		cardTitle.setFontSize(12, Unit.PX);
		incidentImage.setUrl(imageUrl);
		container.getElement().addClassName("incidentCard");
		container.setId(typeId);
		
		   if(studentName != null){
			   this.studentName.setText(studentName);
		   }
		   

		   
		   container.addDomHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					GWT.log("clicking on card");
					SelectionManager.unSelectCurrentSelectedIncidentCard();
					SelectionManager.selectIncidentCard(container);
					
				}
			   }, ClickEvent.getType());
		   
	}
	
	
	public MaterialCard getContainer() {
		return this.container;
	}
	

}
