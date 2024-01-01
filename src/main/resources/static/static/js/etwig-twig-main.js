class myChoices{
	
	constructor(){
		
		/**
		 * portfolioId, -1 stands for all portfolios (default).
		 */
		
		this.portfolio = -1;
		this.portfolioDefault = -1;
		
		/**
		 * Week of the given date, by default is this week.
		 */
		
		this.date = Date.today();
		this.dateDefault = Date.today();
		
		/**
		 * TWIG size, in a choice of:
		 * -1p: Your browser's resolution (default)
		 * 720p: 1280*720
		 * 1080p: 1920*1080
		 * 2k: 2560*1440
		 * 4k: 3840*2160
		 */
		
		this.resolution = "-1p";
		this.resolutionDefault = "-1p";
	}
	
	setPortfolio(portfolio){
		this.portfolio = Number.isInteger(portfolio) ? portfolio : -1;
	}
	
	setDate(date){
		this.date = date;
	}
	
	setResolution(resolution){
		switch (resolution){
			case "720p":
			case "1080p":
			case "2k":
			case "4k":
				this.resolution = resolution;
			default:
				this.resolution = "-1p";
		}
	}
	
	reset(){
		this.portfolio = this.portfolioDefault;
		this.date = this.dateDefault;
		this.resolution = this.portfolioDefault;
	}
}

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
	
	
	var twigPortfolio = $('#twigPortfolio').find(":selected").val();
	alert(twigPortfolio);
	
	var twigWeek = $('#twigWeek').val();
	alert(twigWeek);
	
	var twigResolution = $('#twigResolution').find(":selected").val();
	alert(twigResolution);
}