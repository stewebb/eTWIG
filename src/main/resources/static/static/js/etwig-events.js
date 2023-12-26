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
  		
  	return $(`<span style="color: ${color}"><i class="fa-solid fa-${icon}"></i>${state.text}</span>`);
};

function addEvent(){
	
	// Event name: Required
	var eventName = $.trim($('#eventName').val());
	if(eventName.length == 0){
		invalidFormToast("Event name is required.");
		return 1;
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
		invalidFormToast("Event start time is required");
		return 1;
	}
	
	if(parsedStartTime == null){
		invalidFormToast("Event start time is not well-formed.");
		return 1;
	}
	
	// Event duration: Required only if the eventTimeUnit is not "customize""
	var eventDuration = $('#eventDuration').val();
	
	// Event endTime: Required only if the eventTimeUnit is "customize""
	var eventEndTime = $('#eventEndTime').val();
	var parsedEndTime = Date.parse(eventEndTime);
	
	// Check "Duration" / "End Time" by selected time units.
	if(eventTimeUnit == "customize"){
		
		if(eventEndTime.length == 0){
			invalidFormToast("Event end time is required");
			return 1;
		}
		
		if(parsedEndTime == null){
			invalidFormToast("Event end time is not well-formed.");
			return 1;
		}
		
		if(parsedEndTime.compareTo(parsedStartTime) <= 0){
			invalidFormToast("Event end time must after start time.");
			return 1;
		}
	}
	
	else{
		if(eventDuration.length == 0){
			invalidFormToast("Event duration is required, and it must be a number.");
			return 1;
		}
		
		if(eventDuration <= 0){
			invalidFormToast("Event duration must be a positive number.");
			return 1;
		}
	}
	
	// Event portfolio: Required
	var eventPortfolio = $('#eventPortfolio').find(":selected").val();
	
	// Event Organizer: Required
	var eventOrganizer = $('#eventOrganizer').find(":selected").val();
	alert(eventOrganizer)
	
	return 0;
}

function addEventAndExit(){
	var status = addEvent();
	//parent.$('#etwigModal').modal('hide');
}

function addEventOnly(){
	var status = addEvent();
	//parent.$('#etwigModal').modal('hide');
}

function invalidFormToast(reason){
	$(document).Toasts('create', {
  		title: reason,
  		//position: position,
  		autohide: true,
  		delay: 5000,
  		icon: 'fa fa-circle-exclamation',
  		class: 'toast bg-warning'
	});
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
		$('#unitText').text(this.value + "(s)");
		
		if(this.value == "customize"){
			$("#durationInput").hide();
			$("#endTimeInput").show();
		}else{
			$("#durationInput").show();
			$("#endTimeInput").hide();
		}
		
		
		switch (this.value){
			case "hour":
				startTimePicker.setType("date");
				startTimePicker.getTimePicker().show();
				break;
			case "month":
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
          //layoutType: 'tab',
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