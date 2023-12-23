/**
 * eTWIG event calendar and date picker.
*/

const pad = (num) => (num < 10 ? '0' + num : num);
var calendar = undefined;

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
    	date: currentMonth + '-01'
	};
  
	calendar = new EventCalendar(publicCalendarElem, publicCalendarProperties);
	//console.log(currentMonth);
}

/**
 * Format JavaScript date object to a specific format
 * @param date A JavaScript date object
 * @param mode A format mode
 * @returns The date String with corect format.
 * 
 * Mode Options:
 * d: Format date to yyyy-mm-dd
 * i: Format date to yyyy-mm-dd hh:MM
 * a: Format date to yyyy-mm-dd hh:MM:ss
 */

function formatDate(date, mode) {
    
    let year = date.getFullYear();
    let month = pad(date.getMonth() + 1);
    let day = pad(date.getDate());
    let hours = pad(date.getHours());
    let minutes = pad(date.getMinutes());
    let seconds = pad(date.getSeconds());
    
    var str = '';
    switch (mode){
		case 'd':
			str = `${year}-${month}-${day}`;
			break;
		case 'i':
			str = `${year}-${month}-${day} ${hours}:${minutes}`;
			break;
		default:
			str = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
			break;
	}
    return str;
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
    			showAlert("Failed to get resource because " + json.msg, "danger");
			}else{
				jQuery.each(json.events, function(id, value) {
					var eventStartDateTime = new Date(value.eventStartTime);
					var eventEndDateTime = new Date(eventStartDateTime.getTime() + value.eventDuration * 60000);
  					
  					eventList.push({
						  id: id,
						  start: formatDate(eventStartDateTime, 'i'), 
						  end: formatDate(eventEndDateTime, 'i'), 
						  title: value.eventName + " @ " + value.eventLocation, 
						  color: "#" + value.portfolioColor}
					); 
  					
				})
			}
        },
    	error: function(jqXHR, exception) {   		
    		showAlert("Failed to get resource due to a HTTP " + jqXHR.status + " error.", "danger");
		}
	});
	return eventList;
}

/**
 * Create a ToastUI date picker
 * @param {*} htmlElem The element of datepicker wrapper.
 * @param {*} pickerElem The element of date input.
 * @param {*} buttonElem The element of button that get the date.
 */

function createDatePicker(htmlElem, pickerElem, buttonElem){
	var datepicker = new tui.DatePicker(htmlElem, {
		date: new Date(),
		type: 'month',
		input: {
			element: pickerElem,
			format: 'MMM yyyy',
			usageStatistics: false
		}
	});
	
	// Set date
	$(buttonElem).click(function(){
  		var selectedDate = datepicker.getDate();
    	var yearMonthStr = formatDate(selectedDate, 'd');
    	//alert(yearMonthStr)
    	//$(location).prop('href', '/events/calendar?month=' + yearMonthStr);
    	//createPublicCalendar("etwig-public-calendar", yearMonthStr);
    	calendar.setOption('date', datepicker.getDate());
    	calendar.setOption('events', getEventListByRange(yearMonthStr, "month"));
    	//events: getEventListByRange(currentMonth, "month"),
	}); 
}


