package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.shared.LoginInfo;

public class UserProfilePanel extends Composite{

	private static UserProfilePanelUiBinder uiBinder = GWT.create(UserProfilePanelUiBinder.class);
	interface UserProfilePanelUiBinder extends UiBinder<Widget, UserProfilePanel> {
	}


	@UiField
	MaterialLabel name;
	@UiField
	MaterialImage img;
	@UiField
	MaterialSideProfile sideUserProfile;

	
	public UserProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		sideUserProfile.getElement().getStyle().setCursor(Cursor.POINTER);
	
	}
	
	public void setProfileInfo(String username, String imgUrl){
		name.setText(username);
		img.setUrl(imgUrl);
		
	}
	
	
	public MaterialSideProfile getSideUserProfile() {
		return this.sideUserProfile;
	}


	public interface Presenter{
		void userProfileClickeEvent();
	}

}
