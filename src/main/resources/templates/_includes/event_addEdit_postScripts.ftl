	<#-- Post Scripts -->
	<script>
	
		// Initialize the description box
		initDescriptionBox('#eventDescription');
		
		// Time pickers for both start and end time
		startTimePicker = createDatePicker("#eventStartWrapper", "#eventStartTime", "date", "yyyy-MM-dd HH:mm A", true);
		endTimePicker = createDatePicker("#eventEndWrapper", "#eventEndTime", "date", "yyyy-MM-dd HH:mm A", true);
		
		// Register those time unit buttons
      	timeUnitBtnOnChange(startTimePicker);      	
      	
      	$('#eventOrganizer').select2({
    		theme: 'bootstrap4',
		});
		
      	$('#eventPortfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
    </script>