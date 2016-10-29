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
import net.videmantay.roster.ClientFactory;


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
	


	public UserProfilePage(MaterialNavBrand appTitle) {
		initWidget(uiBinder.createAndBindUi(this));
		title = appTitle;
		renderProfileIfSuccessfull();
	}
	
	public native void renderProfileIfSuccessfull()/*-{
			$wnd.gapi.load('auth2', function(){
	        var auth2 = $wnd.gapi.auth2.init({
	            client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
	        }).then(function(){
	       	             var instance = $wnd.gapi.auth2.getAuthInstance();
		            	 var profile = instance.currentUser.get().getBasicProfile();
		            	@net.videmantay.roster.views.UserProfilePage::renderProfile(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(profile.getFamilyName(), profile.getGivenName(), profile.getEmail(), profile.getImageUrl()); 
	           });
	       });
	}-*/;
	
	
	private static void renderProfile(String givenName, String familyName, String eMail, String profilePictureUrl){
		GWT.log("rendering profile method");
		profileImage.setSrc(profilePictureUrl);
		profileFirstName.setInnerText(givenName);
		profileLastName.setInnerHTML(familyName);
		profileEmail.setInnerHTML(eMail);
		title.setText(givenName + " " + familyName);
	}
	
	public String getProfileFullName(){
		return profileFirstName.getInnerHTML() + " " + profileLastName.getInnerHTML();
		
	}
	
    public String getProfilePictureUrl(){
		return profileImage.getSrc();
		
	}
}
