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

var portfolioId = null;

// Recurrence mode: 1 -> Recurring, 0 -> Both, -1 -> Single time
var recurrenceMode = 0;

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
    	events: eventListForCalendar(currentMonth),

		// Click the event, go to the edit page.
    	eventClick: function (info) {
			$(location).attr('href', '/events/edit.do?eventId=' + info.event.id);
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

/**
 * Retrieves the combined list of single-time and recurring events for the calendar view.
 *
 * @param {Date|string} date - The date object or string representing the current date.
 * @returns {Array<Object>} A combined list of event objects for the calendar.
 */

function eventListForCalendar(date){

		var dateObj = new Date(date);
		var startObj = new Date();
		var endObj = new Date();
	
		if(calendarView == 0) {
			startObj = dateObj.clone().last().monday();
			endObj = startObj.clone().addDays(6);
		}
	
		else {
			startObj = dateObj.clone().moveToFirstDayOfMonth();
			endObj = dateObj.clone().moveToLastDayOfMonth();
		}

	var singleTimeEventList = getSingleTimeEventListByRange(date, startObj, endObj); 
	var recurringEventList = getRecurringTimeEventListByRange(date, startObj, endObj);

	//console.log(recurrenceMode);

	// Recurring events only
	if (recurrenceMode > 0) {
		return recurringEventList;
	}

	// Single time events only
	else if (recurrenceMode < 0) {
		return singleTimeEventList;
	}

	// Both kinds of events
	else {
		return singleTimeEventList.concat(recurringEventList);
	}
}

/**
 * Retrieves the event list based on a specific date range.
 *
 * @param {Date} date - The date object representing the current date.
 * @param {Date} startObj - The date object representing the start of the range.
 * @param {Date} endObj - The date object representing the end of the range.
 * @returns {Array<Object>} A list of event objects.
 */

function getSingleTimeEventListByRange(date, startObj, endObj){

	var eventList = []; 
	$.ajax({ 
		type: 'GET', 
    	url: '/api/event/list', 
    	async: false,
    	data: { 
			startDate: startObj.toString("yyyy-MM-dd"),
			endDate: endObj.toString("yyyy-MM-dd"),
			recurring: false,
			portfolioId: portfolioId,
			sortColumn: 'id',
			sortDirection: 'asc',
			start: 0,
			length: 2147483647,
			draw: 1
		}, 

		success: function(json) {
			jQuery.each(json.data, function(id, value) {

				// Get start and end time
				var eventStartDateTime = new Date(value.startTime);
				var eventEndDateTime = new Date(value.startTime).addMinutes(value.duration);
					
				// Save data
				eventList.push({
					id: value.id,
					start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
					end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
					title: {html: `<span class="font-weight-bold">${value.name}</span>`},
					color: "#" + value.portfolioColor,
					allDay: value.allDayEvent
				}); 	
			});
		},

		error: function(err) {   		
			dangerPopup("Failed to get single time events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});

	return eventList;
}

/**
 * Retrieves the recurring event list based on a specific date range.
 *
 * @param {Date} date - The date object representing the current date.
 * @param {Date} startObj - The date object representing the start of the range.
 * @param {Date} endObj - The date object representing the end of the range.
 * @returns {Array<Object>} A list of recurring event objects.
 */

function getRecurringTimeEventListByRange(date, startObj, endObj){

	var eventList = []; 
	$.ajax({ 
		type: 'GET', 
    	url: '/api/event/list', 
    	async: false,
    	data: { 
			startDate: startObj.toString("yyyy-MM-dd"),
			endDate: endObj.toString("yyyy-MM-dd"),
			recurring: true,
			portfolioId: portfolioId,
			sortColumn: 'id',
			sortDirection: 'asc',
			start: 0,
			length: 2147483647,
			draw: 1
		}, 

		success: function(json) {
			jQuery.each(json.data, function(id, value) {

				var rRule = new ETwig.RRuleFromStr(value.rrule);
				var rule = rRule.getRuleObj();
								
				// Failed to parse rRule, skip it.
				if(rule == undefined || rule == null){
					dangerPopup("Failed to parse Recurrence Rule.", value.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
					return;
				}
				
				// Get and iterate all occurrences in this month.
    			var occurrence = rRule.getOccurrenceBetween(startObj, endObj);
    			for(var i=0; i< occurrence.length; i++){
					
					// Get start and end time for each event.
					var occurrenceDate = new Date(occurrence[i].getTime() + occurrence[i].getTimezoneOffset() * 60000);
					
					if(value.excluded != null){
						if(value.excluded.includes(occurrenceDate.toString('yyyy-MM-dd'))){
							continue;
						}
					}

					var eventTime = (new Date(value.startTime)).toString("HH:mm:ss");					
					var eventStartDateTime = combineDateAndTime(occurrenceDate, eventTime);
					var eventEndDateTime = combineDateAndTime(occurrenceDate, eventTime).addMinutes(value.duration);
					
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
			});
		},

		error: function(err) {   		
			dangerPopup("Failed to get single time events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
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

	datepicker.on('change', function(){
		currentDate = datepicker.getDate();
  		changeCalendar(currentDate);
	});
}

/**
 * Helper function to change the calendar's current date and events.
 */

function changeCalendar(){
	
	// Convert to yyyy-mm-dd format.
    var yearMonthStr = currentDate.toString('yyyy-MM-dd');
    
    // Change calendar value.
    calendar.setOption('date', yearMonthStr);
    calendar.setOption('events', eventListForCalendar(yearMonthStr, "month"));

	// Change the calendar view
	calendar.setOption('view', (calendarView == 0) ? 'timeGridWeek' : 'dayGridMonth');
}

/**
 * Changes the current date based on the specified mode and updates the calendar.
 *
 * @param {number} mode - The mode for changing the date: 
 *                        - Negative value to move to the previous week/month,
 *                        - 0 to reset to the current date,
 *                        - Positive value to move to the next week/month.
 */

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

function updatePortfolio() {
	portfolioId = $('#eventPortfolio').val();
	if(portfolioId <= 0){
		portfolioId = null;
	}
	changeCalendar();
}