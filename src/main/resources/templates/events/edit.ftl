<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The add/edit event page.
   -->
   
<#assign navbar = "CALENDAR">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Event Management - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	<#include "../_includes/header/body_start.ftl">

	<#-- Main Wrapper -->
	<div class="wrapper">

		<#-- Navbar -->
		<#include "../_includes/navbar.ftl">
		<#-- /Navbar -->

		<#-- Content Wrapper. -->
  		<div class="content-wrapper">
  	
			<#-- Page header -->
	    	<section class="content-header">
	      		<div class="container-fluid">
	        		<div class="row mb-2">
	          			<div class="col-sm-6">
	            			<h1 class="bold-text" id="eventPageTitle"></h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/events/calendar.do">Events</a></li>
	              				<li class="breadcrumb-item active"><a id="eventPageLink" href="#"></a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->
    	
    		<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">

					<#-- Basic Information -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-circle-info"></i>&nbsp;Basic Information
							</h3>
						</div>

						<div class="card-body">
							<#include "../_includes/events/addEdit_basicInfo.ftl">	
						</div>

					</div>
					<#-- /Basic Information -->

					<#-- Timing -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-clock"></i>&nbsp;Timing
							</h3>
						</div>

						<div class="card-body">
							<#include "../_includes/events/addEdit_timing.ftl">	
						</div>

					</div>
					<#-- /Timing -->

					<#-- Additional Information -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-map-pin"></i>&nbsp;Additional Information
							</h3>
						</div>

						<div class="card-body">
							<#include "../_includes/events/addEdit_additionalInfo.ftl">	

							<button type="button" class="btn btn-outline-primary right-div" onclick="addEvent();">
                				<i class="fa-solid fa-check"></i>&nbsp;Submit
                			</button>
						</div>

					</div>
					<#-- /Additional Information -->

					
				</div>
			</section>
			<#-- /Main area -->
		
		</div>
		<#-- /Content Wrapper -->

	</div>
	<#-- Main Wrapper -->
	
	<#-- Footer -->
	<#include "../_includes/footer.ftl">
	
	<#include "../_includes/header/body_end.ftl">
	
	<#-- Resources -->
   	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/vendor/summernote-bs4.min.css">
	<script src="/static/js/vendor/summernote-bs4.min.js"></script>
	
	<#-- bs stepper -->
	<#-- PENDING REMOVAL
	<link rel="stylesheet" href="/static/css/vendor/bs-stepper.min.css">
	<script src="/static/js/vendor/bs-stepper.min.js"></script>
	-->

	<#-- jQuery inputmask -->
   	<script src="/static/js/vendor/jquery.inputmask.min.js"></script>

	<#-- rrule.js -->
   	<script type="module" src="/static/js/etwig/recurrent.min.js?ver=${app.appVersion}"></script>

	<#-- Custom JS for adding/editing events-->
	<script src="/static/js/etwig/events.js?ver=${app.appVersion}"></script>
	
   	<#-- Post Scripts -->
	<script>

		$(document).ready(function() {

			// Date and time inputs.
    		var datePickersMap = createDatePickers();

			// Input masks
			$('.event-time').inputmask('99:99');
			$('#eventDuration').inputmask('9d 99h 99m');

			// Get event info and display it.
			getEventInfo(datePickersMap);

			// Calculate duration
			$(".event-date-time").blur(function () {
				calculateDuration();
			});

			$('input[type=radio][name=event-recurrent]').change(function() {
				setRecurrentMode(this.value);
			});

			$('#eventAllDayEvent').change(function() {
				setAllDayEvent(this.checked); 
    		});
			
			$('#eventValidToDateEnabled').change(function(){
				setValidTo(this.checked);
			})

			$('#eventRequestNow').change(function(){
				setGraphicsRequest(this.checked);
			})

			$('.event-rrule-options').change(function(){
				getRRuleByInput();
			})

			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			// Initialize the stepper
     		//eventStepper = new Stepper(document.querySelector('#eventStepper'), {
    		//	linear: false
  			//});

			// Initialize the description box
			initDescriptionBox('#eventDescription');
		});
		
    </script>
</body>
</html>