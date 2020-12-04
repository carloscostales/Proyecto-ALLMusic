$(document).ready(function() {
    
});

function validateDate(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode
	
	if (charCode != 45  && charCode > 31 && (charCode < 48 || charCode > 57))
		return false;

	return true;
}