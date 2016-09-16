package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialIcon;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}

	
	
	@UiField
	MaterialIcon doneBtn;
	
	@UiField
	MaterialIcon cancelBtn;
	
	@UiField
	MaterialIcon editBtn;
	
	@UiField
	MaterialIcon deleteBtn;

	public IncidentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		
		doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//save to roster  and server?
			}});
		
		editBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//show input elements hide labels
				
			}});
		
		deleteBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//show delete confirmation
				
			}});
		
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//don't save changes
			}});
	}
	
}
