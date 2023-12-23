<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- CSS and JS for summernote editor.-->
	<link href="/static/css/summernote-bs4.min.css" rel="stylesheet">
	<script src="/static/js/summernote-bs4.min.js"></script>
	 
	<title>Edit Event - eTWIG Administration Portal</title>
</head>

<body>
	<#include "../_includes/sidebar.ftl">
	
	<#-- Content Wrapper -->
  	<div class="content-wrapper">
  	
    	<#-- Page header -->
    	<section class="content-header">
      		<div class="container-fluid">
        		<div class="row mb-2">
          			<div class="col-sm-6">
            			<h1 class="bold-text">
            				<#if mode=="ADD">Add<#else>Edit</#if> Event
            			</h1>
          			</div>
          			<div class="col-sm-6">
            			<ol class="breadcrumb float-sm-right">
              				<li class="breadcrumb-item"><a href="/">Home</a></li>
              				<li class="breadcrumb-item">Events</li>
              				<#if mode=="ADD">
              					<li class="breadcrumb-item active"><a href="/events/edit?eventId=-1">Add Event</a></li>
              				<#else>
              					<li class="breadcrumb-item active"><a href="/events/edit?eventId=${eventId}">Edit Event</a></li>
              				</#if>
            			</ol>
          			</div>
        		</div>
      		</div>
    	</section>
    	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-6">
						
						<#-- Basic Information -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-circle-info"></i>&nbsp;Basic Information
								</h3>
							</div>
							
							<div class="card-body">
								
								<#-- Name -->
								<div class="form-group row">
									<label for="event-name" class="col-sm-2 col-form-label">Name</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-lightbulb"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Name" id="event-name">
										</div>
									</div>
								</div>
								
								<#-- Location -->
								<div class="form-group row">
									<label for="event-location" class="col-sm-2 col-form-label">Location</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-location-dot"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Location" id="event-location">
										</div>
									</div>
								</div>
								
								<#-- Recurrent -->
								<div class="form-group row">
									<label for="event-recurrent" class="col-sm-2 col-form-label">Recurrent</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
											<div class="icheck-primary">
												<input type="radio" id="single-time-event" name="event-recurrent" checked="" <#if mode=="EDIT">disabled=""</#if>>
												<label for="single-time-event">Single Time</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="recurring-event" name="event-recurrent" <#if mode=="EDIT">disabled=""</#if>>
												<label for="recurring-event">Recurring</label>
											</div>
										</div>
										<#if mode=="ADD">
											<div class="callout callout-info">
												<h5 class="bold-text mb-3">Be Careful!</h5>
												Once you set the recurrent option, it cannot be changed unless you delete the event completely, and add a new event.
											</div>
										<#else>
											<div class="callout callout-warning">
												<h5 class="bold-text mb-3">Recurrent Option Disabled</h5>
												You cannot change the recurrent option for an existing event. If you want to do so, please delate the event and create a new event.
											</div>
										</#if>
									</div>
								</div>
								
								<!--
								<#-- Start Time -->
								<div class="form-group row">
									<label for="event-start-time" class="col-sm-2 col-form-label">Start Time</label>
									<div class="col-sm-10">
										<div class="input-group mb-3">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-clock"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="event-start-time">
										</div>
									</div>
								</div>
								
								<#-- Event Type -->
								<div class="form-group row">
									<label for="event-type" class="col-sm-2 col-form-label">Start Time</label>
									<div class="col-sm-10">
										<div class="input-group mb-3">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-clock"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="event-start-time">
										</div>
									</div>
								</div>
								
								
								<#-- Select Type -->
										<div class="form-group clearfix">
											<div class="icheck-primary d-inline">
												<input type="radio" id="normal-event" name="event-type" checked="">
												<label for="normal-event">Normal</label>
											</div>
											<div class="icheck-primary d-inline">
												<input type="radio" id="all-day-event" name="event-type">
												<label for="all-day-event">All day</label>
											</div>
											<div class="icheck-primary d-inline">
												<input type="radio" id="all-week-event" name="event-type">
												<label for="all-week-event">All week</label>
											</div>
											<div class="icheck-primary d-inline">
												<input type="radio" id="all-month-event" name="event-type">
												<label for="all-month-event">All month</label>
											</div>
										</div>
								
								
								<#-- End Time -->
								<div class="form-group row">
									<label for="event-end-time" class="col-sm-2 col-form-label">End Time</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-clock"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="event-end-time">
										</div>
									</div>
								</div>
								-->
							</div>
						</div>
					</div>
					
					<div class="col-md-6">
						
						<#-- Description -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-pen"></i>&nbsp;Description
								</h3>
							</div>

							<div class="card-body">
								<div id="summernote"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<script>
		$('#summernote').summernote({
			placeholder: 'Hello Bootstrap 4',
        	tabsize: 4,
        	height: 100
      	});
    </script>
</body>
</html>