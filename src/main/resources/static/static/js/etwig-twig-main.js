/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: JS Script for the TWIG main.
 */

function createDatePicker(htmlElem, pickerElem){
	var datepicker = new tui.DatePicker(htmlElem, {
		date: Date.today(),
		type: 'date',
		input: {
			element: pickerElem,
			usageStatistics: false
		}
	});
	
	datepicker.on('change', () => {
    	getWeekByDate(datepicker.getDate().toString("yyyy-MM-dd"));
	});

	return datepicker;
}

/**
 * Create a QR code.
 * @param htmlElemId The HTML elememt ID of the QR code div.
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

function getWeekByDate(date){
	var url = '/api/public/getWeekByDate';
	$.ajax({ 
		type: 'GET', 
    	url: url, 
    	data: { 
			date: date,
		}, 
		success: function(json) {
			
			// HTTP resopnse normally, but has other kinds of error (e.g, invalid input)
			if(json.error > 0){
    			dangerToast("Failed to get week.", json.msg);
    			return;
			}
			
			// Week cannot be found in DB.
			if(json.week == null){
				$("#calculatedWeek").html(`<span class="text-danger">The week cannot be found in the database.</span> Try to select another date.`);
				return;
			}
			
			// Week can be found in DB.
			$("#calculatedWeek").html(`<span class="text-primary">${json.week.name} of ${json.week.semester}</span>`);
			
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get week due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
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
	$('.disabled-by-default').prop('disabled', false);
	
}