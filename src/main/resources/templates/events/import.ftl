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

									<#-- Header 1: Create Template -->
									<div class="step" data-target="#importCopyTemplate">
										<button type="button" class="step-trigger" role="tab" id="eventSteppertrigger1" aria-controls="importCopyTemplate">
											<span class="bs-stepper-circle">
												<span class="fas fa-plus" aria-hidden="true"></span>
											</span>
											<span class="bs-stepper-label">Create Template</span>
										</button>
									</div>
									<#-- /Header 1: Create Template -->

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

									<#-- Content 1: Create Template -->
									<#assign prev = false>
									<#assign next = true>
											
									<div id="importCopyTemplate" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger1">
										
										<div class="container-fluid">
											<div class="row col-12">

												<#-- Steps -->
												<ul>
													<li><b>Step 1:</b> Create a new workbook by using a <b>spreadsheet editor</b>.</li>
													<li><b>Step 2:</b> Copy the following table header into the <b>first row</b>.</li>
													<li><b>Step 3:</b> Ensure the <b>first column</b> is not blank, and it has 6 columns. <b></b></li>
													<li><b>Step 4:</b> Save the file as <b>Microsoft Excel Spreadsheet (*.xlsx)</b> or <b>OpenDocument Spreadsheet (*.ods)</b> format.</li>
													<li><b>Step 5:</b> Add event information, and save the file.</li>
												</ul>
												<#-- /Steps -->

												<#-- Notice -->
												<#--
												<blockquote>
												<p>Impoerant Notice</p>
												Please make sure that this workbook contains <b>no commas</b> 
												</blockquote>
												-->
												<#-- /Notice -->

												<div class="table-responsive">
													<table class="table table-head-fixed table-striped table-hover">
														
														<thead class="table-primary">
															<tr>
																<th></th>
																<th>Name</th>
																<th>Location</th>
																<th>Description</th>
																<th>AllDayEvent</th>
																<th>StartDateTime</th>
																<th>EndDateTime</th>
															</tr>
														</thead>

														<tbody>

															<tr>
																<th scope="col">Accepted format</th>
																<td>Up to 63 characters</td>
																<td>Up to 63 characters</td>
																<td>Up to 65,535 characters</td>
																<td>Any characters</td>
																<td>yyyy-MM-dd HH:mm</td>
																<td>yyyy-MM-dd HH:mm</td>
															</tr>

															<tr>
																<th scope="col">Required</th>
																<td><i class="fa-solid fa-check"></i></td>
																<td><i class="fa-solid fa-xmark"></i></td>
																<td><i class="fa-solid fa-xmark"></i></td>
																<td><i class="fa-solid fa-check"></i></td>
																<td><i class="fa-solid fa-check"></i></td>
																<td><i class="fa-solid fa-check"></i></td>
															</tr>

															<tr>
																<th scope="col">Description</th>
																<td>N/A</td>
																<td>N/A</td>
																<td>N/A</td>
																<td>
																	<b>True:</b> 'true', 'on', 'y', 't' or 'yes' (case insensitive).<br />
																	<b>False:</b> Any other contents.
																</td>
																<td>If AllDayEvent is true, only the <b>date part</b> is required.</td>
																<td>If AllDayEvent is true, only the <b>date part</b> is required. It must after the StartDateTime.</td>
															</tr>

															<tr>
																<th scope="col">Example 1</th>
																<td>My test event</td>
																<td>A test place</td>
																<td>Some description</td>
																<td>Yes</td>
																<td>2020-01-01</td>
																<td>2020-01-02</td>
															</tr>

															<tr>
																<th scope="col">Example 2</th>
																<td>Another test event</td>
																<td>Another test place</td>
																<td>More description</td>
																<td>No</td>
																<td>2020-01-01 08:00:00</td>
																<td>2020-01-01 10:00:00</td>
															</tr>
														</tbody>
													</table>
												</div>

											</div>
										</div>
										<#include "../_includes/events/stepper_btn.ftl">
									</div>
									<#-- Content 1: Create Template -->

									<#-- Content 2: Upload -->
									<#assign prev = true>
									<#assign next = true>
									<div id="importUpload" role="tabpanel" class="bs-stepper-pane" aria-labelledby="eventSteppertrigger2">
										
										<#-- Upload File -->
										<div class="input-group mb-3">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-file-arrow-up"></i>
												</span>
											</div>	

										
											<#-- File upload box -->
											<div class="custom-file">
												<input type="file" class="custom-file-input" id="fileUpload" accept=".xlsx, .ods">
												<label class="custom-file-label" for="exampleInputFile">Choose file</label>
											</div>
											<#-- /File upload box -->
											
											<div class="input-group-append">
											
												<#-- Upload file button -->
												<button type="button" class="btn btn-outline-primary" onclick="importEvents();" id="uploadFileBtn" disabled>
													<i class="fa-solid fa-upload"></i>
												</button>
												
												<#-- Reset file button -->
												<button type="button" class="btn btn-outline-secondary" onclick = "resetFile();">
													<i class="fa-solid fa-xmark"></i>
												</button>
											</div>

										</div>
										<#-- /Upload File -->

										<small class="form-text text-muted mb-3">Only the
											<b>Microsoft Excel Spreadsheet (*.xlsx)</b> and 
											<b>OpenDocument Spreadsheet (*.ods)</b> 
											format are accepted.
										</small>

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

	<script src="/static/js/etwig/import-events.js"></script>

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