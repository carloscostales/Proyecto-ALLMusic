$(document).ready(function() {
    
});

function validate(evt) {
   	var theEvent = evt || window.event;
   	var key = theEvent.keyCode || theEvent.which;
   	key = String.fromCharCode(key);
  	var regex = /[]|\./;
   	if(!regex.test(key)) {
    	theEvent.returnValue = false;
    	if(theEvent.preventDefault) theEvent.preventDefault();
	}
}