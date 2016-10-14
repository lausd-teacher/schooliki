package net.videmantay.roster.views.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;


public class MainRosterSideNav extends Composite {

	private static MainRosterSideNavUiBinder uiBinder = GWT.create(MainRosterSideNavUiBinder.class);

	interface MainRosterSideNavUiBinder extends UiBinder<Widget, MainRosterSideNav> {
	}
	
	@UiField
	MaterialLink rosterLink;
	
	@UiField
	MaterialLink calendarLink;
	
	@UiField
	MaterialLink libraryLink;
	
	@UiField
	MaterialLink lessonsLink;
	
	@UiField
	MaterialLink settingsLink;
	


	public MainRosterSideNav() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public MaterialLink getRosterLink() {
		return this.rosterLink;
	}

	public MaterialLink getCalendarLink() {
		return this.calendarLink;
	}

	public MaterialLink getLibraryLink() {
		return this.libraryLink;
	}

	public MaterialLink getLessonsLink() {
		return this.lessonsLink;
	}
	

	public MaterialLink getSettingsLink() {
		return this.settingsLink;
	}



	
	 public interface Presenter {
		 
		 void rosterLinkClick();
		 void calendarLinkClick();
		 void libraryLinkClick();
		 void lessonsLinkClick();
		 void settingsLinkClick();
		 
	 }
	

}
