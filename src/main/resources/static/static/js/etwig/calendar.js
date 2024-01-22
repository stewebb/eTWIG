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

/**
 * Create a calendar on webpage
 * @param elem The calendar element.
 * @param currentMonth The current month that displayed on the calendar.
 * @returns No returns but the calendar object will be changed.
 */

function createCalendar(elem, currentMonth){
	var calendarElem = document.getElementById(elem);
	
	var calendarProperties = {
		view: 'dayGridMonth',
	    headerToolbar: {
			start: '',
			center: 'title',
			end: ''
		},
    	events: getEventList(currentMonth),
    	eventClick: function (info) {
			//console.log(info)
			$(location).attr('href', '/events/edit?eventId=' + info.event.id);
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

function getEventList(date){
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
    	url: '/api/private/getMonthlySingleTimeEventList', 
    	async: false,
    	data: { 
			date: date,
		}, 
    	dataType: 'json',
		success: function(json) {
			
			// Iterate all dates.
			jQuery.each(json, function(id, value) {				
					
				// Get start and end time
				var eventStartDateTime = new Date(value.startTime);
				var eventEndDateTime = new Date(value.startTime).addMinutes(value.duration);
					
				// Italic font for recurring events.
				//var textFont = value.recurring ? "italic" : "weight-normal";
    				
    			// Save data
				eventList.push({
					  id: id,
					  start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
					  end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
					  title: {html: `<span class="font-weight-normal">${value.name}</span>`},
					  color: "#" + value.portfolioColor
				}); 	
			})
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get calendar due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	return eventList;
}

function getRecurringTimeEventListByRange(date){
	
	// Calculate the first and last day of the month for a given date.
	var dateObj = Date.parse(date);
	var year = dateObj.getFullYear();
    var month = dateObj.getMonth();
    var firstDay = new Date(year, month, 1);
    var lastDay = new Date(year, month + 1, 0);
        				
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
					dangerToast("Failed to parse Recurrence Rule.", value.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
					return;
				}
				
				// Get and iterate all occurrences in this month.
    			var occurrence = rRule.getOccurrenceBetween(firstDay, lastDay);
    			for(var i=0; i< occurrence.length; i++){
					
					// Get start and end time for each event.
					var eventStartDateTime = combineDateAndTime(occurrence[i], value.eventTime);
					var eventEndDateTime = combineDateAndTime(occurrence[i], value.eventTime).addMinutes(value.duration);
					
					// Save data
					eventList.push({
						id: value.id,
						start: eventStartDateTime.toString('yyyy-MM-dd HH:mm'),
						end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
						title: {html: `<span class="font-italic">${value.name}</span>`},
						color: "#" + value.portfolioColor
					}); 	
				}
			})
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
 * Helper function, change the calendar value.
 */

function changeCalendar(){
	
	// Convert to yyyy-mm-dd format.
    var yearMonthStr = currentDate.toString('yyyy-MM-dd');
    
    // Change calendar value.
    calendar.setOption('date', yearMonthStr);
    calendar.setOption('events', getEventList(yearMonthStr, "month"));
}

function combineDateAndTime(date, timeString) {
    // Ensure the input is a Date object
    if (!(date instanceof Date)) {
        console.error("First argument must be a Date object.");
        return;
    }

    // Ensure the time string is in the correct format
    if (!/^\d{2}:\d{2}:\d{2}$/.test(timeString)) {
        console.error("Time string must be in the format 'hh:mm:ss'.");
        return;
    }

    // Split the time string into hours, minutes, and seconds
    const [hours, minutes, seconds] = timeString.split(':').map(Number);

    // Create a new Date object with the same date but with specified time
    const combinedDateTime = new Date(date);
    combinedDateTime.setHours(hours, minutes, seconds);

    return combinedDateTime;
}
