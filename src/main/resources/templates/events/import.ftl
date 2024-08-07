<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "EVENTS_IMPORT">

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
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.EVENTS_CALENDAR}">Calendar</a>&nbsp|&nbsp
									<a href="${ENDPOINTS.EVENTS_LIST}">List</a>
								</li>
	              				<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.EVENTS_IMPORT}" id="eventPageLink">Import</a>
								</li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">

					<#-- Introduction -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-compass"></i>&nbsp;Introduction
							</h3>
						</div>

						<div class="card-body">

							<#-- Steps -->
							<ul>
								<li><b>Step 1:</b> Create a new workbook by using a <b>spreadsheet editor</b>.</li>
								<li><b>Step 2:</b> Copy the following table header into the <b>first row</b>.</li>
								<li><b>Step 3:</b> Ensure the <b>first column</b> is not blank, and it has 6 columns. <b></b></li>
								<li><b>Step 4:</b> Save the file as <b>Comma-separated values (*.csv)</b> format.</li>
								<li><b>Step 5:</b> Add event information, and save the file.</li>
							</ul>
							<#-- /Steps -->

							<div class="table-responsive mb-3">
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

										<#-- Accepted format -->
										<tr>
											<th scope="col">Accepted format</th>
											<td>Up to 63 characters</td>
											<td>Up to 63 characters</td>
											<td>Up to 65,535 characters</td>
											<td>Any characters</td>
											<td>yyyy-MM-dd HH:mm</td>
											<td>yyyy-MM-dd HH:mm</td>
										</tr>
										<#-- /Accepted format -->

										<#-- Required -->
										<tr>
											<th scope="col">Required</th>
											<td><i class="fa-solid fa-check"></i></td>
											<td><i class="fa-solid fa-xmark"></i></td>
											<td><i class="fa-solid fa-xmark"></i></td>
											<td><i class="fa-solid fa-check"></i></td>
											<td><i class="fa-solid fa-check"></i></td>
											<td><i class="fa-solid fa-check"></i></td>
										</tr>
										<#-- /Required -->

										<#-- Description -->
										<tr>
											<th scope="col">Description</th>
											<td>N/A</td>
											<td>N/A</td>
											<td>N/A</td>
											<td><b>True</b> or <b>False</b> (case insensitive).</td>
											<td>If AllDayEvent is true, only the <b>date part</b> is required.</td>
											<td>If AllDayEvent is true, only the <b>date part</b> is required. It must after the StartDateTime.</td>
										</tr>
										<#-- /Description -->

										<#-- Examples -->
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
										<#-- /Examples -->

									</tbody>
								</table>
							</div>
						</div>		

					</div>
					<#-- /Introduction -->

					<div class="row">

						<#-- Upload -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-file-import"></i>&nbsp;Import
									</h3>
								</div>

								<div class="card-body">

									<#-- File -->
									<div class="form-group row">

										<label for="uploaderRole" class="col-sm-2 col-form-label">
											File&nbsp;<span class="required-symbol">*</span>
										</label>
												
										<#-- Upload File -->
										<div class="col-sm-10">
											<div class="input-group mb-3">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-file-arrow-up"></i>
													</span>
												</div>	

													
												<#-- Upload box -->
												<div class="custom-file">
													<input type="file" class="custom-file-input" id="fileUpload" accept=".csv">
													<label class="custom-file-label" for="exampleInputFile">Choose file</label>
												</div>
												<#-- /Upload box -->	

												<#-- Upload button -->
												<div class="input-group-append">
													<button type="button" class="btn btn-outline-primary" onclick="importEvents();" id="uploadFileBtn">
														<i class="fa-solid fa-upload"></i>&nbsp;Upload
													</button>
												</div>		
												<#-- /Upload button -->					

											</div>
											
											
											<small class="form-text text-muted mb-3">Only<b>Comma-separated values (*.csv)</b> format id accepted.
											</small>
											<#-- /Upload File -->

											<div class="callout callout-primary">
												<h5 class="bold-text">Using online editors?</h5>
												<p>You may need to download a copy first.</p>
											</div>
										</div>
									</div>
									<#-- /File -->

								</div>
							</div>
						</div>
						<#-- /Upload -->

						<#-- Result -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-circle-check"></i>&nbsp;Result
									</h3>
								</div>

								<div class="card-body table-responsive">
									<table id="importResult" class="table table-striped table-hover">

										<thead>
										<tr>
											<th>Row Number</th>
											<th>Status</th>
										</tr>
										</thead>

										<tbody>
										</tbody>
									</table>
								</div>

							</div>
						</div>
						<#-- /Result -->

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

	<script src="/static/js/etwig/import-events.js?ver=${app.appVersion}"></script>
</body>
</html>