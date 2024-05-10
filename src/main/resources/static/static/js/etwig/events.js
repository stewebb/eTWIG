/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: Add, edit and delete events.
 	*/

/**
 * A hard limit of recurrent event count to avoid infinite loops when trying to get all occurrences. 
 * It's usually a big number.
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
    
    // Get my positions
    var myPositions = getMyPositions();

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

		// Permission Check
		var myPortfolioIds = [];
		var myPortfolioNames = [];
		for (let key in myPositions) {
			  myPortfolioIds.push(myPositions[key].portfolioId)
			  myPortfolioNames.push(myPositions[key].portfolioName)
		}
		
		// My portfolios should includes the event portfolio.
		if (!myPortfolioIds.includes(eventInfo.portfolioId)){
			$('#noPermissionCallout').html(`
				<div class="callout callout-primary">
					<h5 class="bold-text mb-3">No edit permission</h5>
						This event was created by the user with <span class="bold-text" style="color:#000000">${eventInfo.portfolioName}</span> portfolio. <br />
						However, your portfolios are [
						<span class="bold-text" style="color:#000000}">${myPortfolioNames}</span>, ].
				</div>
			`)
		}

		// Get eventId
		$('#eventIdBlock').show();
		$('#eventId').text(eventInfo.id);

		// Set title.
		$('#eventPageTitle').text('Edit Event: ' + eventInfo.name);
		$('#eventPageLink').text('Edit Event');
		$('#eventPageLink').attr('href', '/events/edit.do?eventId=-' + eventInfo.id);

		// Copy and graphics, only available in edit mode.
		$('.event-hidden-tabs').show();
		//$('#eventCopyLink').attr('href', '/events/edit?eventId=-' + eventInfo.id);
		//$('#eventGraphicsLink').attr('href', '/events/graphics?eventId=' + eventInfo.id);

		// Get name and location
		$('#eventName').val(eventInfo.name);
		$('#eventLocation').val(eventInfo.location);

		// Get organizer info and set it to read-only.
		$('#eventOrganizer').text(eventInfo.organizerName);
		$("#eventRole").append(`<option value="${eventInfo.userRoleId}">${eventInfo.positionName}, ${eventInfo.portfolioName}</option>`);
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
			
		// Event options
		getSelectedOptions(eventId);

		// Get TWIG component
		$.ajax({
			url: `/api/eventGraphics/list`, 
			type: "GET",
			//async: false,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			data: {
				eventId: eventId,
				isBanner: false,
				start: 0,		// First page
				length: 1,		// This page has only 1 item
				draw: 1,
				sortColumn: 'id',
				sortDirection: 'desc'
			},
			success: function (result) {
				if(result.recordsTotal > 0){
					//alert(`<img src="/asset/content.do?assetId=${result.data[0].assetId}" class="img-fluid"></img>`);
					$("#eventTwigComponent").html(`<img src="/assets/content.do?assetId=${result.data[0].assetId}" class="img-fluid"></img>`);
				}
		 	},
		 	error: function (err) {
				dangerPopup("Failed to get TWIG component due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		 	}
	  	});

		// Get Banner
		$.ajax({
			url: `/api/eventGraphics/list`, 
			type: "GET",
			//async: false,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			data: {
				eventId: eventId,
				isBanner: true,
				start: 0,		// First page
				length: 1,		// This page has only 1 item
				draw: 1,
				sortColumn: 'id',
				sortDirection: 'desc'
			},
			success: function (result) {
				if(result.recordsTotal > 0){
					//alert(`<img src="/asset/content.do?assetId=${result.data[0].assetId}" class="img-fluid"></img>`);
					$("#eventBanner").html(`<img src="/assets/content.do?assetId=${result.data[0].assetId}" class="img-fluid"></img>`);
				}
		 	},
		 	error: function (err) {
				dangerPopup("Failed to get Banner due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		 	}
	  	});

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
    //});
	}

	// Set add options
	else{

		// Set default options.
		setRecurrentMode(0);
		setAllDayEvent(false);
		setValidTo(true);
		//setGraphicsRequest(true);
		
		// Set hidden fields.
		$('#eventIdBlock').hide();
		$('#eventCreatedTimeBlock').hide();
		$('#eventUpdatedTimeBlock').hide();
		$('.event-hidden-tabs').hide();
		$('#eventRequestNowBlock').show();
	
		// Set title.
		$('#eventPageTitle').text('Add Event');
		$('#eventPageLink').text('Add Event');
		$('#eventPageLink').attr('href', '/events/add.do');
		$('#isEdit').val('0');
		
		// Set role(s).
		$('#eventOrganizer').text($('#userName').text());
		for (let key in myPositions) {
			$("#eventRole").append(`<option value="${myPositions[key].userRoleId}">${myPositions[key].position}, ${myPositions[key].portfolioName}</option>`);
		}

		$('#eventGraphicsTab').hide();
    	//$('#eventRequestNowBlock').show();
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
 * Add an excluded date to the list.
 * @param {string} dateStr Date string in yyyy-mm-dd format.
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
	
	// Event Organizer Role
	newEventObj["eventRole"]  = parseInt($('#eventRole').find(":selected").val());
	
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
	
	
	// Graphics request (only available when adding an event)
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

	console.log(newEventObj);
	
	var hasError = true;
	$.ajax({
   		url: isEdit ? '/api/event/edit' : '/api/event/add', 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newEventObj),
   		success: function (result) {
			if(result.error > 0){
				dangerPopup("Failed to " + modeStr +" event.", result.msg);
				hasError = true;
			}else{
				var modeStrPP = (modeStr == "copy") ? "copied" : (modeStr + "ed");
				successPopup("Event " + modeStrPP + " successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerPopup("Failed to " + modeStr +"  event due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	if(!hasError){
		setTimeout(function() { isEdit ? window.location.reload() : $(location).attr('href','/events/calendar.do'); }, 2500);
	}
}

/**
 * Create a WYSIWYG editor by using summernote.
 * @param boxElem The HTML element for this editor.
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

//function toggleBannerRequestArea(graphicsRequest){
//	//$('#returningDate').attr('disabled', !graphicsRequest);
//	//$('#requestComment').attr('disabled', !graphicsRequest);
//	$('#eventRequestNowBlock').toggle(graphicsRequest);
//}

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
 * e.g., 1st, 2nd, 3rd, 4th...
 * @param {int} n A number
 * @returns Number with the ordinal indicator.
 */

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

