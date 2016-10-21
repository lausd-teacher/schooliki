function login(googleuser){


        var login = getUserprofileAsJson(googleuser);
        var isAdmin = document.getElementById("isadmin").checked;
        
        console.log(login);
       
              jQuery.ajax({
                      url : "/login",
                      headers: { 
                          'Accept': 'application/json',
                          'Content-Type': 'application/json' 
                      },
                      type: "POST",
                      data : login,
                      contentType: "application/json",
                      success: function(data, textStatus, jqXHR)
                      {
                          console.log("successfully logged in");
                          window.location = data;
                          
                      },
                      error: function (jqXHR, textStatus, errorThrown)
                      {
                         alert("Error! Please try later");
                      }
                  });
}



function getUserprofileAsJson(googleuser){

    var profile = googleuser.getBasicProfile();
     
    var newUser = {"googleId":profile.getId(), "name": profile.getName(), "imageUrl":profile.getImageUrl(), "token":googleuser.getAuthResponse().id_token};

    return JSON.stringify(newUser);

}

  function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());

        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);

        login(googleUser);
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
	                	 jQuery.ajax({
	                         url : "/logout",
	                         type: "POST",
	                         data : {token: token},
	                         success: function(data, textStatus, jqXHR)
	                         {
	                        	 console.log('logged out');
	                        	 window.location = "/login";
	                         },
	                         error: function (jqXHR, textStatus, errorThrown)
	                         {
	                            alert("Error logging out. Please try later.");
	                         }
	                     });
	                	 
	       		      
	       		    });
            });
            	
            });
    }else{
    	var instance = auth2.getAuthInstance();
	    
    	var instance = gapi.auth2.getAuthInstance();
   	 var token = instance.currentUser.get().getAuthResponse().id_token;
        instance.signOut().then(function () {
       	 jQuery.ajax({
                url : "/logout",
                type: "POST",
                data : {token: token},
                success: function(data, textStatus, jqXHR)
                {
               	 console.log('logged out');
               	 window.location = "/login";
                },
                error: function (jqXHR, textStatus, errorThrown)
                {
                   alert("Error logging out. Please try later.");
                }
            });
       	 
		      
		    });
    	
    }
    
   	
  }
