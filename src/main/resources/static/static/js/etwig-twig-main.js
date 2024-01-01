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
    	//async: false,
    	data: { 
			date: date,
		}, 
    	//dataType: 'json',
		success: function(json) {
			
			// HTTP resopnse normally, but has other kinds of error (e.g, invalid input)
			if(json.error > 0){
    			dangerToast("Failed to get events.", json.msg);
    			return;
			}
			
			var week = json.week;
			if(week == null){
				$("#calculatedWeek").html(`The week cannot be found in database by the given date.`);
			}
			
			else{
				$("#calculatedWeek").html(`${week.name} of ${week.semester}`);
			}
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get calendar due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	//return eventList;
}