/**
 * Get all selected options for a event
 * @param {int} eventId 
 */

function getSelectedOptions(eventId){

	// No need to get in add mode.
	if(eventId <= 0){
		return;
	}
	
	$.ajax({ 
		type: 'GET', 
    	url: '/api/private/getSelectedOptionsByEventId', 
    	data: {
			eventId: eventId
		},
    	async: false,
		success: function(json) {
			
			// Iterate all selected choices.
			jQuery.each(json, function(id, value) {
				$('.property-select-box option[value='+value+']').attr('selected','selected');
			})
        },
        
        // Popup error info when it happens
    	error: function(err) {   		
			dangerPopup("Failed to get selected options due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
}

/**
 * Convert the duration in minutes to _d __h __m format.
 * @param {int} minutes 
 * @returns The converted string.
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
 * Convert duration string (_d __h __m format) back to minutes
 * @param {string} durationStr 
 * @returns The duration in minutes, or null if the input is not well-formed.
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
 * Calculate the real-time duration when clicking the event date/time inputs.
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
 * Renders the approval status as an HTML span element with a specific badge class based on the data provided.
 * 
 * @param {boolean|null|undefined} data - The data indicating the approval status, which can be true (approved), 
 * 										  false (declined), or null/undefined (pending).
 * @param {*} type - (not used in the current implementation).
 * @param {*} row -  (not used in the current implementation).
 * @returns {string} HTML string representing a span element with a class and text that reflects the approval status: 
 * 										  'Pending', 'Approved', or 'Declined'.
 */

function approvalStatusRender(data, type, row){

	// null -> Pending
	if(data == undefined || data == null){
		return '<span class="badge badge-warning">Pending</span>';
	}

	// true -> Approved
	else if(data){
		return '<span class="badge badge-success">Approved</span>';
	}

	// false -> Declined
	else{
		return '<span class="badge badge-danger">Declined</span>';
	}
}

function assetRender(data, type, row){
	return (data == undefined || data == null) ? 'N/A' : `<img src="/assets/content.do?assetId=${data}" class="table-img">`;
}

function dateWeekRender(data, type, row){
	var targetDate = Date.parse(data);
	return targetDate.toString('yyyy-MM-dd HH:mm');
}