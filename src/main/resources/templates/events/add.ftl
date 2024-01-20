<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The add event page.
   -->
   
<#assign navbar = "CALENDAR">

<#-- Add mode. -->
<#assign isEdit = false>
<#assign isEditStr = "false">

<#-- In add mode, inputs are also enabled, -->
<#assign disabled = false>
<#assign disabledStr ="">

<#-- In add mode, mode is always called "Add". -->
<#assign modeStr = "Add">

<#-- In add mode, the link of this page is always "/event/add". -->
<#assign link = "add">

<#-- In add mode, the default time unit is always h (hour) and the default duration is always 1.  -->
<#assign timeUnit = "h">
<#assign duration = 1>

<#-- In add mode, the duration input will never been hidden.  -->
<#assign durationHidden = false>

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Add Event - ${app.appName}</title>
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
    		<#include "../_includes/events/addEdit_header.ftl">
    		<#-- /Page header -->
    	
    		<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
				
					<#-- Stepper -->
					<div id="eventStepper" class="bs-stepper bg-white">

						<#-- Header -->
          				<div class="bs-stepper-header" role="tablist">

							<#-- Header 1: Basic Info -->
            				<div class="step" data-target="#eventBasicInfo">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="eventBasicInfo">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-circle-info" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Basic Info</span>
              					</button>
            				</div>
							<#-- /Header 1: Basic Info -->

							<#-- Header 2: Timing -->
            				<div class="bs-stepper-line"></div>
            				<div class="step" data-target="#eventTiming">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger2" aria-controls="eventTiming">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-clock" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Timing</span>
              					</button>
            				</div>
							<#-- /Header 2: Timing -->

							<#-- Header 3: Additional Info -->
							<div class="bs-stepper-line"></div>
            				<div class="step" data-target="#eventAdditionalInfo">
              					<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="eventAdditionalInfo">
                					<span class="bs-stepper-circle">
                  						<span class="fas fa-map-pin" aria-hidden="true"></span>
                					</span>
                					<span class="bs-stepper-label">Additional Info</span>
              					</button>
            				</div>
							<#-- /Header 3: Additional Info -->

          				</div>
						<#-- /Header -->

						<#-- Content -->
          				<div class="bs-stepper-content">

							<#-- Content 1: Basic Info -->
							<#assign prev = false>
            				<#assign next = true>
            						
              				<div id="eventBasicInfo" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger1">
              					<#include "../_includes/events/addEdit_basicInfo.ftl">	
                				<#include "../_includes/events/stepper_btn.ftl">
              				</div>
							<#-- /Content 1: Basic Info -->

							<#-- Content 2: Timing -->
							<#assign prev = true>
            				<#assign next = true>
              				<div id="eventTiming" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger2">
                				<#include "../_includes/events/addEdit_timing.ftl">	
								<#include "../_includes/events/stepper_btn.ftl">
              				</div>
							<#-- /Content 2: Timing -->

							<#-- Content 3: Additional Info -->
							<#assign prev = true>
            				<#assign next = false>
			  				<div id="eventAdditionalInfo" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="eventSteppertrigger3">
                				<#include "../_includes/events/addEdit_additionalInfo.ftl">	
								<#include "../_includes/events/stepper_btn.ftl">
              				</div>
            				<#-- /Content 3: Additional Info -->

          				</div>
						<#-- Content -->

        			</div>
					<#-- Stepper -->

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
	
	<#-- Post Scripts -->
	<#include "../_includes/events/addEdit_postScripts.ftl">
</body>
</html>