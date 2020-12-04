$(document).ready(function() {
    
});

function validateNumberAndDash(event) {
    var key = window.event ? event.keyCode : event.which;

  if (event.keyCode >= 48 && event.keyCode <= 57 || event.keyCode >= 189) {
    return true;
  } else {
    return false;
  }
};
