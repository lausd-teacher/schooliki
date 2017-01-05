package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialNavBrand;


public class UserProfilePage extends Composite {

	private static UserProfilePageUiBinder uiBinder = GWT.create(UserProfilePageUiBinder.class);

	interface UserProfilePageUiBinder extends UiBinder<Widget, UserProfilePage> {
	}
	
	@UiField
	HTMLPanel container;
	@UiField
	static ImageElement profileImage;
	@UiField
	static SpanElement profileFirstName;
	@UiField
	static SpanElement profileLastName;
	@UiField
	static SpanElement profileEmail;
	
	static MaterialNavBrand title;
	
	static UserProfilePanel userProfilePanel;


	public UserProfilePage(MaterialNavBrand appTitle, UserProfilePanel profilePanel) {
		initWidget(uiBinder.createAndBindUi(this));
		title = appTitle;
		userProfilePanel = profilePanel;
	}
	

}
