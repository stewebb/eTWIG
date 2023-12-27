/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developters [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: Add, edit and delete events.
 */

function formatState(state) {
	var option = $(state.element);
  	var color = option.data("color");
  	var icon = option.data("icon");
  	
  	if (!color) {
    	 return state.text;
  	}
  	
  	if(!icon){
		icon = 'square';
	}
  		
  	return $(`<span style="color: ${color};background-color: #FFF">&nbsp;<i class="fa-solid fa-${icon}"></i>${state.text}&nbsp;</span>`);
};

/**
 * Validate the add event form. If passed, add the event.
 * @param embedded True if the event add page is in the "embed" mode, i.e., this page is in the frame of the calendar. 
 * 					The modal should be closed after operation.
 * 					False otherwise. The page should refresh after operation.
 */

function addEvent(embedded){
	
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

	// Create an object for the new event.
	var newEventObj = {
		"isRecurrment": false,
		"name": eventName,
		"location": eventLocation,
		"description": eventDescription,
		"timeUnit": eventTimeUnit,
		"startTime": eventStartTime,
		"duration": realDuration,
		"portfolio": eventPortfolio,
		"organizer": eventOrganizer
	};
	
	// POST the new event into the event add API
	var hasError = true;
	$.ajax({
   		url: "/api/public/addEvent", 
   		type: "POST",
   		async: false,
   		dataType: "json",
   		contentType: "application/json; charset=utf-8",
   		data: JSON.stringify(newEventObj),
   		success: function (result) {
			if(result.error > 0){
				dangerToast("Failed to add event.", result.msg);
				hasError = true;
			}else{
				successToast("Event added successfully.");
				hasError = false;
			}	
    	},
    	error: function (err) {
    		dangerToast("Failed to add event due to a HTTP " + err.status + " error.", err.responseJSON.exception);
    		hasError = true;
    	}
 	});

	// Post-add operations
	setTimeout(
		function() {
			embedded ? parent.$('#etwigModal').modal('hide') : window.location.reload();
		}, 
		hasError ? 5000 : 2000
	);
}

function initDescriptionBox(boxElem){
	$(boxElem).summernote({
		placeholder: 'Event description',
        tabsize: 4,
        height: 500,
        minHeight: 500,
  		maxHeight: 800,
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

function timeUnitBtnOnChange(startTimePicker){
	$('input[type=radio][name=eventTimeUnit]').change(function() {
		
		//alert(this.value);
		$('#unitText').text(this.value);
		
		if(this.value == "c"){
			$("#durationInput").hide();
			$("#endTimeInput").show();
		}else{
			$("#durationInput").show();
			$("#endTimeInput").hide();
		}
		
		
		switch (this.value){
			case "h":
			case "c":
				startTimePicker.setType("date");
				startTimePicker.getTimePicker().show();
				break;
			case "m":
				startTimePicker.setType("month");
				startTimePicker.getTimePicker().setTime(0, 0);
				startTimePicker.getTimePicker().hide();
				break;
			default:
				startTimePicker.setType("date");
				startTimePicker.getTimePicker().setTime(0, 0);
				startTimePicker.getTimePicker().hide();
				break;		
		}
	});

}


function createDatePicker(htmlElem, pickerElem, type, format, timePicker){
	
	var t = false;
	if(timePicker){
		t = {
          inputType: 'spinbox'
        }
	}
	 
	var datepicker = new tui.DatePicker(htmlElem, {
		date: Date.today(),
		type: type,
		input: {
			element: pickerElem,
			format: format,
			usageStatistics: false
		},
		timePicker: t,
	});
	return datepicker;
}