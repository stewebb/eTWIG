<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/summernote-bs4.min.css">
	<script src="/static/js/summernote-bs4.min.js"></script>
	 
	<#-- CSS and JS for select 2.-->
	<link rel="stylesheet" href="/static/css/select2.min.css"/>
	<script src="/static/js/select2.min.js"></script>

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
												Once you set the recurrent option, it cannot be changed unless you delete the event completely, then add a new event.
											</div>
										<#else>
											<div class="callout callout-warning">
												<h5 class="bold-text mb-3">Recurrent Option Disabled</h5>
												You cannot change the recurrent option for an existing event. If you want to do so, please delate the event and create a new event.
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
								<div id="event-description"></div>
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
											<input type="text" class="form-control" placeholder="Event Organizer" id="event-organizer">
										</div>
									</div>
									
									<div class="form-group">
<label>Minimal</label>
<select class="form-control select2bs4 select2-hidden-accessible" style="width: 100%;" data-select2-id="17" tabindex="-1" aria-hidden="true">
<option selected="selected" data-select2-id="19">Alabama</option>
<option data-select2-id="79">Alaska</option>
<option data-select2-id="80">California</option>
<option data-select2-id="81">Delaware</option>
<option data-select2-id="82">Tennessee</option>
<option data-select2-id="83">Texas</option>
<option data-select2-id="84">Washington</option>
</select><span class="select2 select2-container select2-container--bootstrap4 select2-container--above select2-container--focus" dir="ltr" data-select2-id="18" style="width: 100%;"><span class="selection"><span class="select2-selection select2-selection--single" role="combobox" aria-haspopup="true" aria-expanded="false" tabindex="0" aria-disabled="false" aria-labelledby="select2-q3d7-container"><span class="select2-selection__rendered" id="select2-q3d7-container" role="textbox" aria-readonly="true" title="Alabama">Alabama</span><span class="select2-selection__arrow" role="presentation"><b role="presentation"></b></span></span></span><span class="dropdown-wrapper" aria-hidden="true"></span></span>
</div>


								</div>
							</div>
						</div>
						
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
    </script>
</body>
</html>