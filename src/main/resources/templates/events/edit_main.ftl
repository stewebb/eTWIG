<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/summernote-bs4.min.css">
	<script src="/static/js/summernote-bs4.min.js"></script>
	 
	<#-- CSS and JS for select 2.-->
	<link rel="stylesheet" href="/static/css/select2.min.css"/>
	<link rel="stylesheet" href="/static/css/select2-bootstrap4.min.css"/>
	<script src="/static/js/select2.min.js"></script>

	<title>Edit Event - eTWIG Administration Portal</title>
</head>

<body class="sidebar-mini layout-fixed">
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
											<input type="text" class="form-control" placeholder="Event Name" id="event-name" value="<#if eventDetails?has_content>${eventDetails.detail.eventName}</#if>">
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
											<input type="text" class="form-control" placeholder="Event Location" id="event-location" value="<#if eventDetails?has_content>${eventDetails.detail.eventLocation}</#if>">
										</div>
									</div>
								</div>
								
								<#assign disabled = (mode == "EDIT")>
								<#assign recurring_checked = (disabled && eventDetails.isRecurring)>

								<#-- Recurrent -->
								<div class="form-group row">
									<label for="event-recurrent" class="col-sm-2 col-form-label">Recurrent</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
											<div class="icheck-primary">
												<input type="radio" id="single-time-event" name="event-recurrent" <#if !recurring_checked>checked</#if> <#if disabled>disabled</#if>>
												<label for="single-time-event">Single Time</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="recurring-event" name="event-recurrent" <#if recurring_checked>checked</#if> <#if disabled>disabled</#if>>
												<label for="recurring-event">Recurring</label>
											</div>
										</div>
										
										<#if disabled>
											<div class="callout callout-warning">
												<h5 class="bold-text mb-3">Recurrent Option Disabled</h5>
												You cannot change the recurrent option for an existing event. If you want to do so, please delate the event and create a new event.
											</div>					
										<#else>
											<div class="callout callout-info">
												<h5 class="bold-text mb-3">Be Careful!</h5>
												Once you set the recurrent option, it cannot be changed unless you delete the event completely, then add a new event.
											</div>
										</#if>
									</div>
								</div>
							</div>
						</div>
						
						<#-- Timing: Single Time Event -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-clock"></i>&nbsp;Timing: Single Time Event
								</h3>
							</div>

							<div class="card-body">
								
								<#-- Time Unit-->
								<div class="form-group row">
									<label for="event-time-unit" class="col-sm-2 col-form-label">Time Unit</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
											<div class="icheck-primary">
												<input type="radio" id="hour" name="event-time-unit" checked="">
												<label for="hour">Hour</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="day" name="event-time-unit">
												<label for="day">Day [00:00-23:59]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="week" name="event-time-unit">
												<label for="week">Week [00:00 Mon-23:59 Sun]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="month" name="event-time-unit">
												<label for="month">Month [00:00 1st day-23:59 last day]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="customize" name="event-time-unit">
												<label for="customize">Customize</label>
											</div>
										</div>
									</div>
								</div>
								
								<#-- Start Time -->
								<div class="form-group row">
									<label for="event-start-time" class="col-sm-2 col-form-label">Start Time</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-start"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="event-start-time">
										</div>
									</div>
								</div>					
								
								<#-- Duration -->
								<div class="form-group row">
									<label for="event-duration" class="col-sm-2 col-form-label">Duration</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-half"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Duration" id="event-duration">
											<div class="input-group-append">
												<span class="input-group-text">Hours</span>
											</div>
										</div>
									</div>
								</div>
								
								<#-- End Time -->
								<div class="form-group row">
									<label for="event-end-time" class="col-sm-2 col-form-label">End Time</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-end"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event End Time" id="event-end-time">
										</div>
									</div>
								</div>			
								
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
								<div id="event-description"><#if eventDetails?has_content>${eventDetails.detail.eventDescription}</#if></div>
							</div>
						</div>
						
						<#-- Organizer -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-user-tie"></i>&nbsp;Organizer
								</h3>
							</div>

							<div class="card-body">
								
								<#-- Organizer -->
								<div class="form-group row">
									<label for="event-organizer" class="col-sm-2 col-form-label">Organizer</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-user"></i>
												</span>
											</div>
											
											<!--
											<input type="text" class="form-control" placeholder="Event Organizer" id="event-organizer">
											-->
											
											<select class="form-control select2" name="event-organizer">
        										<option data-select2-id="3">c</option>
      										</select>
										</div>
									</div>
								</div>
								
								<#-- Portfolio -->
								<div class="form-group row">
									<label for="event-poerfolio" class="col-sm-2 col-form-label">Portfolio</label>
									<div class="col-sm-10 input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-briefcase"></i>
											</span>
										</div>
      									<select class="form-control select2" name="event-portfolio" id="event-portfolio">
        									<option></option>
        									<optgroup label="My Portfolio(s)">
          										<option>A1</option>
          										<option>A2</option>
          										<option>A3</option>
        									</optgroup>
        									<optgroup label="Other">
          										<option>B1</option>
          										<option>B2</option>
         										<option>B3</option>
        									</optgroup>
      									</select>
									</div>
								</div>
								
							</div>
						</div>
						
					</div>
				</div>
				
				<#-- Submit Options -->
				<div class="card">
					<div class="card-body">
						
						<#-- Save -->
						<div class="btn-group mr-1 mb-1 mt-1 btn-group-justified" role="group" aria-label="Basic example">
							<button type="button" class="btn btn-outline-primary">
								<i class="fa-regular fa-check"></i>&nbsp;Save and exit
							</button>
							<button type="button" class="btn btn-outline-primary">
								<i class="fa-regular fa-check"></i>&nbsp;Save
							</button>
						</div>
	
						<#-- Discard -->
						<div class="btn-group mr-1 mb-1 mt-1 btn-group-justified" role="group" aria-label="Basic example">
							<button type="button" class="btn btn-outline-secondary">
								<i class="fa-solid fa-xmark"></i>&nbsp;Discard
							</button>
							<button type="button" class="btn btn-outline-secondary">
								<i class="fa-solid fa-xmark"></i>&nbsp;Discard and exit
							</button>
						</div>
						
						<#-- Delete -->
						<#if mode=="EDIT">		
							<button type="button" class="btn btn-outline-danger mr-2 mb-1 mt-1">
								<i class="fa-solid fa-trash"></i>&nbsp;Delete
							</button>
						</#if>
						
					</div>
				</div>
				
			</div>
		</section>

	</div>
	
	<script>
		$('#event-description').summernote({
			placeholder: 'Event description',
        	tabsize: 4,
        	height: 400,
        	minHeight: 400,
  			maxHeight: 400
      	});
      	
      	$('#event-portfolio').select2({
    		theme: 'bootstrap4',
		});
    </script>
</body>
</html>