<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "CALENDAR">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Import Events - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">

		<#include "../_includes/navbar.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">
			
			<#-- Page header -->
	    	<section class="content-header">
	      		<div class="container-fluid">
	        		<div class="row mb-2">
	          			<div class="col-sm-6">
	            			<h1 class="bold-text">Import Events</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/events/calendar">Events</a></li>
	              				<li class="breadcrumb-item active"><a href="#" id="eventPageLink">Import</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">

					<div class="card card-primary card-outline">
						<div class="card-header d-flex p-0">

							<#-- Tabs -->
							<ul class="nav nav-pills p-2">

								<#-- Back -->							
								<li class="nav-item">
									<a class="nav-link" href="/events/calendar">
										<i class="fa-solid fa-arrow-left"></i>&nbsp;Back
									</a>
								</li>
								<#-- /Back -->	

								<#-- View/Edit -->	
								<li class="nav-item">
									<a class="nav-link active" href="#" id="eventEditLink">
										<i class="fa-solid fa-pen-to-square"></i>&nbsp;Edit
									</a>
								</li>
								<#-- /View/Edit -->	

								<#-- Graphics Request -->	
								<li class="nav-item event-hidden-tabs">
									<a class="nav-link" href="#" id="eventGraphicsLink">
										<i class="fa-solid fa-image"></i>&nbsp;Graphics
									</a>
								</li>
								<#-- /Graphics Request -->	

								<#-- Copy -->	
								<li class="nav-item event-hidden-tabs">
									<a class="nav-link" href="#" id="eventCopyLink">
										<i class="fa-solid fa-copy"></i>&nbsp;Copy
									</a>
								</li>
								<#-- /Copy -->	

							</ul>
							<#-- /Tabs -->

						</div>

						<#-- Edit area -->
						<div class="card-body">

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
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 1: Basic Info -->

									<#-- Content 2: Timing -->
									<#assign prev = true>
									<#assign next = true>
									<div id="eventTiming" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger2">
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 2: Timing -->

									<#-- Content 3: Additional Info -->
									<#assign prev = true>
									<#assign next = false>
									<div id="eventAdditionalInfo" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="eventSteppertrigger3">
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 3: Additional Info -->

								</div>
								<#-- /Content -->

							</div>
							<#-- /Stepper -->

						</div>
						<#-- /Edit area -->

					</div>
				</div>
			</section>
			<#-- /Main area -->
            
		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
</body>
</html>