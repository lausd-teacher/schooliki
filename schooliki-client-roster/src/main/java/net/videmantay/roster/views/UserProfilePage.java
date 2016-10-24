package net.videmantay.roster.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class UserProfilePage extends Composite {

	private static UserProfilePageUiBinder uiBinder = GWT.create(UserProfilePageUiBinder.class);

	interface UserProfilePageUiBinder extends UiBinder<Widget, UserProfilePage> {
	}
	
	@UiField
	HTMLPanel container;

	public UserProfilePage() {
		initWidget(uiBinder.createAndBindUi(this));
		//GoogleJs.renderGoogleProfile();
		Element profileElement = Document.get().getElementById("profile");
		Element temp = profileElement;
		temp.getStyle().setDisplay(Display.BLOCK);
		container.add(HTMLPanel.wrap(temp));
	}
}
