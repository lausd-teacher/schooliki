function login(googleuser){

        var profile = googleuser.getBasicProfile();
        
        var googleId = profile.getId();
        var lastName = profile.getFamilyName();
        var firstName = profile.getGivenName();
        var imageUrl = profile.getImageUrl();
        var email = profile.getEmail();
        var token = googleuser.getAuthResponse().id_token;
       
        
        var formGoogleId = document.getElementById("googleId");
        var formLastName = document.getElementById("lastName");
        var formFirstName = document.getElementById("firstName");
        var formImageUrl = document.getElementById("profilePicUrl");
        var formEmail = document.getElementById("email");
        var formToken = document.getElementById("token");
        var admin = document.getElementById("isadminForm");
        
        
        formGoogleId.value = googleId;
        formLastName.value = lastName;
        formFirstName.value = firstName;
        formImageUrl.value = imageUrl;
        formEmail.value = email;
        formToken.value = token;
        
          if(document.getElementById("isadmin").checked)
        	  admin.value = "true";
          else
        	  admin.value = "false";


        document.getElementById("loginForm").submit();        
}



function getUserprofileAsJson(googleuser){

    var profile = googleuser.getBasicProfile();
     
    var newUser = {"googleId":profile.getId(), "name": profile.getName(), "imageUrl":profile.getImageUrl(), "token":googleuser.getAuthResponse().id_token};

    return JSON.stringify(newUser);

}

  function onSignIn(googleUser) {
	       if(document.getElementById("secondAttempt")){
	    	   if(document.getElementById("secondAttempt").innerText === "1"){
	    		   document.getElementById("secondAttempt").innerText = "0";
	    	         signOut();
	    	   }else{
	    		   login(googleUser);  
 
	    	   }
	            
	       }else{
	    	   login(googleUser);
	       }
      };

    function onFailure(error) {
      console.log(error);
    }

    function renderButton() {
      gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSignIn,
        'onfailure': onFailure
      });
    }

    

    function signOut() {
    var auth2 = gapi.auth2;
    
    if(!auth2){
    	//usually the case, because it's done on another page than the main login page
            gapi.load('auth2', function(){
                /**
                 * Retrieve the singleton for the GoogleAuth library and set up the
                 * client.
                 */
                auth2 = gapi.auth2.init({
                    client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
                }).then(function(){
                	var instance = gapi.auth2.getAuthInstance();
	            	 var token = instance.currentUser.get().getAuthResponse().id_token;
	                 instance.signOut().then(function () {
//	                	 jQuery.ajax({
//	                         url : "/logout",
//	                         type: "POST",
//	                         data : {token: token},
//	                         success: function(data, textStatus, jqXHR)
//	                         {
//	                        	 console.log('logged out');
//	                        	 window.location = "/login";
//	                         },
//	                         error: function (jqXHR, textStatus, errorThrown)
//	                         {
//	                            alert("Error logging out. Please try later.");
//	                         }
//	                     });
	                	 
	                	 console.log('logged out');
	                	 
	       		      
	       		    });
            });
            	
            });
    }else{
    	var instance = auth2.getAuthInstance();
	    
    	var instance = gapi.auth2.getAuthInstance();
   	 var token = instance.currentUser.get().getAuthResponse().id_token;
        instance.signOut().then(function () {
//       	 jQuery.ajax({
//                url : "/logout",
//                type: "POST",
//                data : {token: token},
//                success: function(data, textStatus, jqXHR)
//                {
//               	 console.log('logged out');
//               	 window.location = "/login";
//                },
//                error: function (jqXHR, textStatus, errorThrown)
//                {
//                   alert("Error logging out. Please try later.");
//                }
//            });
//       	 
//		      
        	console.log('logged out');
		    });
        	
    	
    }
    
   	
  }