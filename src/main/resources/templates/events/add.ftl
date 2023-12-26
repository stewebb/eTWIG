<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/summernote-bs4.min.css">
	<script src="/static/js/summernote-bs4.min.js"></script>
	
	<#-- Custom JS for adding events-->
	<script src="/static/js/etwig-events.js"></script>

	<title>Add Event - eTWIG Administration Portal</title>
</head>

<body class="sidebar-mini layout-fixed">
	<#if embedded == false>
		<#include "../_includes/sidebar.ftl">
	</#if>
	
	
	<#-- Content Wrapper -->
  	<div class="<#if embedded == false>content-</#if>wrapper">
  	
    	<#-- Page header -->
    	<section class="content-header">
      		<div class="container-fluid">
        		<div class="row mb-2">
          			<div class="col-sm-6">
            			<h1 class="bold-text">
            				Add Event
            			</h1>
          			</div>
          			<div class="col-sm-6">
            			<ol class="breadcrumb float-sm-right">
              				<li class="breadcrumb-item"><a href="/">Home</a></li>
              				<li class="breadcrumb-item">Events</li>
              				<li class="breadcrumb-item active"><a href="/events/add">Add Event</a></li>
            			</ol>
          			</div>
        		</div>
      		</div>
    	</section>
    	
    	<#-- Main area -->
    	<section class="content">
			<form class="container-fluid" id="addEventForm" action="/events/add" method="post">
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
									<label for="eventName" class="col-sm-2 col-form-label">
										Name&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-lightbulb"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Name" id="eventName" maxlength="31">
										</div>
									</div>
								</div>
								
								<#-- Location -->
								<div class="form-group row">
									<label for="eventLocation" class="col-sm-2 col-form-label">Location</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-location-dot"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Location" id="eventLocation" maxlength="63">
										</div>
									</div>
								</div>

								<#-- Recurrent -->
								<div class="form-group row">
									<label for="event-recurrent" class="col-sm-2 col-form-label">
										Recurrent&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
											<div class="icheck-primary">
												<input type="radio" id="single-time-event" name="event-recurrent" checked="">
												<label for="single-time-event">Single Time</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="recurring-event" name="event-recurrent" disabled>
												<label for="recurring-event">Recurring</label>
											</div>
										</div>				
								
										<div class="callout callout-info">
											<h5 class="bold-text mb-3">Be Careful!</h5>
											Once you set the recurrent option, it cannot be changed unless you delete the event completely, then add a new event.
										</div>
									</div>
								</div>
								
								<#-- Description -->
								<div class="form-group">
									<label for="eventDescription">Description</label>
									<div id="eventDescription"></div>
								</div>
							</div>
						</div>			
					</div>
					
					<div class="col-md-6">
					
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
									<label for="eventTimeUnit" class="col-sm-2 col-form-label">
										Time Unit&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
											<div class="icheck-primary">
												<input type="radio" id="hour" name="eventTimeUnit" checked="" value="hour">
												<label for="hour">Hour</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="day" name="eventTimeUnit" value="day">
												<label for="day">Day [00:00-23:59]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="week" name="eventTimeUnit" value="week">
												<label for="week">Week [00:00 Mon-23:59 Sun]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="month" name="eventTimeUnit" value="month">
												<label for="month">Month [00:00 1st day-23:59 last day]</label>
											</div>
											<div class="icheck-primary">
												<input type="radio" id="customize" name="eventTimeUnit" value="customize">
												<label for="customize">Customize</label>
											</div>
										</div>
									</div>
								</div>
								
								<#-- Start Time -->
								<div class="form-group row">
									<label for="eventStartTime" class="col-sm-2 col-form-label">
										Start Time&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-start"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="eventStartTime">
										</div>
										<div id="eventStartWrapper" class="datepicker"></div>
									</div>
								</div>					
								
								<#-- Duration -->
								<div class="form-group row" id="durationInput">
									<label for="eventDuration" class="col-sm-2 col-form-label">
										Duration&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-half"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Duration" id="eventDuration">
											<div class="input-group-append">
												<span class="input-group-text" id="unitText">Hour(s)</span>
											</div>
										</div>
									</div>
								</div>
								
								<#-- End Time -->
								<div class="form-group row" id="endTimeInput" style="display:none;">
									<label for="eventEndTime" class="col-sm-2 col-form-label">
										End Time&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-end"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event End Time" id="eventEndTime">
										</div>
									</div>
								</div>			
								
								
								
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
        									<option value="-1" selected>Please Select...</option>
        									<optgroup label="My Portfolio(s)">
        									
        									<#if myPortfolios?has_content>
        										<#list myPortfolios as portfolio_id, portfolio_info>
													<option data-color="#${portfolio_info.color}" data-icon="<#if portfolio_info.icon?has_content>${portfolio_info.icon}</#if>" value="${portfolio_id}">
														${portfolio_info.name}
													</option>
												</#list>
        									</#if>
        									</optgroup>
      									</select>
									</div>
								</div>
								
								<#-- Organizer -->
								<div class="form-group row">
									<label for="event-organizer" class="col-sm-2 col-form-label">Name</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-user"></i>
												</span>
											</div>
											
											<select class="form-control select2" name="event-organizer" id="event-organizer">
												<option value="-1" selected>Please Select...</option>
        										<optgroup label="Myself">
          											 <option value="${user.userId}">${user.username}</option>
        										</optgroup>
       
      										</select>
										</div>
									</div>
								</div>
								
								<#-- Role -->
								<div class="form-group row">
									<label for="event-organizer-role" class="col-sm-2 col-form-label">Role</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-clipboard-user"></i>
												</span>
											</div>
											
											<select class="form-control select2" name="event-organizer-role" id="event-organizer-role" disabled>
        										
      										</select>
										</div>
									</div>
								</div>
								
							</div>
						</div>
						
						<#-- Properties -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-ellipsis"></i>&nbsp;Properties
								</h3>
							</div>

							<div class="card-body">
								
							</div>
						</div>
						
						
					</div>
				</div>
				
				<#-- Submit Options -->
				<div class="card">
					<div class="card-body">
						
						<#-- Add -->
						<div class="btn-group mr-1 mb-1 mt-1 btn-group-justified" role="group" aria-label="Basic example">
							<button type="button" class="btn btn-outline-primary" id="add-event-exit" onclick="parent.$('#etwigModal').modal('hide');">
								<i class="fa-regular fa-plus"></i>&nbsp;Add and exit
							</button>
							<button type="button" class="btn btn-outline-primary" id="add-event">
								<i class="fa-regular fa-plus"></i>&nbsp;Add event
							</button>
						</div>
	
						<#-- Cancel -->
						<div class="btn-group mr-1 mb-1 mt-1 btn-group-justified" role="group" aria-label="Basic example">
							<button type="button" class="btn btn-outline-secondary" onclick="window.location.reload(); ">
								<i class="fa-solid fa-xmark"></i>&nbsp;Cancel
							</button>
							<button type="button" class="btn btn-outline-secondary">
								<i class="fa-solid fa-xmark"></i>&nbsp;Cancel and exit
							</button>
						</div>
						
					</div>
				</div>
				
			</form>
		</section>

	</div>
	
	<script>
		initDescriptionBox('#eventDescription');
		startTimePicker = createDatePicker("#eventStartWrapper", "#eventStartTime", "date", "yyyy-MM-dd HH:mm A", true);
		
      	timeUnitBtnOnChange();      	
      	
      	$('#event-organizer').select2({
    		theme: 'bootstrap4',
		});
		
      	$('#event-portfolio').select2({
    		theme: 'bootstrap4',
    		templateResult: formatState,
  			templateSelection: formatState,
		});
		
		//$("form").validate();
		$("#add-event").click(addEvent); 
		$("#add-event-exit").click(addEventAndExit); 
    </script>
</body>
</html>