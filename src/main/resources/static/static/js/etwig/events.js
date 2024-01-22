/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Add, edit and delete events.
 	*/

/**
 * A hard limit of the recurrent event counts to avoid infinite loops when trying to get all occurrences. It's usually a big number.
 */

COUNT_HARD_LIMIT = 1000;

function getEventInfo(datePickersMap){
	
	// Get eventId
	var urlParams = new URLSearchParams(window.location.search);
    var eventId = urlParams.get('eventId');
    
    /**
	 * Add mode.
	 */
	
    // Null check.
    if(eventId == undefined || eventId == null || eventId.length == 0){
		warningToast("The eventId provided is empty.", "It must be not empty, and an integer. This page will be switched to Add Event mode.");
		initAddOption();
		return;
	}
    
    // Invalid check (not an integer).
    if(eventId % 1 !== 0){
		warningToast(eventId +" is not a valid eventId", "It must be an integer. This page will be switched to Add Event mode.");
		initAddOption();
		return;
	}
    
    // Zero or Negative eventId, add event mode.
    if(eventId <= 0){
		initAddOption();
		return;
	}
	
	// Positive eventId, search it in the DB.
	var eventInfo;
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getEventById', 
    	data: { 
			eventId: eventId,
		}, 
    	async: false,
		success: function(json) {
			eventInfo = json;
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get event information due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	
	if(eventInfo == undefined || eventInfo == null || eventInfo.length == 0){
		warningToast("The event with id=" + eventId + " does not exist");
		initAddOption();
		return;
	}
    
    /**
	 * Edit mode.
	 */
	
    // Get eventId
    $('#eventIdBlock').show();
    $('#eventId').text(eventInfo.id);
    
    // Set the title.
	$('#eventPageTitle').text('Edit Event: ' + eventInfo.name);
	$('#eventPageLink').text('Edit Event');
	$('#eventPageLink').attr('href', '/events/edit?eventId=' + eventInfo.id);
	$('#eventGraphicsTab').show();
	$('#eventGraphicsLink').attr('href', '/events/graphics?eventId=' + eventInfo.id);
	$('#eventRequestNowBlock').hide();
    
    // Get name and location
    $('#eventName').val(eventInfo.name);
    $('#eventLocation').val(eventInfo.location);
    
    // Get organizer info and set it to read-only.
    var position = eventInfo.position;
    $("#eventRole").append(`<option value="${position.userRoleId}">${position.position}, ${position.portfolio.name}</option>`);
    $("#eventRole").prop('disabled', true);
    
    // Get created and updated time.
    $('#eventCreatedTimeBlock').show();
    $('#eventUpdatedTimeBlock').show();
    $('#eventCreatedTime').text((new Date(eventInfo.createdTime)).toString('yyyy-MM-dd HH:mm:ss'));
    $('#eventUpdatedTime').text((new Date(eventInfo.updatedTime)).toString('yyyy-MM-dd HH:mm:ss'));
    
    // Get description
    $('#eventDescription').html(eventInfo.description);
    
    // Get event start and end datetime
    var eventStartDate = Date.parse(eventInfo.startTime);    
    datePickersMap.get('eventStartDate').setDate(eventStartDate);
    $('#eventStartTime').val(eventStartDate.toString('HH:mm'));
    $('#eventRecurringTime').val(eventStartDate.toString('HH:mm'));
    
    var eventEndDate = eventStartDate.addMinutes(eventInfo.duration);
    datePickersMap.get('eventEndDate').setDate(eventEndDate);
    $('#eventEndTime').val(eventEndDate.toString('HH:mm'));
    
    // Get the duration
    $('#eventDuration').val(eventInfo.duration);
    $('#eventDurationCalculated').text(formatTime(eventInfo.duration));
    
    // Get recurrent info.
    var rRule = new ETwig.RRuleFromStr(eventInfo.rrule);
	var rule = rRule.getRuleObj();
	
	// This is a single time event, or the rRule is invalid.
	if(rule == undefined || rule == null){
		
		// Invalid rRule check.
		if(eventInfo.rrule != undefined && eventInfo.rrule != null && eventInfo.rrule.length > 0){
			dangerToast("Failed to parse Recurrence Rule.", eventInfo.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
		}
		
		// Single time mode
    	setRecurrentMode(false);
    	$('input[name="event-recurrent"][value="0"]').prop('checked', true);
	}
	
	// This is a recurring event.
	else{
		
		// Set the frequency.
		$('input[name="eventFrequency"][value="' + rule.options.freq + '"]').prop('checked', true);
		
		// Set valid from
		datePickersMap.get('eventValidFromDate').setDate(rule.options.dtstart);
		
		// Set valid to
		$('#eventValidToDateEnabled').prop('checked', rule.options.until != null);
		setValidTo(rule.options.until != null);
		if(rule.options.until != null){
			datePickersMap.get('eventValidToDate').setDate(rule.options.until);
		}
		
		// Set count
		$('#eventCount').val(rule.options.count);
		
		// Set interval
		$('#eventInterval').val(rule.options.interval);
		
		// Set by weekday
		if(rule.options.byweekday != null){
			$('#eventByWeekDay option').each(function() {
       	 		if (rule.options.byweekday.includes(parseInt($(this).val()))) {
           			$(this).prop('selected', true);
       			}    
       		});
		}
		
		// Set by month
		if(rule.options.bymonth != null){
			$('#eventByMonth option').each(function() {
       	 		if (rule.options.bymonth.includes(parseInt($(this).val()))) {
           			$(this).prop('selected', true);
       			}    
       		});
		}
		
		// Set by month day
		$('#eventByMonthDay').val(rule.options.bymonthday);
		
		// Display the rule.
		//$('#eventRFCRRule').text(rule.toString());
		$('#eventRRuleDiscription').text(rule.toText());
		//rule.options.until == null ? $('#eventValidToDate').val('') : datePickersMap.get('eventValidToDate').setDate(rule.options.until);

		// Recursion mode.
		setRecurrentMode(true);
		$('input[name="event-recurrent"][value="1"]').prop('checked', true);
		
		//console.log(rule.options);
		getRRuleByInput();
	}
	
}

function getRRuleByInput(){
	var currentRule = {};
	//currentRule["tzid"] = 'Australia/Brisbane';
	
	// Frequency: 1 -> Monthly, 2 -> Weekly, 3 -> Daily
	var eventFrequency = parseInt($('input[type=radio][name=eventFrequency]:checked').val());
	currentRule["freq"] = constrainNumber(eventFrequency, 1 , 3);
	
	// Valid From
	currentRule["dtstart"] = Date.parse($('#eventValidFromDate').val());
	
	// Valid To
	var validToEnabled = $('#eventValidToDateEnabled').is(':checked');
	var validToDate = Date.parse($('#eventValidToDate').val());
	var validToIsValid = validToEnabled && validToDate !=undefined && validToDate != null;
	
	if(validToIsValid){
		currentRule["until"] = Date.parse($('#eventValidToDate').val());
	}
	
	if(!validToIsValid){
		currentRule["count"] = COUNT_HARD_LIMIT;
	}
	
	// Count: A number with at least 2.
	var eventCount = parseInt($('#eventCount').val());
	if(!isNaN(eventCount)){
		currentRule["count"] = constrainNumber(eventCount, 2, COUNT_HARD_LIMIT);
	}
	
	// Interval
	var eventInterval = parseInt($('#eventInterval').val());
	if(!isNaN(eventInterval)){
		currentRule["interval"] = Math.max(eventInterval, 1);
	}
	
	// By week day. 0 -> Monday, ..., 6 -> Sunday
	var eventByWeekDay = $('#eventByWeekDay').val();
	if(eventByWeekDay.length > 0){
		
		// Convert each string in the array to a number
        var eventByMonthInt = eventByWeekDay.map(function(item) {
            return parseInt(constrainNumber(item, 0, 6), 10);
        });
        currentRule["byweekday"] = eventByMonthInt;
	}
	
	// By month. 0 -> Jan, ..., 11 -> Dec
	var eventByMonth = $('#eventByMonth').val();
	if(eventByMonth.length > 0){
        var eventByMonthInt = eventByMonth.map(function(item) {
            return parseInt(constrainNumber(item, 1, 12), 10);
        });
        currentRule["bymonth"] = eventByMonthInt;
	}
	
	// By month day
	var eventByMonthDay = $('#eventByMonthDay').val();
    var eventByMonthDayInt = eventByMonthDay.split(',').filter(function(item) {
        return /^\d+$/.test(item);
    }).map(Number);
	
	if(eventByMonthDayInt.length > 0){
		currentRule["bymonthday"] = eventByMonthDayInt;
	}

	// Get recurrent info.
    var rRule = new ETwig.RRuleFromForm(currentRule);
    rRule.generateRRule();
	var allDates = rRule.all();
	
	// Set description
    $('#eventRRuleDescription').text(rRule.toText());
    
    // Clear existing data
    $('#eventRRuleAllDates tbody').empty();
    $('#eventRRuleAllDatesNum').text(allDates.length);

    // Process and append new data
    $.each(allDates, function(i, date) {
        // Extracting date components
        
        date = new Date(date.getTime() + date.getTimezoneOffset() * 60000);
        
        var dayOfWeek = date.toLocaleString('default', { weekday: 'long' });
        var year = date.getFullYear();
        var month = date.toLocaleString('default', { month: 'long' });
        var dateOfMonth = getOrdinalIndicator(date.getDate());

        // Append row to table
        $('#eventRRuleAllDates tbody').append(
            '<tr><td>' + dayOfWeek + '</td><td>' + year + '</td><td>' + month + '</td><td>' + dateOfMonth + '</td></tr>'
        );
    });
    
    //console.log(currentRule); 
	//console.log(rRule.all());
	//console.log(rRule.toString());
}

/**
 * Validate the add event form. If passed, add the event.
 * @param embedded True if the event add page is in the "embed" mode, i.e., this page is in the frame of the calendar. 
 * 					The modal should be closed after operation.
 * 					False otherwise. The page should refresh after operation.
 * @param isEdit True the mode is edit event, other the mode is add event.
 */

function addEvent(embedded, isEdit){
	
	// Event id: Required in edit mode.
	var eventId = $('#eventId').val();
	if(isEdit && eventId.length == 0){
		warningToast("Event id is required in edit mode.");
		return;
	}
	
	// Event name: Required
	var eventName = $.trim($('#eventName').val());
	if(eventName.length == 0){
		warningToast("Event name is required.");
		return;
	}
	
	// Event location: Optional
	var eventLocation = $.trim($('#eventLocation').val());
	
	// Event description: Optional
	var eventDescription = $("#eventDescription").summernote("code");
	
	// Event time unit: Required
	var eventTimeUnit = $('input[type=radio][name=eventTimeUnit]:checked').val();
	
	// Event startTime: Required
	var eventStartTime = $('#eventStartTime').val();
	var parsedStartTime = Date.parse(eventStartTime);
	
	if(eventStartTime.length == 0){
		warningToast("Event start time is required");
		return;
	}
	
	if(parsedStartTime == null){
		warningToast("Event start time is not well-formed.");
		return;
	}
	
	// Event duration: Required only if the eventTimeUnit is not "customize""
	var eventDuration = $('#eventDuration').val();
	var realDuration = 0;
	
	// Event endTime: Required only if the eventTimeUnit is "customize""
	var eventEndTime = $('#eventEndTime').val();
	var parsedEndTime = Date.parse(eventEndTime);
	
	// Check "Duration" / "End Time" by selected time units.
	if(eventTimeUnit == "c"){
		
		if(eventEndTime.length == 0){
			warningToast("Event end time is required");
			return;
		}
		
		if(parsedEndTime == null){
			warningToast("Event end time is not well-formed.");
			return;
		}
		
		var timestampDiff = parsedEndTime.getTime() - parsedStartTime.getTime();
		if(timestampDiff <= 0){
			warningToast("Event end time must after start time.");
			return;
		}
		
		// timestampDiff unit is millisecond, realDuration unit is minute
		realDuration = timestampDiff / 60000;
	}
	
	else{
		if(eventDuration.length == 0){
			warningToast("Event duration is required, and it must be a number.");
			return;
		}
		
		if(eventDuration <= 0){
			warningToast("Event duration must be a positive number.");
			return;
		}
		realDuration = eventDuration;
	}
	
	// Event portfolio: Required
	var eventPortfolio = $('#eventPortfolio').find(":selected").val();
	
	// Event Organizer: Required
	var eventOrganizer = $('#eventOrganizer').find(":selected").val();

	// Properties 
	var selectedProperties = [];
	var mandatoryCheckPassed = true;
	
	$('.property-select-box').each(function() {
    	//var propertyId = $(this).data('property-id');
    	var propertyName = $(this).data('property-name');
    	var isMandatory = $(this).data('mandatory');
    	var selectedValue = $(this).val();

		// Mandatory check
		if(isMandatory && selectedValue <= 0){
			warningToast("Selecting a value for property " + propertyName + " is required.");
			mandatoryCheckPassed = false;
			//return;
		}
		
		// Only store the positive optionIds.
		if(selectedValue > 0){
			selectedProperties.push(selectedValue);
		}
	});
	
	if(!mandatoryCheckPassed){
		return;
	}
	
	// Create an object for the new event.
	var newEventObj = {
		"eventId" : eventId,
		"isRecurrent": false,
		"name": eventName,
		"location": eventLocation,
		"description": eventDescription,
		"timeUnit": eventTimeUnit,
		"startTime": eventStartTime,
		"duration": realDuration,
		"portfolio": eventPortfolio,
		"organizer": eventOrganizer,
		"properties": selectedProperties
	};
	
	// POST the new (or changed) event into the event add/edit API
	var url = isEdit ? "/api/private/editEvent" : "/api/private/addEvent";
	var mode =  isEdit ? "edit" : "add";
	
	var hasError = true;
	$.ajax({
   		url: url, 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newEventObj),
   		success: function (result) {
			if(result.error > 0){
				dangerToast("Failed to " + mode +" event.", result.msg);
				hasError = true;
			}else{
				successToast("Event  " + mode +"ed  successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerToast("Failed to  " + mode +"  event due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Post-add operations
	// More timeout if error happens.
	setTimeout(
		function() {
			embedded ? parent.location.reload() : window.location.reload();
		}, 
		hasError ? 10000 : 2000
	);
}

/**
 * Create a WYSIWYG editor by using summernote.
 * @param boxElem The HTML element for this editor.
 */

function initDescriptionBox(boxElem){
	$(boxElem).summernote({
		placeholder: 'Event description',
        tabsize: 4,
        height: 350,
        minHeight: 200,
  		maxHeight: 500,
  		toolbar: [
  			['style', ['style']],
  			['font', ['bold', 'underline', 'clear']],
  			['fontname', ['fontname']],
  			['color', ['color']],
  			['para', ['ul', 'ol', 'paragraph']],
  			['table', ['table']],
  			['insert', ['link']],
  			['view', ['fullscreen', 'help']],
		]
	});
}

function setRecurrentMode(recurrentMode){
	//getRRuleByInput();
	$('#singleTimeEventOptions').toggle(recurrentMode == 0);
    $('#recurringEventOptions').toggle(recurrentMode != 0);
}

function setAllDayEvent(allDayEvent){
	//getRRuleByInput();
	$('[id^="event"][id$="TimeBlock"]').toggle(allDayEvent);
    $('[id^="event"][id$="TimeBlock"]').toggle(!allDayEvent);
}

function setValidTo(enableValidTo){
	getRRuleByInput();
	$('#eventValidToDate').attr('disabled', !enableValidTo);
	if(!enableValidTo){
		$('#eventValidToDate').val('');
	}
}

function setGraphicsRequest(graphicsRequest){
	$('#returningDate').attr('disabled', !graphicsRequest);
	$('#requestComment').attr('disabled', !graphicsRequest);
}

function createDatePickers() {
	var datePickersMap = new Map();
	
    // Select all elements with IDs that match the pattern "event*Date"
    $('[id^="event"][id$="Date"]').each(function() {
        var inputId = '#' + this.id;
        var wrapperId = '#' + this.id + 'Wrapper';

        // Initialize the date picker
        var datePicker = new tui.DatePicker(wrapperId, {
            date: Date.today(),
            type: "date",
            input: {
                element: inputId,
                format: "yyyy-MM-dd",
                usageStatistics: false
            },
        });
        
        // Also store this in a map.
		datePickersMap.set(this.id, datePicker);
    });
    	
	// Set onchange listener
	datePickersMap.get('eventValidFromDate').on('change', () => {
    	getRRuleByInput();
	});
	datePickersMap.get('eventValidToDate').on('change', () => {
    	getRRuleByInput();
	});
    
    return datePickersMap;
}

function initAddOption(){
	
	// Set the default options.
	setRecurrentMode(0);
	setAllDayEvent(false);
	setValidTo(true);
	setGraphicsRequest(false);
	
	// Set the hidden fields.
	$('#eventIdBlock').hide();
	$('#eventCreatedTimeBlock').hide();
    $('#eventUpdatedTimeBlock').hide();
    $('#eventGraphicsTab').hide();
    $('#eventRequestNowBlock').show();
   
	// Set the title.
	$('#eventPageTitle').text('Add Event');
	$('#eventPageLink').text('Add Event');
	$('#eventPageLink').attr('href', '/events/edit?eventId=-1');
	
	// Set the role(s).
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getMyPositions', 
    	async: false,
		success: function(json) {
			
			// Iterate all roles.
			jQuery.each(json, function(id, value) {
				$("#eventRole").append(`<option value="${value.userRoleId}">${value.position}, ${value.portfolioName}</option>`);
			})
        },
        
        // Toast error info when it happens
    	error: function(err) {   		
			dangerToast("Failed to get user positions due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	
}

function getOrdinalIndicator(n) {
    var s = ["th", "st", "nd", "rd"];
    var v = n % 100;
    return n + (s[(v - 20) % 10] || s[v] || s[0]);
}

/**
 * Enable the "delete event" button when click the confirmation checkbox.
 */

function deleteEventCheckboxOnChange(){
	$('#confirmDeletion').change(function() {
		$('#deleteEventBtn').prop('disabled', !this.checked);
    });
}