<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developters [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, post JavaScript part.
	This part contains the JavaScript after the form. Commonly the initializaion of some libraries.
   -->
   
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