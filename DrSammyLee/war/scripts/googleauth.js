document.addEventListener("DOMContentLoaded", function(event) { 
	  /////////////this string is in three different places
        //net.videmantay.security.GoogleTokenVerifier
        //net.videmantay.shared.utils.GoogleJS -- in two different places 
        //war/script.googleaut.js -- in two different places /////////////////////////////
	var clientIdToken = '342098051221-q0t02hqffb9pq193k1jrkeojj13667fg.apps.googleusercontent.com';
	  var auth2 = gapi.auth2;    
	    if(!auth2){    
		  
				gapi.load('auth2', function(){
			        var auth2 = gapi.auth2.init({
			            client_id: clientIdToken
			        }).then(function(){
			       	         var instance = gapi.auth2.getAuthInstance();
				            	 instance.isSignedIn.listen(function(isSignedIn){
				            	           if(!isSignedIn){
				            	            document.getElementById("logout").submit();
				            	           
				            	           }
				            	    }
				            	 );
				            	 
				            	 
			           });
			    });
	    
	    }else{
	       var instance = auth2.getAuthInstance();
		    
		   	              instance.isSignedIn.listen(function(isSignedIn){
				            	           if(!isSignedIn){
				            	            document.getElementById("logout").submit();
				            	           
				            	           }
				            	    }
				            	 );
	            }
	    
		
 });

window.addEventListener("beforeunload", function(event){
	 var loader = document.getElementById("loader");
	 loader.style.visibility="visible";
	
});

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
        'scope': 'profile email https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/drive',
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
                auth2 = gapi.auth2.init({
                    client_id: clientIdToken
                }).then(function(){
                	var instance = gapi.auth2.getAuthInstance();

	                 instance.signOut().then(function () {
	                	 document.getElementById("logout").submit();
	                	 console.log('logged out');
	       		    });
            });
            	
            });
    }else{
    	var instance = auth2.getAuthInstance();

        instance.signOut().then(function () {
        	document.getElementById("logout").submit();
        	console.log('logged out');
		    });
        	
    	
    }
    
    }
    
    
    
    
   