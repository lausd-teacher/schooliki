document.addEventListener("DOMContentLoaded", function(event) { 
	
	for (var it in $.cookie()) 
		 $.removeCookie(it);  
 });