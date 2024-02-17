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

								<#-- Import -->	
								<li class="nav-item">
									<a class="nav-link active" href="/events/import"">
										<i class="fa-solid fa-file-import"></i>&nbsp;Import
									</a>
								</li>
								<#-- /Import -->	

							</ul>
							<#-- /Tabs -->

						</div>

						<#-- Edit area -->
						<div class="card-body">

							<#-- Stepper -->
							<div id="eventStepper" class="bs-stepper bg-white">

								<#-- Header -->
								<div class="bs-stepper-header" role="tablist">

									<#-- Header 1: Copy Template -->
									<div class="step" data-target="#importCopyTemplate">
										<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="importCopyTemplate">
											<span class="bs-stepper-circle">
												<span class="fas fa-copy" aria-hidden="true"></span>
											</span>
											<span class="bs-stepper-label">Copy Template</span>
										</button>
									</div>
									<#-- /Header 1: Copy Template -->

									<#-- Header 2: Upload -->
									<div class="bs-stepper-line"></div>
									<div class="step" data-target="#importUpload">
										<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger2" aria-controls="importUpload">
											<span class="bs-stepper-circle">
												<span class="fas fa-cloud-arrow-up" aria-hidden="true"></span>
											</span>
											<span class="bs-stepper-label">Upload</span>
										</button>
									</div>
									<#-- /Header 2: Upload -->

									<#-- Header 3: Additional Info -->
									<div class="bs-stepper-line"></div>
									<div class="step" data-target="#importCheck">
										<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="importCheck">
											<span class="bs-stepper-circle">
												<span class="fas fa-check" aria-hidden="true"></span>
											</span>
											<span class="bs-stepper-label">Check</span>
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
											
									<div id="importCopyTemplate" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger1">
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 1: Basic Info -->

									<#-- Content 2: Timing -->
									<#assign prev = true>
									<#assign next = true>
									<div id="importUpload" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger2">
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 2: Timing -->

									<#-- Content 3: Check -->
									<#assign prev = true>
									<#assign next = false>
									<div id="importCheck" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="eventSteppertrigger3">
										
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- /Content 3: Check -->

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

	<#-- bs stepper -->
	<link rel="stylesheet" href="/static/css/vendor/bs-stepper.min.css">
	<script src="/static/js/vendor/bs-stepper.min.js"></script>

	<#-- Post Scripts -->
	<script>

		$(document).ready(function() {

			// Initialize the stepper
     		eventStepper = new Stepper(document.querySelector('#eventStepper'), {
    			linear: true
  			});

		});
		
    </script>
</body>
</html>