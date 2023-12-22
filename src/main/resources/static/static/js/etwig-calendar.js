/**
 * eTWIG event calendar and date picker.
*/

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
  
	new EventCalendar(publicCalendarElem, publicCalendarProperties);
	//console.log(currentMonth);
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
		type: 'month',
		input: {
			element: pickerElem,
			format: 'MMM yyyy',
			usageStatistics: false
		}
	});
}

function getEventById(id){
	var url = '/api/getEventById?eventId=' + id + '&showAllDetails=true';
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
    			return;
			}
			
			if(!json.exists){
				showAlert("Oops! Event with id=" + id + " doesn't exist.", "warning");
				return;
			}
			
			var bodyHTML = "" +
				"<div class='container'>" +
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Name</div>" +
						"<div class='col-sm-8'>" + json.detail.eventName + "</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Location</div>" +
						"<div class='col-sm-8'>" + json.detail.eventLocation + "</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Start Time</div>" +
						"<div class='col-sm-8'>" + json.detail.eventStartTime + "</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Duration</div>" +
						"<div class='col-sm-8'>" + json.detail.eventDuration + " minutes</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Hold by</div>" +
						"<div class='col-sm-8'>" + json.detail.portfolioName + "</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Organizer</div>" +
						"<div class='col-sm-8'>" + json.detail.organizerName + "</div>" +
					"</div>" + 
					"<div class='row'>" + 
						"<div class='col-sm-4 bold'>Description</div>" +
						"<div class='col-sm-8'>" + json.detail.eventDescription + "</div>" +
					"</div>" + 
					
					
				"</div>"
			$("#etwig-modal-title").html("<i class='fa-solid fa-list-ul'></i> Event deatils");
			$("#etwig-modal-body").html(bodyHTML);
			$('#etwig-modal').modal('toggle');
			//console.log(json.detail);
				//jQuery.each(json.events, function(id, value) {
					//console.log(ev)
				//})
			
        },
    	error: function(jqXHR, exception) {   		
    		showAlert("Failed to get resource due to a HTTP " + jqXHR.status + " error.", "danger");
		}
	});
}