/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Display and manipulate the Event Calendar / Planner. 
 	*/

// The calendar object
var calendar = undefined;

// The current date which can be changes by buttons and date picker in "Options".
var currentDate = Date.today();

// Calendar view: 1 -> Monthly, 0 -> Weekly, -1 -> Daily (not used in here but will be used in the TWIG)
var calendarView = 0;

/**
 * Create a calendar on webpage
 * @param elem The calendar element.
 * @param currentMonth The current month that displayed on the calendar.
 */

function createCalendar(elem, currentMonth){
	var calendarElem = document.getElementById(elem);
	
	var calendarProperties = {
		view: 'timeGridWeek',
		firstDay: 1,
	    headerToolbar: {
			start: '',
			center: 'title',
			end: ''
		},
    	events: getSingleTimeEventList(currentMonth),

		// Click the event, go to the edit page.
    	eventClick: function (info) {
			$(location).attr('href', '/events/edit?eventId=' + info.event.id);
		},
    	dayMaxEvents: true,
    	nowIndicator: true,
    	selectable: false,
    	eventStartEditable: false,
    	height: '1080px',
    	date: currentMonth,
		scrollTime: '12:00:00'
	};
  
  	// Register the created calendar.
	calendar = new EventCalendar(calendarElem, calendarProperties);
}

function getSingleTimeEventList(date){
	var singleTimeEventList = getSingleTimeEventListByRange(date); 
	var recurringEventList = getRecurringTimeEventListByRange(date);
	return singleTimeEventList.concat(recurringEventList);
}

/**
 * Get the event list based on a specific range
 * @param dateStr The String contains current month/week/day.
 * @param rangeStr The String to set the search range.
 * @returns A list of events objects
 */

function getSingleTimeEventListByRange(date){
	var eventList = []; 
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getSingleTimeEventList', 
    	async: false,
    	data: { 
			date: date,
			calendarView: calendarView
		}, 
		success: function(json) {
			
			// Iterate all dates.
			jQuery.each(json, function(id, value) {				
					
				// Get start and end time
				var eventStartDateTime = new Date(value.startTime);
				var eventEndDateTime = new Date(value.startTime).addMinutes(value.duration);
    				
    			// Save data
				eventList.push({
					  id: id,
					  start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
					  end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
					  title: {html: `<span class="font-weight-bold">${value.name}</span>`},
					  color: "#" + value.portfolioColor,
					  allDay: value.allDayEvent
				}); 	
			})
        },
        
        // Popup error info when it happens
    	error: function(err) {   		
			dangerPopup("Failed to get single time events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	return eventList;
}

function getRecurringTimeEventListByRange(date){
	
	// Calculate the first and last day of the month for a given date.
	var dateObj = Date.parse(date);
	var firstDay;
	var lastDay;

	// Monthly view
	if(calendarView > 0){
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth();
		firstDay = new Date(year, month, 1);
		lastDay = new Date(year, month + 1, 1);
	}

	// Weekly view
	else{
		firstDay = Date.parse(date).monday().addDays(-7);
		lastDay = Date.parse(date).monday();
	}
        				
	var eventList = []; 
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getAllRecurringEventList', 
    	async: false,
    	data: { 
			date: date,
		}, 
    	dataType: 'json',
		success: function(json) {
			
			// Iterate all dates.
			jQuery.each(json, function(id, value) {			
								
				var rRule = new ETwig.RRuleFromStr(value.rrule);
				var rule = rRule.getRuleObj();
								
				// Failed to parse rRule, skip it.
				if(rule == undefined || rule == null){
					dangerPopup("Failed to parse Recurrence Rule.", value.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
					return;
				}
				
				// Get and iterate all occurrences in this month.
    			var occurrence = rRule.getOccurrenceBetween(firstDay, lastDay);
    			for(var i=0; i< occurrence.length; i++){
					
					// Get start and end time for each event.
					var occurrenceDate = new Date(occurrence[i].getTime() + occurrence[i].getTimezoneOffset() * 60000);
					
					if(value.excluded != null){
						if(value.excluded.includes(occurrenceDate.toString('yyyy-MM-dd'))){
							continue;
						}
					}
					
					var eventStartDateTime = combineDateAndTime(occurrenceDate, value.eventTime);
					var eventEndDateTime = combineDateAndTime(occurrenceDate, value.eventTime).addMinutes(value.duration);
					
					// Save data
					eventList.push({
						id: value.id,
						start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
						end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
						title: {html: `<span class="font-italic bold-text">${value.name}</span>`},
						color: "#" + value.portfolioColor,
						allDay: value.allDayEvent
					}); 	
				}
			})
        },
        
        // Popup error info when it happens
    	error: function(err) {   		
			dangerPopup("Failed to get recurrent events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	return eventList;
}

/**
 * Create a PopupUI date picker
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
		
		// Get selected date from PopupUI datepicker and store it.
  		currentDate = datepicker.getDate();
  		changeCalendar(currentDate);
	}); 
}

/**
 * Helper function, change the calendar value.
 */

function changeCalendar(){
	
	// Convert to yyyy-mm-dd format.
    var yearMonthStr = currentDate.toString('yyyy-MM-dd');
    
    // Change calendar value.
    calendar.setOption('date', yearMonthStr);
    calendar.setOption('events', getSingleTimeEventList(yearMonthStr, "month"));

	// Change the calendar view
	calendar.setOption('view', (calendarView == 0) ? 'timeGridWeek' : 'dayGridMonth');
}

function changeCurrentDate(mode){

	// Last
	if(mode < 0){
		currentDate = (calendarView == 0) ? currentDate.last().week() : currentDate.last().month();
	}

	// Reset
	else if(mode == 0){
		currentDate = Date.today();
	}

	// Next
	else{
		currentDate = (calendarView == 0) ? currentDate.next().week() : currentDate.next().month();
	}
	changeCalendar();
}