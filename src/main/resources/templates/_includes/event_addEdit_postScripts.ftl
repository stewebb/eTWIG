	<#-- Post Scripts -->
	<script>
		initDescriptionBox('#eventDescription');
		startTimePicker = createDatePicker("#eventStartWrapper", "#eventStartTime", "date", "yyyy-MM-dd HH:mm A", true);
		
      	timeUnitBtnOnChange();      	
      	
      	$('#event-organizer').select2({
    		theme: 'bootstrap4',
		});
		
      	$('#event-portfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
		
		//$("form").validate();
		//$("#add-event").click(addEvent); 
		//$("#add-event-exit").click(addEventAndExit); 
    </script>