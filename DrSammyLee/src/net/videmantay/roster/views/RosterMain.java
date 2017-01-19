package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialTooltip;
import net.videmantay.roster.RosterUtils;
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
	MaterialLink logoutLink;
	
	@UiField
	MaterialTooltip calendarTooltip;
	
	@UiField
	MaterialTooltip todoTooltip;
	
	@UiField
	MaterialTooltip notificationTooltip;
	
	@UiField
	UserProfilePanel profile;
	
	@UiField
	MaterialSideNav sideNav;
	private final RosterUtils utils;
	
	@UiConstructor
	public RosterMain(RosterUtils ru) {
		this.utils = ru;
		console.log("Main Roster loaded");
		console.log(utils.getRosterList());
		initWidget(uiBinder.createAndBindUi(this));
		final InfoJson info = ru.getInfo();
		console.log(info);
		profile.setProfileInfo(info);
		rosterLink.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent e){
				History.newItem("roster");
				$("div#sidenav-overlay").remove();
				$("div.drag-target").remove();
			}
		});
		
		calendarLink.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent e){
				History.newItem("calendar");
				$("div#sidenav-overlay").remove();
				$("div.drag-target").remove();
			}
		});
		logoutLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(info.logout());
				
			}});
	}
	
	public void rosters(){
		console.log("RosterMain rosters(); called");
		mainPanel.clear();
		mainPanel.add(new RosterDisplay(utils));
	}
	
	public void calendar(){
		console.log("calendars called");
		mainPanel.clear();
		mainPanel.add(new AllCalendarsPanel());
	}
	
	@Override
	public void onLoad(){
		sideNav.hide();
		sideNav.hideOverlay();
		$("div#sidenav-overlay").remove();
		$("div.drag-target").remove();
	}
	
	@Override
	public void onUnload(){
		//clean up html picker and tool tips
		$("div.picker, .material-tooltip").remove();
	}
	

}