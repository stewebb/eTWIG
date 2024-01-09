/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Display and manipulate the Event Calendar / Planner. 
 	*/

// Leading zeros for the (positive) integers that below to 10. 
const pad = (num) => (num < 10 ? '0' + num : num);

// The calendar object
var calendar = undefined;

// The current date which can be changes by buttons and date picker in "Options".
var currentDate = Date.today();

/**
 * Create a calendar on webpage
 * @param elem The calendar element.
 * @param currentMonth The current month that displayed on the calendar.
 * @returns No returns but the calendar object will be changed.
 */

function createCalendar(elem, currentMonth){
	var calendarElem = document.getElementById(elem);
	
	// Only has "Month View" currently
	// TODO: Support Week view
	var calendarProperties = {
		view: 'dayGridMonth',
	    headerToolbar: {
			start: '',
			center: 'title',
			end: ''
		},
		//scrollTime: '09:00:00',
    	events: getEventListByRange(currentMonth, "month"),
    	eventClick: function (info) {
			editEventBtn(info.event.id);
		},
    	dayMaxEvents: true,
    	nowIndicator: true,
    	selectable: false,
    	eventStartEditable: false,
    	height: '1080px',
    	date: currentMonth
	};
  
  	// Register the created calendar.
	calendar = new EventCalendar(calendarElem, calendarProperties);
}

/**
 * Get the event list based on a specific range
 * @param dateStr The String contains current month/week/day.
 * @param rangeStr The String to set the search range.
 * @returns A list of events objects
 */

function getEventListByRange(date, range){
	var eventList = []; 
	var url = '/api/private/getEventList';
	$.ajax({ 
		type: 'GET', 
    	url: url, 
    	async: false,
    	data: { 
			date: date,
			range: range
		}, 
    	dataType: 'json',
		success: function(json) {
			
			// HTTP resopnse normally, but has other kinds of error (e.g, invalid input)
			if(json.error > 0){
    			dangerToast("Failed to get events.", json.msg);
			}
			
			else{
				jQuery.each(json.events, function(id, value) {
					var eventStartDateTime = new Date(value.details.startDateTime);
					var eventEndDateTime = new Date(value.details.endDateTime);
					
  					// Transfer the dates and other information to the frontend.
  					eventList.push({
						  id: id,
						  start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
						  end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
						  title: value.details.name, 
						  color: "#" + value.portfolio.color}
					); 
  					
				})
			}
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get calendar due to a HTTP " + err.status + " error.", err.responseJSON.exception);
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

/**
 * Actions for click "Add Event" button.
 */

function addEventBtn(){
	
	$('#etwigModalTitle').text('Add Event');
	$('#etwigModalBody').html(`
		<div class="embed-responsive embed-responsive-1by1">
			<iframe class="embed-responsive-item" src="/events/add?embedded=true" allowfullscreen></iframe>
        </div>`
    );
	
	// This modal cannot be closed when clicking outside area.  
	$('#etwigModal').modal({
    	backdrop: 'static',
    	keyboard: false,
	})
	$('#etwigModal').modal('show');
}

function editEventBtn(eventId){
	
	$('#etwigModalTitle').text('Edit Event');
	$('#etwigModalBody').html(`
		<ul class="nav nav-tabs mb-3" id="editDelete" role="tablist">
  			<li class="nav-item" role="presentation">
    			<button class="nav-link active" id="edit-tab" data-toggle="tab" data-target="#edit" type="button" role="tab" aria-controls="edit" aria-selected="true">
    				<i class="fa-solid fa-pencil"></i>&nbsp;Edit
    			</button>
  			</li>
  			<li class="nav-item" role="presentation">
    			<button class="nav-link" id="delete-tab" data-toggle="tab" data-target="#delete" type="button" role="tab" aria-controls="delete" aria-selected="false">
    				<i class="fa-solid fa-eraser"></i>&nbsp;Delete
    			</button>
  			</li>
		</ul>
		
		<div class="tab-content" id="editTabContent">
  			<div class="tab-pane fade show active" id="edit" role="tabpanel" aria-labelledby="edit-tab">
				<div class="embed-responsive embed-responsive-1by1">
					<iframe class="embed-responsive-item" src="/events/edit?eventId=${eventId}&embedded=true" allowfullscreen></iframe>
        		</div>
			</div>
  			<div class="tab-pane fade" id="delete" role="tabpanel" aria-labelledby="delete-tab">
  				<div class="embed-responsive embed-responsive-1by1">
					<iframe class="embed-responsive-item" src="/events/delete?eventId=${eventId}&embedded=true" allowfullscreen></iframe>
        		</div>
			</div>
		</div>
 	`);
	
	// This modal cannot be closed when clicking outside area.  
	$('#etwigModal').modal({
    	backdrop: 'static',
    	keyboard: false,
	})
	$('#etwigModal').modal('show');
}

/**
 * Helper function, change the calendar value.
 */

function changeCalendar(){
	
	// Convert to yyyy-mm-dd format.
    var yearMonthStr = currentDate.toString('yyyy-MM-dd');
    	
    // Change calendar value.
    calendar.setOption('date', yearMonthStr);
    calendar.setOption('events', getEventListByRange(yearMonthStr, "month"));
}
