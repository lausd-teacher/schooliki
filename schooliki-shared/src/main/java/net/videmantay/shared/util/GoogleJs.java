package net.videmantay.shared.util;

public class GoogleJs {
	
	
	
	public native static void logout()/*-{
		 var auth2 = $wnd.gapi.auth2;    
    if(!auth2){
    	//usually the case, because it's done on another page than the main login page
            $wnd.gapi.load('auth2', function(){
                auth2 = $wnd.gapi.auth2.init({
                    client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
                }).then(function(){
		            	 var instance = $wnd.gapi.auth2.getAuthInstance();
		            	 var token = instance.currentUser.get().getAuthResponse().id_token;
		                 instance.signOut().then(function () {
		                	 document.getElementById("logout").submit();
		       		    });
                   });
            	
            });
    }else{
    	var instance = auth2.getAuthInstance();
    	var token = instance.currentUser.get().getAuthResponse().id_token;
	    
	   	instance.signOut().then(function () {
	   		       $wnd.document.getElementById("logout").submit();
		    });
    	
    }
     }-*/;
	
	public native static void InitializeSignedInListener()/*-{
	  var auth2 = $wnd.gapi.auth2;    
    if(!auth2){    
	  
			$wnd.gapi.load('auth2', function(){
		        var auth2 = $wnd.gapi.auth2.init({
		            client_id: '535909648993-7nfaqivi206q2phmicubas1hjri084eb.apps.googleusercontent.com'
		        }).then(function(){
		       	         var instance = $wnd.gapi.auth2.getAuthInstance();
			            	 instance.isSignedIn.listen(function(isSignedIn){
			            	           if(!isSignedIn){
			            	            $wnd.document.getElementById("logout").submit();
			            	           
			            	           }
			            	    }
			            	 );
			            	 
			            	 
		           });
		    });
    
    }else{
       var instance = auth2.getAuthInstance();
	   	              instance.isSignedIn.listen(function(isSignedIn){
			            	           if(!isSignedIn){
			            	            $wnd.document.getElementById("logout").submit();
			            	           
			            	           }
			            	    }
			            	 );
            }
    }-*/;
	
	public native static String getAccessToken()/*-{
		
    var instance = $wnd.gapi.auth2.getAuthInstance();
    var currentUser = instance.currentUser.get();
    
       if(currentUser.Zi.access_token) 
             return currentUser.Zi.access_token;
       else
             return null;
		       	         
		          
  }-*/;
	
	
	 

}
