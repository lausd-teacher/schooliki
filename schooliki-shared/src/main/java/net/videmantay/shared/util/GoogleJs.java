package net.videmantay.shared.util;

public class GoogleJs {
	
	
	
	public native static void logout()/*-{
		$wnd.signOut();
     }-*/;
	
	public native static void renderGoogleProfile()/*-{
	     
	     $wnd.console.log("document content loaded");
	$wnd.gapi.load('auth2', function(){
     
        var auth2 = $wnd.gapi.auth2.init({
            client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
        }).then(function(){
       	         var instance = $wnd.gapi.auth2.getAuthInstance();
	            	 var profile = instance.currentUser.get().getBasicProfile();
	            	 var currentuser = {"email":profile.getEmail(), "name": profile.getName(), "imageUrl":profile.getImageUrl()};
	            	 
	            	 var profilepicimage = $wnd.document.getElementById("profileimg");
	            	 var firsnameSpan = $wnd.document.getElementById("profilefname");
	            	 var lastNameSpan = $wnd.document.getElementById("profilelname");
	            	 var emailSpan = $wnd.document.getElementById("profilemail");
	            	 
	            	 profilepicimage.src = profile.getImageUrl();
	            	 firsnameSpan.innerText = profile.getGivenName();
	            	 lastNameSpan.innerText = profile.getFamilyName();
	            	 emailSpan.innerText = profile.getEmail();
	            	 
	            	 console.log(currentuser);
	            	 
           });
    });
	     
	     
	     
    }-*/;

}
