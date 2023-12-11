/**
 * eTWIG event calendar and date picker.
*/

/**
 * Create a public calendar
 * @param elem The calendar element.
 */

function createPublicCalendar(elem){
	var publicCalendarElem = document.getElementById(elem);
	var publicCalendarProperties = {
		view: 'timeGridWeek',
    	headerToolbar: {
			start: 'prev,next today',
			center: 'title',
			end: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
		},
        
		scrollTime: '09:00:00',
    	events: getEventListByRange("2023-12-04", "month"),
    	eventClick: function (info) {
			$("#etwig-modal-title").text("New word");
			$('#etwig-modal').modal('toggle');
			console.log(info)
		},
    	dayMaxEvents: true,
    	nowIndicator: true,
    	selectable: false,
    	eventStartEditable: false
	};
  
	new EventCalendar(publicCalendarElem, publicCalendarProperties);
}

/**
 * Format JavaScript date object to yyyy-mm-dd hh:MM format
 * @param date A JavaScript date object
 * @returns The date String with corect format.
 */

function formatDate(date) {
    const pad = (num) => (num < 10 ? '0' + num : num);
    let year = date.getFullYear();
    let month = pad(date.getMonth() + 1);
    let day = pad(date.getDate());
    let hours = pad(date.getHours());
    let minutes = pad(date.getMinutes());
    //let seconds = pad(date.getSeconds());
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

/**
 * Get the event list based on a specific range
 * @param dateStr The String contains current month/week/day.
 * @param rangeStr The String to set the search range.
 * @returns A list of events objects
 */

function getEventListByRange(dateStr, rangeStr){
	var eventList = []; 
	var url = '/public/_getEventList?dateStr=' + dateStr + '&rangeStr='+ rangeStr;
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
					var eventEndDateTime = new Date(eventStartDateTime.getTime() + value.eventDuration*60000);
  					
  					eventList.push({
						  id: id,
						  start: formatDate(eventStartDateTime), 
						  end: formatDate(eventEndDateTime), 
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
 */
function createDatePicker(htmlElem, pickerElem){
	var datepicker = new tui.DatePicker(htmlElem, {
		date: new Date(),
		input: {
			element: pickerElem,
			format: 'yyyy-MM-dd',
			usageStatistics: false
		}
	});
}

function getEventById(id){
	var url = '/public/_getEventById?eventId=' + id + '&rangeStr='+ rangeStr;
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
					var eventEndDateTime = new Date(eventStartDateTime.getTime() + value.eventDuration*60000);
  					
  					eventList.push({
						  id: id,
						  start: formatDate(eventStartDateTime), 
						  end: formatDate(eventEndDateTime), 
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
}