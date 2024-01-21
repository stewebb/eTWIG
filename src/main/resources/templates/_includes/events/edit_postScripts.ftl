<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, properties part.
	This part contains the form of timing, including event start/end time and duration.
   -->
<#-- Resources -->
   	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/vendor/summernote-bs4.min.css">
	<script src="/static/js/vendor/summernote-bs4.min.js"></script>
	
	<#-- bs stepper -->
	<link rel="stylesheet" href="/static/css/vendor/bs-stepper.min.css">
	<script src="/static/js/vendor/bs-stepper.min.js"></script>

	<#-- jQuery inputmask -->
   	<script src="/static/js/vendor/jquery.inputmask.min.js"></script>

	<#-- rrule.js -->
   	<script type="module" src="/static/js/etwig/recurrent.min.js"></script>

	<#-- Custom JS for adding/editing events-->
	<script src="/static/js/etwig/events.js"></script>
	
   	<#-- Post Scripts -->
	<script>

		$(document).ready(function() {
			
			//var myInstance = new ETwig.EtwigRRule("RRULE:FREQ=MONTHLYCOUNT=5");
			//var a = myInstance.getRuleObj();
			//console.log(a);


			// Initialize the description box
			initDescriptionBox('#eventDescription');

			// Date and time inputs.
    		var datePickersMap = createDatePickers();
			$('.event-time').inputmask('99:99');

			// Get event info and display it.
			getEventInfo(datePickersMap);

			
			$('input[type=radio][name=event-recurrent]').change(function() {
				setRecurrentMode(this.value);
			});

			$('#eventAllDayEvent').change(function() {
				setAllDayEvent(this.checked); 
    		});
			$('#eventValidToDateEnabled').change(function(){
				setValidTo(this.checked);
			})
			
			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			// Initialize the template stepper
     		eventStepper = new Stepper(document.querySelector('#eventStepper'), {
    			linear: false
  			});
		});
		
    </script>