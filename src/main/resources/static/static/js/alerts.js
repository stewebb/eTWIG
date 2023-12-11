/**
 * 
 */

function showAlert(message, type) {
	
	// Type to Font Awesome icon.
	switch (type){
		case "success":
			alertType = "success";
			msgHead = "SUCCESS:";
			alertIcon = "circle-check";
			break;
		case "info":
			alertType = "info";
			msgHead = "NOTICE:";
			alertIcon = "circle-info";
			break;
		case "warning":
			alertType = "warning";
			msgHead = "WARNING:";
			alertIcon = "circle-exclamation";
			break;
		default:
			alertType = "danger";
			msgHead = "ERROR:";
			alertIcon = "circle-xmark";
	}
	
	var newAlert = $(
		'<div class="alert alert-' + alertType +' alert-dismissible fade show" role="alert">' +
		'<strong><i class="fa-solid fa-' + alertIcon + '"></i>&nbsp;' + msgHead + '</strong>&nbsp;' + message +
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button></div>').appendTo('#alerts');

      newAlert.slideDown(500).delay(5000).slideUp(500, function() {
        $(this).remove();
      });
    }