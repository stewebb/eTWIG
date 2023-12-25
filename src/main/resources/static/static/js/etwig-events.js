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
	//var isFormValid = true;
	
	// Event name: Reauired
	var eventName = $.trim($('#eventName').val());
	if(eventName.length == 0){
		invalidFormToast("Event name is required.");
		return;
	}
	
	// Event location: Optional
	var eventLocation = $.trim($('#eventLocation').val());
	
	// Event description: Optional
	var eventDescription = $("#eventDescription").summernote("code");
	//alert(eventDescription);
}

function addEventAndExit(){
	addEvent();
	// TODO BACK TO LAST PAGE
}

function invalidFormToast(reason){
	$(document).Toasts('create', {
  		title: reason,
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

function timeUnitBtnOnChange(){
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
				//$('#unitText').text("Hour(s)");
				break;
			case "day":
				//$('#unitText').text("Day(s)");
				break;
			case "week":
				//$('#unitText').text("(s)");
				break;
			case "month":
				//$('#unitText').text("(s)");
				break;
			default:
				//$('#unitText').text("(s)");
				break;
				
		}
		//alert(this.value);
    //if (this.value == 'allot') {
        // ...
    //}
    //else if (this.value == 'transfer') {
        // ...
    //}
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