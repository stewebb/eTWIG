	<#-- Post Scripts -->
	<script>
		initDescriptionBox('#eventDescription');
		startTimePicker = createDatePicker("#eventStartWrapper", "#eventStartTime", "date", "yyyy-MM-dd HH:mm A", true);
		endTimePicker = createDatePicker("#eventEndWrapper", "#eventEndTime", "date", "yyyy-MM-dd HH:mm A", true);
		
      	timeUnitBtnOnChange(startTimePicker);      	
      	
      	$('#eventOrganizer').select2({
    		theme: 'bootstrap4',
		});
		
      	$('#eventPortfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
		
		//$("form").validate();
		//$("#add-event").click(addEvent); 
		//$("#add-event-exit").click(addEventAndExit); 
    </script>