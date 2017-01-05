package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import net.videmantay.shared.LoginInfo;
import net.videmantay.student.json.InfoJson;

public class RosterMain extends Composite {

	
	private static RosterMainUiBinder uiBinder = GWT.create(RosterMainUiBinder.class);

	interface RosterMainUiBinder extends UiBinder<Widget, RosterMain> {
	}
	
	@UiField
	MaterialContainer mainPanel;
	

	@UiField
	MaterialLink rosterLink;
	
	@UiField
	MaterialLink calendarLink;
	
	@UiField
	MaterialTooltip calendarTooltip;
	
	@UiField
	MaterialTooltip todoTooltip;
	
	@UiField
	MaterialTooltip notificationTooltip;
	
	@UiField
	UserProfilePanel profile;
	
	public RosterMain() {
		console.log("Main Roster loaded");
		initWidget(uiBinder.createAndBindUi(this));
		InfoJson info = window.getPropertyJSO("info").cast();
		console.log(info);
		profile.setProfileInfo(info);
	}
	
	@Override 
	public void onLoad(){
	
	}
	
	public void handleRequest(String request){
		
	}
	
	public void rosters(){
		console.log("RosterMain rosters(); called");
		mainPanel.clear();
		mainPanel.add(new RosterDisplay());
	}
	
	public void setting(){
		mainPanel.clear();
		mainPanel.add(new RosterSettingsPanel());
	}
	
	public void calendars(){
		mainPanel.clear();
		mainPanel.add(new AllCalendarsPanel());
	}
	

}