

/**
 * Create a QR code.
 * @param htmlElemId The HTML element ID of the QR code div.
 * @param text The text content of the QR code.
 * @param color The color of the QR code. 
 * It is a 2-element array. The first element is the dark-side color while the second element is the light-side color.
 * Both of them are expressed into hexadecimal form.
 */
function createQRCode(htmlElemId, text, color){
	
	var element = document.getElementById(htmlElemId);
	
	var qrcode = new QRCode(element, {
    	text: text,
    	//width: 128,
    	//height: 128,
   	 	colorDark : color[0],
    	colorLight : color[1],
    	//correctLevel : QRCode.CorrectLevel.H
	});

	return qrcode;
}



function applyChanges(){
	
	// Get the settings
	var twigPortfolio = $('#twigPortfolio').find(":selected").val();	
	var twigWeek = $('#twigWeek').val();	
	var twigResolution = $('#twigResolution').find(":selected").val();

	// Get the new TWIG url based on the settings.
	 var url = `/twig/content?portfolioId=${twigPortfolio}&week=${twigWeek}&resolution=${twigResolution}`
	 
	 // Change the HTML content.
	$('#twigFrame').attr('src', url);
	$('#twig-link').val(window.location.origin + url);
	enableShare(true);
}

/**
 * Enable / Disable share buttons.
 * @param isEnable
 */

function enableShare(isEnable){
	if(isEnable){
		$('#holdOn').hide();
		$("#applyBtn").removeClass("fa-bounce");
	}else{
		$('#holdOn').show();
		$("#applyBtn").addClass("fa-bounce");
	}
	$('.disabled-by-default').prop('disabled', !isEnable);
}