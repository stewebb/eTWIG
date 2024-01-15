<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, post JavaScript part.
	This part contains the JavaScript after the form. Commonly the initialization of some libraries.
   -->
   
	<#-- Common resources -->
   	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/summernote-bs4.min.css">
	<script src="/static/js/summernote-bs4.min.js"></script>
	
	<#-- Custom JS for adding/editing events-->
	<script src="/static/js/etwig-events.js"></script>
	
   	<#-- Post Scripts -->
	<script>
	
		// Initialize the description box
		initDescriptionBox('#eventDescription');
		
		<#if disabled>
			$('#eventDescription').summernote('disable');
		</#if>
		
		// Time pickers for both start and end time
		startTimePicker = createDatePicker("#eventStartWrapper", "#eventStartTime", "date", "yyyy-MM-dd HH:mm A");
		endTimePicker = createDatePicker("#eventEndWrapper", "#eventEndTime", "date", "yyyy-MM-dd HH:mm A");
		
		<#if isEdit>
			startTimePicker.setDate(Date.parse("${eventDetails.details.startDateTime}"));
			endTimePicker.setDate(Date.parse("${eventDetails.details.endDateTime}"));
		</#if>
		
		// Register those time unit buttons
      	timeUnitBtnOnChange(startTimePicker);      	
      	
      	$('.common-select-box').select2({
    		theme: 'bootstrap4',
		});
		
      	$('#eventPortfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
    </script>