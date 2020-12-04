$(document).ready(function() {

    /* ANIMACION DE TODOS LOS MODALES */
    $('.modal').on('show.bs.modal', function (e) {
		$('.modal .modal-dialog').attr('class', 'modal-dialog animate__animated animate__bounceInDown animate__faster');
	})
	$('.modal').on('hide.bs.modal', function (e) {
	   	$('.modal .modal-dialog').attr('class', 'modal-dialog animate__animated animate__bounceOutDown animate__faster');
		$('.fade').css('transition', 'opacity 0.4s linear');
	})
});

/* FUNCIÓN QUE BLOQUEA LA ENTRADA DE LETRAS EN EL INPUT DE LA FECHA */
function validateDate(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode
	
	if (charCode != 45  && charCode > 31 && (charCode < 48 || charCode > 57))
		return false;

	return true;
}
