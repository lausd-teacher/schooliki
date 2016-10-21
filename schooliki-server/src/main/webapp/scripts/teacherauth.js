function getUserprofileAsJson(){
	
	
	var user;
	
	 gapi.load('auth2', function(){
         /**
          * Retrieve the singleton for the GoogleAuth library and set up the
          * client.
          */
         var auth2 = gapi.auth2.init({
             client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
         }).then(function(){
        	         var instance = gapi.auth2.getAuthInstance();
	            	 var profile = instance.currentUser.get().getBasicProfile();
	            	 
	            	 var currentuser = {"googleId":profile.getId(), "name": profile.getName(), "imageUrl":profile.getImageUrl()};
	            	 console.log(currentuser);
	            	 user = currentuser;
	                 
            });
     });
	
    return JSON.stringify(user);
}


function signOut() {
    var auth2 = gapi.auth2;
    
    if(!auth2){
    	//usually the case, because it's done on another page than the main login page
            gapi.load('auth2', function(){
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
