/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Add, edit and delete events.
 	*/

/**
 * A hard limit on the number of recurrent event occurrences to avoid infinite loops.
 * This limit is used to prevent excessive computations when generating all occurrences of a recurrent event.
 * Typically set to a large number.
 * 
 * @constant {number}
 */

COUNT_HARD_LIMIT = 1000;

/**
 * Get the event information, and display them on the frontend.
 * @param {*} datePickersMap 
 * @returns 
 */

function getEventInfo(datePickersMap){

	// Get page name, e.g., for http://localhost:8080/events/add.do, the page name is add.do
	var pathname = window.location.pathname;
	var pageName = pathname.substring(pathname.lastIndexOf('/') + 1);
	
	// Get eventId
	var urlParams = new URLSearchParams(window.location.search);
    var eventId = urlParams.get('eventId');

	// Set mode (add or edit)
	var isEdit = pageName.includes("edit");
	$('#isEdit').val(isEdit ? 1 : -1);
    
	// Set edit options
	if(isEdit){

		var eventInfo;
		var commonInfo = " This page will be redirected to Add Event mode.";

		// Null check
		if(eventId == undefined || eventId == null || eventId.length == 0){
			warningPopup("eventId is empty.", "It must be not empty, and an integer." + commonInfo);
			toAddPage();
			return;
		}

		// Integer check
		if(eventId % 1 !== 0){
			warningPopup(eventId + " is not a valid eventId", "It must be an integer." + commonInfo);
			toAddPage();
			return;
		}

		// Zero check
		if(eventId == 0){
			warningPopup("eventId is empty.", "It must be an integer which is either positive or negative." + commonInfo);
			toAddPage();
			return;
		}

		// Existence check
		$.ajax({ 
			type: 'GET', 
    		url: '/api/event/view', 
    		data: { 
				eventId: Math.abs(eventId),
			}, 
    		async: false,
			success: function(json) {
				eventInfo = json;
        	},
			error: function(err) {   		
				dangerPopup("Failed to get event information due to a HTTP " + err.status + " error.", err.responseJSON.exception);
			}
		});
	
		if(eventInfo == undefined || eventInfo == null || eventInfo.length == 0){
			warningPopup("The event with id=" + eventId + " does not exist" + commonInfo);
			toAddPage();
			return;
		}

		// Get eventId
		$('#twigDeadline').hide();
		$('#eventIdBlock').show();
		$('#eventId').text(eventInfo.id);

		// Set title.
		//$('#currentAction').text('edit');
		$('#eventPageTitle').text('Edit Event: ' + eventInfo.name);
		$('#eventPageLink').text('Edit');
		$('#eventPageLink').attr('href', $('#editEventLink').val() + '?eventId=' + eventInfo.id);

		// Copy and graphics, only available in edit mode.
		$('.event-hidden-tabs').show();

		// Display delete button
		$('#deleteEventBtn').show();
		var actionData = {
            functionName: "removeEvent",
            params: [eventInfo.id]
        };
		//console.log(actionData)
        document.getElementById("deleteEventBtn").setAttribute("data-action", JSON.stringify(actionData));

		// Get name and location
		$('#eventName').val(eventInfo.name);
		$('#eventLocation').val(eventInfo.location);

		// Get organizer info and set it to read-only.
		$('#eventOrganizer').text(eventInfo.organizerName);
		$("#eventRole").html(`${eventInfo.positionName}, ${eventInfo.portfolioName}`);
		$("#eventRole").prop('disabled', true);

		// Get created and updated time.
		$('#eventCreatedTimeBlock').show();
		$('#eventUpdatedTimeBlock').show();
		$('#eventCreatedTime').text((new Date(eventInfo.createdTime)).toString('yyyy-MM-dd HH:mm:ss'));
		$('#eventUpdatedTime').text((new Date(eventInfo.updatedTime)).toString('yyyy-MM-dd HH:mm:ss'));
	
		// Get description
		$('#eventDescription').html(eventInfo.description);
    
		// Get event start and end date time
		var eventStartDate = Date.parse(eventInfo.startTime);    
		datePickersMap.get('eventStartDate').setDate(eventStartDate);
		$('#eventStartTime').val(eventStartDate.toString('HH:mm'));
		$('#eventRecurringTime').val(eventStartDate.toString('HH:mm'));
			
		var eventEndDate = eventStartDate.addMinutes(eventInfo.duration);
		datePickersMap.get('eventEndDate').setDate(eventEndDate);
		$('#eventEndTime').val(eventEndDate.toString('HH:mm'));
			
		// Get duration
		$('#eventDuration').val(minutesToString(eventInfo.duration));
		$('#eventDurationCalculated').text(formatTime(eventInfo.duration));
			
		// Get recurrent info.
		var rRule = new ETwig.RRuleFromStr(eventInfo.rrule);
		var rule = rRule.getRuleObj();
		
		// All day event?
		$('#eventAllDayEvent').prop('checked', eventInfo.allDayEvent);
		$('#eventStartTimeBlock').toggle(!eventInfo.allDayEvent);
		$('#eventEndTimeBlock').toggle(!eventInfo.allDayEvent);
			
		// This is a single time event, or the rRule is invalid.
		if(rule == undefined || rule == null){
				
			// Invalid rRule check.
			if(eventInfo.rrule != undefined && eventInfo.rrule != null && eventInfo.rrule.length > 0){
				dangerPopup("Failed to parse Recurrence Rule.", eventInfo.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
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
			$('#eventRRuleDiscription').text(rule.toText());
		
			// Recursion mode.
			setRecurrentMode(true);
			$('input[name="event-recurrent"][value="1"]').prop('checked', true);
				
			// Get RRule and selected options.
			getRRuleByInput();
				
			// Excluded dates
			var excludedDates = eventInfo.excluded;
			if(excludedDates != undefined && excludedDates != null){
				var excludedDatesStr = excludedDates.replace(/^\[|\]$/g, '').trim();
				var excludeDates = excludedDatesStr.split(/\s*,\s*/);
					
				for(var i=0; i<excludeDates.length; i++){
					addExcludeDate(excludeDates[i]);
				}
			}
		}

		// Get Graphics (TWIG component and Banner)
		getGraphics(eventId, false);
		getGraphics(eventId, true);

		// Banner request history
		$('#bannerRequestHistory').show();
        $('#requestsTable').DataTable({
            "processing": true,
            "serverSide": true,
			"lengthMenu": [[3, 5, 10], [3, 5, 10]],
			"pageLength": 3,
			"searching": false, 
			"order": [[0, "desc"]],
            "ajax": {
                "url": "/api/bannerRequest/list?eventId=" + eventId + "&isApproved=na",
                "type": "GET",
                "data": function(d) {
                    return $.extend({}, d, {
                        "sortColumn": d.columns[d.order[0].column].data,
                        "sortDirection": d.order[0].dir
                    });
                }
            },
            "columns": [
				{ "data": "id", "orderable": false},
				{ "data": "assetId", "orderable": false, "render": assetRender},
                { "data": "requestTime", "orderable": false, "render": dateWeekRender},
				{ "data": "approved", "orderable": false, "render": approvalStatusRender},
                //{ "data": "expectDate", "orderable": false},
                //{ "data": "requesterName", "orderable": false},
				//{ "data": "requestTime", "orderable": false},
				//{ "data": "requestComment", "orderable": false},
				//
				//{ "data": "approverName", "orderable": false},
				//{ "data": "responseTime", "orderable": false},
				//{ "data": "responseComment", "orderable": false},
				//
            ]
        });

		// Permission check
		if($('#myPortfolioName').val() != eventInfo.portfolioName) {
			$('#portfolioCheck').html(`
				<b>You cannot edit this event</b> because it was organized by a user with
				<span class="bold-text" style="color:#${eventInfo.portfolioColor};">${eventInfo.portfolioName}</span> portfolio.
			`);
		
			// Disable some inputs if no permission
			$('input').prop('disabled', true);
			$('select').prop('disabled', true);
			$('button').prop('disabled', true);

			$('#selectRole').prop('disabled', false);
			
		}
	}

	// Set add options
	else{

		// Set default options.
		setRecurrentMode(0);
		setAllDayEvent(false);
		setValidTo(true);
		
		// Set hidden fields.
		$('#twigDeadline').show();
		$('#eventIdBlock').hide();
		$('#eventCreatedTimeBlock').hide();
		$('#eventUpdatedTimeBlock').hide();
		$('.event-hidden-tabs').hide();
		$('#deleteEventBtn').hide();
	
		// Set title.
		//$('#currentAction').text('add');
		$('#eventPageTitle').text('Add Event');
		$('#eventPageLink').text('Add');
		$('#eventPageLink').attr('href', $('#addEventLink').val());
		$('#isEdit').val('0');
		
		// Set role(s).
		$('#eventOrganizer').text($('#userName').text());
		$('#eventOrganizer').css('color', '#808080');

		$('#bannerRequestHistory').hide();
	}
}

function getRRuleByInput(){
	var currentRule = {};
	
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
    
    // Calculate occurrences
    $('#eventRRuleAllDatesNum').text(allDates.length + ' occurrence(s)');
	if(allDates.length == 0){
		$('#eventRRuleAllDatesNum').attr("class","text-danger bold-text");
	}else{
		$('#eventRRuleAllDatesNum').attr("class","");
	}

    // Process and append new data
    $.each(allDates, function(i, date) {

        // Extracting date components
        date = new Date(date.getTime() + date.getTimezoneOffset() * 60000);
        var dateStr = date.toString('yyyy-MM-dd');
        
        var dayOfWeek = date.toLocaleString('default', { weekday: 'long' });
        var year = date.getFullYear();
        var month = date.toLocaleString('default', { month: 'long' });
        var dateOfMonth = getOrdinalIndicator(date.getDate());

        // Append row to table
        $('#eventRRuleAllDates tbody').append(
            `<tr>
            	<td>${dayOfWeek}</td>
            	<td>${year}</td>
            	<td>${month}</td>
            	<td>${dateOfMonth}</td>
            	<td>
            		<button class="btn btn-outline-danger btn-xs" onclick="addExcludeDate('${dateStr}');">
            			<i class="fa-solid fa-calendar-xmark"></i>
            		</button>
            	</td>
            </tr>`
        );
    });
    
	return rRule.toString();
}

/**
 * Adds an excluded date to the list of excluded dates.
 * This function appends the provided date string to a select element with the ID 'eventExcludedDates',
 * ensuring the date is only added if the string is non-empty.
 *
 * @param {string} dateStr - A date string in yyyy-mm-dd format.
 */

function addExcludeDate(dateStr){

	// Ignore the empty strings.
	if(dateStr.length > 0){
		$('#eventExcludedDates').append(`<option value="${dateStr}" selected>${dateStr}</option>`);
	}
}

function addEvent(){
	var newEventObj = {};
	
	/**
	 * Basic Info.
	 */
	
	// Current mode, -1 -> Copy 0 -> Add, 1-> Edit
	var mode = parseInt($('#isEdit').val());
	console.log(mode)

	// Mode in string
	var modeStr;
	if(mode < 0){
		modeStr = "copy";
	} else if(mode == 0){
		modeStr = "add";
	} else{
		modeStr = "edit";
	}

	var isEdit = mode > 0;
	newEventObj["isEdit"] = isEdit;
	
	// Event id: Required in edit mode and provided
	var eventId = parseInt($('#eventId').text());
	isNaN(eventId) ? newEventObj["id"] = -1 : newEventObj["id"] = eventId;
	
	// Event name
	var eventName = $.trim($('#eventName').val());
	if(eventName.length == 0){
		warningPopup("Event name is required.");
		//$('#eventName').addClass('is-invalid');
		return;
	}
	//$('#eventName').removeClass('is-invalid');
	newEventObj["name"] = eventName;
	
	// Event location
	newEventObj["location"] = $('#eventLocation').val();
	
	// Event description
	newEventObj["description"] = $("#eventDescription").summernote("code");
		
	/**
	 * Timing
	 */
	
	// Event recurrent: 0 -> Single time 1-> recurrent
	var eventRecurrent = parseInt($('input[type=radio][name=event-recurrent]:checked').val());
	newEventObj["isRecurring"]  = (eventRecurrent > 0);
	
	// All day event
	var allDayEvent = $("#eventAllDayEvent").is(':checked');
	newEventObj["allDayEvent"]  = allDayEvent;
	
	// Single Time event
	if(eventRecurrent == 0){
		
		// Start and end date
		var parsedStartDate = Date.parse($('#eventStartDate').val());
		var parsedEndDate = Date.parse($('#eventEndDate').val());

		if(parsedStartDate == null || parsedStartDate.length == 0){
			warningPopup("Event start date is required, and it must be yyyy-MM-dd format.");
			return;
		}
		
		if(parsedEndDate == null || parsedEndDate.length == 0){
			warningPopup("Event end date is required, and it must be yyyy-MM-dd format.");
			return;
		}
		
		// Start and end time
		var eventStartTime;
		var eventEndTime;
		
		// All day event
		if(allDayEvent){
			eventStartTime = '00:00';
			eventEndTime = '00:00';
		}
		
		// Not all day event
		else{
			
			eventStartTime = $('#eventStartTime').val();
			eventEndTime = $('#eventEndTime').val();
			
			if(eventStartTime.length == 0){
				warningPopup("Event start time is required, and it must be HH:mm format.");
				return;
			}
			
			if(eventEndTime.length == 0){
				warningPopup("Event end time is required, and it must be HH:mm format.");
				return;
			}
		}
		
		var singleTime = {};
		singleTime["startDateTime"] = combineDateAndTime(parsedStartDate, eventStartTime + ':00');
		singleTime["endDateTime"] = combineDateAndTime(parsedEndDate, eventEndTime + ':00');
		
		// Calculate the duration
		var timestampDiff = singleTime["endDateTime"] - singleTime["startDateTime"];
		if(timestampDiff <= 0){
			warningPopup("Event end time must after start time.");
			return;
		}
		
		// Time unit is minute. 1min = 60 seconds = 60,000 milliseconds.
		newEventObj["duration"] = timestampDiff / 60000;

		newEventObj["singleTime"] = singleTime;
	}
	
	// Recurring event
	else{
		var recurring = {};
		var eventRecurringTime;
		
		// All day event
		if(allDayEvent){
			eventRecurringTime = '00:00';
		}
		
		// Not all day event
		else{
			eventRecurringTime = $('#eventRecurringTime').val();
		
			if(eventRecurringTime.length == 0){
				warningPopup("Event start time is required, and it must be HH:mm format.");
				return;
			}
		}
		
		// Start time
		recurring["recurringTime"] = combineDateAndTime(Date.today(), eventRecurringTime + ':00');
		
		// Duration
		var eventDurationStr = stringToMinutes($('#eventDuration').val());
		if(eventDurationStr == null || eventDurationStr == undefined){
			warningPopup("The duration string is not well-formed", "The format must be _d __h __m");
			return;
		}
		//console.log(eventDurationStr)
		
		var eventDuration = parseInt(eventDurationStr);
		if(isNaN(eventDuration) || eventDuration <= 0){
			warningPopup("Event duration is required, and it must be a positive integer.");
			return;
		}
		newEventObj["duration"] = eventDuration;
		
		// RRule
		var eventRRule = getRRuleByInput();
		if(eventRRule == undefined || eventRRule == null){
			warningPopup("Invalid Recurrence Rule.", eventRRule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
			return;
		}
		recurring["rrule"] = eventRRule;
		
		// Excluded Dates
		recurring ["excluded"] = [...new Set($('#eventExcludedDates').val())]
		
		newEventObj["recurring"]  = recurring;
	}
	
	/**
	 * Additional info.
	 */
	
	// Properties 
	var selectedProperties = [];
	var mandatoryCheckPassed = true;
	
	$('.property-select-box').each(function() {
    	var propertyName = $(this).data('property-name');
    	var isMandatory = $(this).data('mandatory');
    	var selectedValue = parseInt($(this).val());

		// Mandatory check
		if(isMandatory && selectedValue <= 0){
			warningPopup("Selecting a value for the following property is required.", propertyName);
			mandatoryCheckPassed = false;
		}
		
		// Only store the positive optionIds.
		if(selectedValue > 0){
			selectedProperties.push(selectedValue);
		}
	});
	
	if(!mandatoryCheckPassed){
		return;
	}
	newEventObj["properties"]  = selectedProperties;
	
	// Banner request (only available when adding an event)
	if($("#eventRequestNow").is(':checked')){
		var graphics = {};
		
		// Returning Date
		var eventGraphicsDate = Date.parse($('#eventGraphicsDate').val());
		if(eventGraphicsDate == null || eventGraphicsDate.length == 0){
			warningPopup("Graphics returning date is required, and it must be yyyy-MM-dd format.");
			return;
		}
		graphics["returningDate"] = eventGraphicsDate.toString("yyyy-MM-dd");
		
		// Additional comments
		graphics["comments"] =  $("#requestComment").val();
		newEventObj["graphics"] = graphics;
	}
	
	$.ajax({
   		url: isEdit ? '/api/event/edit' : '/api/event/add', 
   		type: "POST",
   		async: false,
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newEventObj),
   		success: function () {
			successPopup("Event " + modeStr + "ed successfully.");
			setTimeout(function() { 
				isEdit ? window.location.reload() : $(location).attr('href', $('#eventCalendarLink').val()); 
			}, 2500);
    	},
    	error: function (err) {
    		dangerPopup("Failed to " + modeStr +"  event due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
 	});
}

/**
 * Initializes a WYSIWYG editor using Summernote on the specified HTML element.
 * 
 * This function configures the Summernote editor with a placeholder for 
 * event descriptions, a tab size of 4, and fixed dimensions of 300px height.
 * It also sets up a toolbar with various formatting options.
 *
 * @param {HTMLElement} boxElem - The HTML element where the Summernote editor will be initialized.
 */

function initDescriptionBox(boxElem){
	$(boxElem).summernote({
		placeholder: 'Event description',
        tabsize: 4,
        height: 300,
        minHeight: 300,
  		maxHeight: 300,
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

/**
 * Get the ordinal indicator for a number.
 * This function returns the number with its corresponding ordinal indicator
 * (e.g., 1st, 2nd, 3rd, 4th, ...).
 *
 * @param {number} n - The number for which to get the ordinal indicator.
 * @returns {string} The number followed by its ordinal indicator.
 */

function getOrdinalIndicator(n) {
    var s = ["th", "st", "nd", "rd"];
    var v = n % 100;
    return n + (s[(v - 20) % 10] || s[v] || s[0]);
}

/**
 * Enable the "delete event" button when click the confirmation checkbox.
 */

//function deleteEventCheckboxOnChange(){
//	$('#confirmDeletion').change(function() {
//		$('#deleteEventBtn').prop('disabled', !this.checked);
//    });
//}

/**
 * Converts a duration in minutes into a formatted string "_d __h __m".
 * This function formats the given minutes into a human-readable string that represents the number of days, hours, and minutes.
 * The days component is capped at 9, meaning if the computed days exceed 9, it will be represented as 9.
 * 
 * @param {int} minutes - The total number of minutes to convert. Must be a non-negative integer.
 * @returns {string} - The formatted duration string. Days are capped at 9 and both hours and minutes are zero-padded to two digits.
 * 
 * Example usage:
 * minutesToString(1500);    // returns "1d 01h 00m"
 * minutesToString(10000);   // returns "9d 00h 00m" (days are capped at 9)
 * minutesToString(61);      // returns "0d 01h 01m"
 */

function minutesToString(minutes) {
    const perDay = 1440;
    const perHour = 60;

    var days = Math.floor(minutes / perDay);
    var hours = Math.floor((minutes % perDay) / perHour);
    var mins = minutes % 60;

	// The days field has only one digit.
	if(days > 9){
		days = 9;
	}

    return days + "d " + pad(hours, 2) + "h " + pad(mins, 2) + "m";
}

/**
 * Converts a duration string formatted as "_d __h __m" back to total minutes.
 * This function is useful for interpreting human-readable duration strings into a numerical value that represents the total minutes.
 * 
 * @param {string} durationStr - The duration string in the format of "_d __h __m", where "_" can be any integer.
 *        For example, "2d 3h 15m" represents 2 days, 3 hours, and 15 minutes.
 * @returns {number|null} - Returns the total duration in minutes if the input string is well-formed according to the specified format.
 *        Returns null if the input string does not match the expected format, indicating an improperly formed input.
 * 
 * Example usage:
 * stringToMinutes("2d 3h 15m"); 	// returns 3335
 * stringToMinutes("hello world"); 	// returns null
 */

function stringToMinutes(durationStr) {
    var regex = /(\d+)d\s+(\d+)h\s+(\d+)m/;
    var matches = durationStr.match(regex);

	// The duration string is well-formed
    if (matches && matches.length === 4) {
        var days = parseInt(matches[1], 10);
        var hours = parseInt(matches[2], 10);
        var minutes = parseInt(matches[3], 10);

        // Calculate total minutes
        return (days * 24 * 60) + (hours * 60) + minutes;
    } 
	
	// Not well-formed
	else {
        return null;
    }
}

/**
 * Calculates the duration of an event based on its start and end times and updates the display.
 * The function first checks if the event is marked as an all-day event. 
 * If it is, it assumes the event starts and ends at midnight of the selected dates. 
 * Otherwise, it uses the user-provided start and end times. 
 * The duration is calculated in minutes, converted to a user-friendly format, and displayed in a specific page element.
 */

function calculateDuration(){

	// Get event date time
	var allDayEvent = $("#eventAllDayEvent").is(':checked');
	var eventStartTime = allDayEvent ? '00:00' : $('#eventStartTime').val();
	var eventEndTime = allDayEvent ? '00:00' : $('#eventEndTime').val();

	var startDateTime = combineDateAndTime(Date.parse($('#eventStartDate').val()), eventStartTime + ':00');
	var endDateTime = combineDateAndTime(Date.parse($('#eventEndDate').val()), eventEndTime + ':00');
	
	// Re-format the duration string.
	$('#eventDurationCalculated').text(formatTime((endDateTime - startDateTime) / 60000));
}

/**
 * Redirects the user to the add event page after a delay of 2 seconds.
 */

function toAddPage(){
	setTimeout(function() {
        window.location.href = "/events/add.do";
    }, 2000);
}

/**
 * Fetches and displays graphics for a specific event component based on the given parameters.
 * The function determines whether to fetch a banner or a TWIG component graphic based on the `isBanner` flag.
 * It sends a GET request to the server and dynamically updates the HTML content of the selected element
 * with the graphic if available, or displays a placeholder in case of no results or an error.
 *
 * @param {string} eventId - The unique identifier for the event for which graphics are being requested.
 * @param {boolean} isBanner - Determines the type of graphic to fetch:
 *                             true for an event banner, false for a TWIG component.
 */

function getGraphics(eventId, isBanner){

	var selectedElement = isBanner ? "#eventBanner" : "#eventTwigComponent";
	var selectedButton = isBanner ? "#bannerDownloadBtn" : "#twigComponentDownloadBtn";
	var title = isBanner ? "Banner" : "TWIG Component";

	$.ajax({
		url: `/api/eventGraphics/list`, 
		type: "GET",
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		data: {
			eventId: eventId,
			isBanner: isBanner,
			start: 0,		// The first page of result set
			length: 1,		// This page has only 1 item
			draw: 1,
			sortColumn: 'id',
			sortDirection: 'desc'
		},
		success: function (result) {
			if(result.recordsTotal > 0){

				var imageUrl = '/assets/content.do?assetId=' + result.data[0].assetId;
				$(selectedElement).html(`<img src="${imageUrl}" class="img-fluid"></img>`);
				$(selectedButton).attr("onclick", `window.location.href='${imageUrl}&download=true'`);
				$(selectedButton).attr("disabled", false);
			}

			else{
				$(selectedElement).html(`		
					<div class="d-flex justify-content-center bold-text text-secondary">
						No ${title}.
					</div>`
				);

				$(selectedButton).attr("onclick", "");
				$(selectedButton).attr("disabled", true);
			}
		 },
		 error: function (err) {
			dangerPopup(`Failed to get ${title} due to a HTTP ${err.status} error.`, err.responseJSON.exception);
		 }
	});
}

/**
 * Sends an AJAX request to remove an event by its ID.
 * 
 * This function makes a GET request to the server to remove the specified event. 
 * Upon successful removal, it displays a success popup message and redirects the user 
 * to the events page after a short delay. If the request fails, it displays an error popup message 
 * with the HTTP status code and exception details.
 * 
 * @function removeEvent
 * @param {number|string} eventId - The ID of the event to be removed.
 */

function removeEvent(eventId) {
	$.ajax({
   		url: '/api/event/remove', 
   		type: "GET",
   		data: {
			eventId: eventId
		},
   		success: function () {
			successPopup("The event is removed successfully.");
			setTimeout(function() {	$(location).attr('href','/events/'); }, 2500);
    	},
    	error: function (err) {
    		dangerPopup("Failed to remove event due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    	}
 	});
}

function eventListTable() {
	$('#eventsTable').DataTable({
		"processing": true,
		"serverSide": true,
		"lengthMenu": [[10, 20, 50], [10, 20, 50]],
		"pageLength": 20,
		"language": {
            "searchPlaceholder": "Event Name",
        },
		"order": [[0, "desc"]],
		"ajax": {
			"url": "/api/event/list",
			"type": "GET",
			"data": function(d) {
				return $.extend({}, d, {
					"sortColumn": d.columns[d.order[0].column].data,
					"sortDirection": d.order[0].dir
				});
			}
		},
		"columns": [
			{ "data": "id" },
			{ "data": "name" },
			{ "data": "location" },
			{ 
				"data": "recurring",
				"render": function (data, type, row) {
					//if (weeksDifference === 0) {
					//	return dateWeek + `<span class="badge badge-danger">In this week</span>`;
					//}

					//var color = data ? "success" : "primary";
					//var output = `<span class="badge badge-${color}">${data}</span>`;

					if(data) {
						var out = '<span class="badge badge-primary">Yes</span>';
						
						// Excluded date check (it does not included in the rrule)
						if(row.excluded) {
							out += '&nbsp;<span class="badge badge-warning">Contains excluded dates</span>';
						}

						// Display human-understandable recurrent rule
						var rRule = new ETwig.RRuleFromStr(row.rrule);
						var rule = rRule.getRuleObj();
						return out + '<br>' + rule.toText();
					}

					else {
						return `<span class="badge badge-danger">No</span>`;
					}

				} 
			},
			{ "data": "startTime", "render": dateWeekRender },
			{ "data": "duration" },
			/*
			
			{ 
				"data": "size", 
				"render": function (data, type, row) {} 
			},
			{ "data": "uploader", "orderable": false },
			{ "data": "lastModified", "orderable": true, "render": dateWeekRender },
			{ "mRender": assetPreviewRender, "orderable": false },
			{ 
				// Action
				"mRender": function (data, type, full) {
					var disabledStr = full.canDelete ? '' : 'disabled';
					return `
						<div class="btn-group" role="group">
							<a href="${$('#assetContentLink').val()}?assetId=${full.id}&download=true" class="btn btn-outline-secondary btn-sm" target="_blank">
								<i class="fa-solid fa-download"></i>&nbsp;Download
							</a>&nbsp;
				
							<a href="${$('#assetContentLink').val()}?assetId=${full.id}&download=false" class="btn btn-outline-primary btn-sm" target="_blank">
								<i class="fa-solid fa-magnifying-glass-plus"></i>&nbsp;View
							</a>&nbsp;
				
							<button type="button" class="btn btn-outline-danger btn-sm" ${disabledStr}>
								<i class="fa-solid fa-trash"></i>&nbsp;Delete
							</button>
						</div>
					`;
				}, 
				"orderable": false 
			}
				*/
		]
	});
}