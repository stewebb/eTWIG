/**
 * eTWIG event calendar and date picker.
*/

const pad = (num) => (num < 10 ? '0' + num : num);
var calendar = undefined;
var currentDate = Date.today();

/**
 * Create a public calendar
 * @param elem The calendar element.
 */

function createPublicCalendar(elem, currentMonth){
	var publicCalendarElem = document.getElementById(elem);
	var publicCalendarProperties = {
		view: 'dayGridMonth',
	    headerToolbar: {
			start: '',
			center: 'title',
			end: ''
		},
		scrollTime: '09:00:00',
    	events: getEventListByRange(currentMonth, "month"),
    	eventClick: function (info) {
			//getEventById(info.event.id);
			$(location).prop('href', '/events/edit?eventId=' + info.event.id);
		},
    	dayMaxEvents: true,
    	nowIndicator: true,
    	selectable: false,
    	eventStartEditable: false,
    	height: '720px',
    	date: currentMonth
	};
  
	calendar = new EventCalendar(publicCalendarElem, publicCalendarProperties);
	//console.log(currentMonth);
}

/**
 * Get the event list based on a specific range
 * @param dateStr The String contains current month/week/day.
 * @param rangeStr The String to set the search range.
 * @returns A list of events objects
 */

function getEventListByRange(date, range){
	var eventList = []; 
	var url = '/api/getEventList?date=' + date + '&range='+ range;
	$.ajax({ 
		type: 'GET', 
    	url: url, 
    	async: false,
    	data: { 
			get_param: 'value' 
		}, 
    	dataType: 'json',
		success: function(json) {
			if(json.error > 0){
				$(document).Toasts('create', {
  					title: "Failed to get resource.",
  					body: json.msg,
  					autohide: true,
  					delay: 5000,
  					icon: 'fa fa-circle-xmark',
  					class: 'toast bg-danger'
				});
    			//showAlert("Failed to get resource because " + json.msg, "danger");
			}else{
				jQuery.each(json.events, function(id, value) {
					var eventStartDateTime = new Date(value.eventStartTime);
					var eventEndDateTime = new Date(eventStartDateTime.getTime() + value.eventDuration * 60000);
  					
  					eventList.push({
						  id: id,
						  start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
						  end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
						  title: value.eventName + " @ " + value.eventLocation, 
						  color: "#" + value.portfolioColor}
					); 
  					
				})
			}
        },
        
        // Toast error info when it happens
    	error: function(jqXHR, exception) {   		
			$(document).Toasts('create', {
  				title: "Failed to get resource due to a HTTP " + jqXHR.status + " error.",
  				body: exception,
  				autohide: true,
  				delay: 10000,
  				icon: 'fa fa-circle-xmark',
  				class: 'toast bg-danger'
			});
		}
	});
	return eventList;
}

function changeCalendar(){
	
	// Convert to yyyy-mm-dd format.
    var yearMonthStr = currentDate.toString('yyyy-MM-dd');
    	
    // Change calendar value.
    calendar.setOption('date', yearMonthStr);
    calendar.setOption('events', getEventListByRange(yearMonthStr, "month"));
}

/**
 * Create a ToastUI date picker
 * @param {*} htmlElem The element of datepicker wrapper.
 * @param {*} pickerElem The element of date input.
 * @param {*} buttonElem The element of button that get the date.
 */

function createDatePicker(htmlElem, pickerElem, buttonElem){
	var datepicker = new tui.DatePicker(htmlElem, {
		date: Date.today(),
		type: 'month',
		input: {
			element: pickerElem,
			format: 'MMM yyyy',
			usageStatistics: false
		}
	});
	
	// Set date
	$(buttonElem).click(function(){
		
		// Get selected date from ToastUI datepicker and store it.
  		currentDate = datepicker.getDate();
  		changeCalendar(currentDate);
	}); 
}


function dateOptions(lastMthBtn, resetBtn, nextMthBtn){
	
	$(lastMthBtn).click(function(){
		currentDate = currentDate.last().month();
		changeCalendar();
	});
	
	$(resetBtn).click(function(){
		currentDate = Date.today();
		changeCalendar();
	});
	
	$(nextMthBtn).click(function(){
		currentDate = currentDate.next().month();
		changeCalendar();
	});
}


