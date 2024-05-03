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

					<#-- Event Information -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-circle-info"></i>&nbsp;Event Information
							</h3>
						</div>

						<div class="card-body">
							<#--<div class="container-fluid">-->
							<div class="row col-12">

								<#-- Col 1: Basic -->
								<div class="col-md-6">

									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-map-pin"></i>&nbsp;Basic
									</h5>

									<div class="mb-2" id="noPermissionCallout"></div>
									<input type="hidden" id="isEdit" value="0" />

									<#-- EventId -->
									<div class="form-group row" id="eventIdBlock" style="display:none">
										<label for="EventId" class="col-lg-3">eventId</label>
										<div class="col-lg-9" id="eventId"></div>
									</div>
									<#-- /EventId -->

									<#-- Name -->
									<div class="form-group row">
										<label for="eventName" class="col-lg-3 col-form-label">
											Name&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-lightbulb"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="eventName" maxlength="63">
											</div>
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Name -->

									<#-- Location -->
									<div class="form-group row">
										<label for="eventLocation" class="col-lg-3 col-form-label">Location</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-location-dot"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="eventLocation" maxlength="63">
											</div>
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Location -->

									<#-- Organizer -->
									<div class="form-group row">
										<label for="eventOrganizer" class="col-lg-3">Organizer</label>
										<div class="col-lg-9" id="eventOrganizer"></div>
									</div>
									<#-- /Organizer -->

									<#-- Organizer Role -->
									<div class="form-group row">
										<label for="eventRole" class="col-lg-3 col-form-label">
											Organizer Role&nbsp;<span class="required-symbol">*</span>
											</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												
												<select class="form-control select2bs4" name="eventRole" id="eventRole"></select>
											</div>
											<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
										</div>
									</div>
									<#-- Organizer Role -->

									<#-- Created Time -->
									<div class="form-group row" id="eventCreatedTimeBlock" style="display:none;">
										<label for="eventCreatedTime" class="col-lg-3">Created Time</label>
										<div class="col-lg-9" id="eventCreatedTime"></div>
									</div>
									<#-- /Created Time -->

									<#-- Updated Time -->
									<div class="form-group row" id="eventUpdatedTimeBlock" style="display:none;">
										<label for="eventUpdatedTime" class="col-lg-3">Updated Time</label>
										<div class="col-lg-9" id="eventUpdatedTime"></div>
									</div>
									<#-- /Updated Time -->

								</div>
								<#-- /Col 1: Basic -->

								<#-- Col 2: Additional -->
								<div class="col-md-6" style="text-align:left;">

									<#-- Additional Properties -->
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-ellipsis"></i>&nbsp;Additional
									</h5>
									<#-- /Additional Properties -->

									<#-- Description -->		
									<div class="form-group">
										<label for="eventDescription">Description</label>
										<div id="eventDescription"></div>
										<small class="form-text text-muted">Up to 65,535 characters.</small>
									</div>
									<#-- /Description -->

									<#-- Iterate all properties -->
									<#if allProperties?has_content>
										<#list allProperties as property_id, property_info>
									
											<#-- Set default value of the icon if it's empty in the DB. -->
											<#if property_info.icon?has_content>
												<#assign property_icon = property_info.icon>
											<#else>
													<#assign property_icon = "list-check">
											</#if>
											<#-- /Set. -->
											
											<#-- Convert the propertyId to String, as Freemarker doesn't accept numeric key when accessing to a map. -->
											<#assign string_id = property_id?string>
											<#-- /Convert. -->

											<#-- Mandatory field check. -->
											<#if property_info.mandatory>
												<#assign mandatoryStr = "true">
												<#assign mandatorySymbol = "<span class='required-symbol'>*</span>">
											<#else>
												<#assign mandatoryStr = "false">
												<#assign mandatorySymbol = "">
											</#if>
											<#-- /Mandatory field check. -->

												<div class="form-group row">

													<#-- Property name -->
													<label for="property-${property_id}" class="col-lg-4 col-form-label">
														${property_info.name}&nbsp;${mandatorySymbol}
													</label>
													<#-- /Property name -->

													<div class="col-lg-8 input-group">
										
														<div class="input-group-prepend">
															<span class="input-group-text">
																<i class="fa-solid fa-${property_icon}"></i>
															</span>
														</div>
											
														<#-- Each property has a select box. -->
														<select class="form-control select2bs4 property-select-box" name="property-${property_id}" data-property-name="${property_info.name}" data-mandatory=${mandatoryStr}>
															<option value="-1">(Not selected)</option>
									
															<#-- Get all options of a property -->
															<#if allOptions[string_id]?has_content>
																<#list allOptions[string_id] as opt>
																	<option value="${opt.id}">${opt.name}</option>
																</#list>
															</#if>
															<#-- /Get all options of a property -->
															
														</select>
														<#-- /Each property has a select box. -->
														
												</div>
											</div>
										</#list>
										

									<#-- Or just tell user there has no properties. -->
									<#else>
										<div class="d-flex justify-content-center">
											<i class="fa-regular fa-face-dizzy big-icons"></i>
										</div>
											
										<div class="d-flex justify-content-center bold-text text-secondary">
											No properties.
										</div>
									</#if>
									<#-- /Or. -->
								<#-- /Additional Properties -->

								</div>
							</div>
							<#-- /Col 2: Additional -->

						</div>

					</div>
					<#-- /Event Information -->

					<#-- Timing -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-clock"></i>&nbsp;Timing
							</h3>
						</div>

						<div class="card-body">
							<#--
								<#include "../_includes/events/addEdit_timing.ftl">	
							-->
							

						<!-- <div class="container-fluid"> -->
							<div class="row col-12">

								<#-- Col 1 -->
								<div class="col-md-6">

									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-toggle-on"></i>&nbsp;Mode
									</h5>

									<#-- Recurrent -->
									<div class="form-group row">
										<label for="event-recurrent" class="col-sm-2">
											Recurrent&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="form-group clearfix">
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="single-time-event" name="event-recurrent" checked="" value="0">
													<label for="single-time-event">Single Time</label>
												</div>
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="recurring-event" name="event-recurrent" value="1">
													<label for="recurring-event">Recurring</label>
												</div>
											</div>				

											<div class="callout callout-primary">
												<h5 class="bold-text mb-3">Be Careful!</h5>
												If you switch between recurrent options, some timing information may lose and they are not recoverable.
											</div>
												
										</div>
									</div>
									<#-- /Recurrent -->

									<#-- All day event -->
									<div class="form-group row">
										<label for="eventAllDayEvent" class="col-sm-2 col-form-label">
											All day event&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="icheck-primary">
  												<input type="checkbox" id="eventAllDayEvent" name="eventAllDayEvent">
              									<label for="eventAllDayEvent">All day event</label>
											</div>
											<small class="form-text text-muted">If this event as lasting the entire day, enable the checkbox.</small>
										</div>
									</div>
									<#-- /All day event -->

								</div>
								<#-- /Col 1 -->

								<#-- Col 2 -->
								<div class="col-md-6">

									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-list"></i>&nbsp;Details
									</h5>

									<div id="singleTimeEventOptions">
									
										<#-- Start Date -->
										<div class="form-group row">
											<label for="eventStartDate" class="col-sm-3 col-form-label">
												Start Date&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-regular fa-calendar"></i>
														</span>
													</div>
													<input type="text" class="form-control event-date-time" id="eventStartDate">
												</div>
												<div id="eventStartDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">yyyy-MM-dd format</small>
											</div>
										</div>
										<#-- /Start Date -->

										<#-- Start Time -->
										<div class="form-group row" id="eventStartTimeBlock">
											<label for="eventStartTime" class="col-lg-3 col-form-label">
												Start Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-start"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time event-date-time" id="eventStartTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock</small>
											</div>
										</div>
										<#-- /Start Time -->

										<#-- End Date -->
										<div class="form-group row">
											<label for="eventEndDate" class="col-lg-3 col-form-label">
												End Date&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-end"></i>
														</span>
													</div>
													<input type="text" class="form-control event-date-time" id="eventEndDate">
												</div>
												<div id="eventEndDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">yyyy-MM-dd format</small>
											</div>
										</div>			
										<#-- /End Date -->

										<#-- End Time -->
										<div class="form-group row" id="eventEndTimeBlock">
											<label for="eventEndTime" class="col-lg-3 col-form-label">
												End Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-end"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time event-date-time" id="eventEndTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock</small>
											</div>
										</div>			
										<#-- /End Time -->

										<#-- Duration -->
										<div class="form-group row">
											<label for="eventDurationCalculated" class="col-lg-3">Duration</label>
											<div class="col-lg-9">
												<div id="eventDurationCalculated"></div>
											</div>
										</div>			
										<#-- /Duration -->

										<#-- Possible clashes -->
										<!--
										<div class="form-group row">
											<label for="eventPossibleClashes" class="col-lg-3">Possible Clashes</label>
											<div class="col-lg-9">
												<div id="eventPossibleClashes"></div>
												<small class="form-text text-muted">You can add an event anyway regardless of the possible clashes. (Not recommended)</small>
											</div>
										</div>	
										-->		
										<#-- /Possible clashes -->
									</div>

									<div id="recurringEventOptions">

										<#-- Event Time -->
										<div class="form-group row" id="eventRecurringTimeBlock">
											<label for="eventRecurringTime" class="col-lg-3 col-form-label">
												Start Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-clock"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time" id="eventRecurringTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock.</small>
											</div>
										</div>
										<#-- /Event Time -->

										<#-- Duration -->
										<div class="form-group row" id="durationInput" >
											<label for="eventDuration" class="col-lg-3 col-form-label">
												Duration&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-half"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="eventDuration">
												</div>
											</div>
										</div>
										<#-- /Duration -->

										<#-- Frequency-->
										<div class="form-group row">
											<label for="eventFrequency" class="col-lg-3">
												Frequency&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="form-group clearfix">
												
													<#-- Daily -->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="daily" name="eventFrequency" checked value="3">
														<label for="daily">Daily</label>
													</div>
													
													<#-- Weekly -->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="weekly" name="eventFrequency" value="2">
														<label for="weekly">Weekly</label>
													</div>
													
													<#-- Monthly-->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="monthly" name="eventFrequency" value="1">
														<label for="monthly">Monthly</label>
													</div>
													
												</div>
											</div>
										</div>
										<#-- /Frequency -->

										<#-- Valid from -->
										<div class="form-group row">
											<label for="eventValidFromDate" class="col-sm-3 col-form-label">
												Valid From&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-start"></i>
														</span>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventValidFromDate">
												</div>
												<small class="form-text text-muted">The date when the recurrence start.</small>
												<div id="eventValidFromDateWrapper" class="datepicker"></div>
											</div>
										</div>
										<#-- /Valid from -->

										<#-- Valid to -->
										<div class="form-group row">
											<label for="eventValidToDate" class="col-lg-3 col-form-label">Valid To</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<div class="icheck-primary">
															<input type="checkbox" id="eventValidToDateEnabled" checked>
															<label for="eventValidToDateEnabled"></label>
														</div>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventValidToDate">
												</div>
												<small class="form-text text-muted">The date when the recurrence end.</small>
												<div id="eventValidToDateWrapper" class="datepicker"></div>
											</div>
										</div>			
										<#-- /Valid to -->

										<#-- Count -->
										<div class="form-group row">
											<label for="eventCount" class="col-lg-3 col-form-label">Count</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-stopwatch"></i>
														</span>
													</div>
													<input type="number" min="2" class="form-control event-rrule-options" id="eventCount">
												</div>
												<small class="form-text text-muted">How many occurrences will be generated. The smallest number allowed is 2.</small>
											</div>
										</div>
										<#-- /Count -->

										<#-- Interval -->
										<div class="form-group row">
											<label for="eventInterval" class="col-lg-3 col-form-label">Interval</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-person-running"></i>
														</span>
													</div>
													<input type="number" min="1" class="form-control event-rrule-options" id="eventInterval">
												</div>
												<small class="form-text text-muted">The interval between each frequency iteration.</small>
											</div>
										</div>
										<#-- /Interval -->

										<#-- By week day -->
										<div class="form-group row">
											<label for="eventByWeekDay" class="col-lg-3 col-form-label">By Week Day</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-week"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventByWeekDay">
														<option value="0">Monday</option>
														<option value="1">Tuesday</option>
														<option value="2">Wednesday</option>
														<option value="3">Thursday</option>
														<option value="4">Friday</option>
														<option value="5">Saturday</option>
														<option value="6">Sunday</option>
													</select>	
												</div>
												<small class="form-text text-muted">The day of week to apply the recurrence to.</small>
											</div>
										</div>
										<#-- /By week day -->

										<#-- By month -->
										<div class="form-group row">
											<label for="eventByMonth" class="col-lg-3 col-form-label">By Month</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-days"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventByMonth">
														<option value="1">January</option>
														<option value="2">February</option>
														<option value="3">March</option>
														<option value="4">April</option>
														<option value="5">May</option>
														<option value="6">June</option>
														<option value="7">July</option>
														<option value="8">August</option>
														<option value="9">September</option>
														<option value="10">October</option>
														<option value="11">November</option>
														<option value="12">December</option>				
													</select>									
												</div>
												<small class="form-text text-muted">The month to apply the recurrence to</small>
											</div>
										</div>
										<#-- /By month -->

										<#-- By Month Day -->
										<div class="form-group row">
											<label for="eventByMonthDay" class="col-lg-3 col-form-label">By Month Day</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-days"></i>
														</span>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventByMonthDay">
												</div>
												<small class="form-text text-muted">The day of month to apply the recurrence to. Use comma to divide each number.</small>
											</div>
										</div>
										<#-- By Month Day -->

										<#-- Excluded dates -->
										<div class="form-group row">
											<label for="eventExcludedDates" class="col-lg-3 col-form-label">Excluded Dates</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-xmark"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventExcludedDates">
			
													</select>									
												</div>
												<small class="form-text text-muted">The dates that you want to exclude.</small>
											</div>
										</div>
										<#-- Excluded dates -->

										<#-- Description -->
										<div class="form-group row">
											<label for="eventRRuleDescription" class="col-lg-3">Description</label>
											<div class="col-lg-9" id="eventRRuleDescription"></div>
										</div>			
										<#-- Description -->

										<#-- All dates -->
										<div class="form-group row">
											<label for="eventRRuleAllDates" class="col-lg-3">All dates</label>
											<div class="col-lg-9">
												<span id="eventRRuleAllDatesNum"></span>
												<div class="table-responsive" style="max-height: 300px;">
													<table class="table table-head-fixed text-nowrap table-striped table-hover" id="eventRRuleAllDates">
														<thead>
															<tr>
																<th>Day of week</th>
																<th>Year</th>
																<th>Month</th>
																<th>Day</th>
																<th>Exclude</th>
															</tr>
														</thead>
														<tbody>

														</tbody>
													</table>
												</div>
											</div>
										</div>
										<#-- /All dates -->

									</div>

								</div>
								<#-- /Col 2 -->

							</div>
						<!--</div> -->
						<#-- /Timing -->


						</div>

					</div>
					<#-- /Timing -->

					<#-- Additional Information -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-palette"></i>&nbsp;Grphics
							</h3>
						</div>

						<div class="card-body">

							<div class="row col-12">

								<#-- Col 1: Basic -->
								<div class="col-md-6">
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-images"></i>&nbsp;Current Graphics
									</h5>
								</div>

								<#-- Col 2: Banner Request -->
								<div class="col-md-6">

									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-hand"></i>&nbsp;Banner Request
									</h5>

									<#-- Request a grphic now -->										
									<div class="mb-3">
										<div class="icheck-primary">
											<input type="checkbox" id="eventRequestNow" name="eventRequestNow">
											<label for="eventRequestNow">Request a banner now</label>
										</div>
									</div>
									<#-- /Request a grphic now -->
					
									<div id="eventRequestNowBlock" style="display:none">
										
										

										<#-- <#assign editPermission = true> -->
										
										
										<#-- Requester Role -->
										<!--
										<div class="form-group">
											<label for="requesterRole">
												Role&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
														
												<select class="form-control" name="requesterRole" id="requesterRole"></select>
											</div>
											<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
										</div>
										-->
										<#-- Requester Role -->

										<#-- Returning Date -->
										<div class="form-group">
											<label for="eventGraphicsDate">
												Returning date&nbsp;<span class="required-symbol">*</span>
											</label>

											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-clock"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="eventGraphicsDate">
											</div>

											<div id="eventGraphicsDateWrapper" class="datepicker"></div>
											<small class="form-text text-muted">Returning date for getting your graphic.</small>
										</div>			
											<#-- Returning Date -->
											
										<#-- Comment -->
										<div class="form-group">
											<label for="comment">Additional Comments</label>										
											<textarea id="requestComment" class="form-control fixed-textarea" maxlength="255" rows="5"></textarea>
											<small class="form-text text-muted">Additional comments and requirements, up to 255 characters.</small>
										</div>			
											<#-- /Comment -->

											 <!--
											<script>

												
												var myPositions = getMyPositions();
												for (var key in myPositions) {
													$("#requesterRole").append('<option value="' + myPositions[key].userRoleId + '">' + myPositions[key].position + ', ' + myPositions[key].portfolioName + '</option>');
												}

												$('#requesterRole').select2({
      												theme: 'bootstrap4'
    											})

											</script>
											-->


									</div>
									

								</div>

								<#-- /Col 2: Banner Request -->
							</div>

							<#--
							<#include "../_includes/events/addEdit_additionalInfo.ftl">	
							-->

							<div class="table-responsive" id="bannerRequestHistory" style="display:none;">

								<h5 class="mb-3 bold-text text-primary">
									<i class="fa-solid fa-clock-rotate-left"></i>&nbsp;Request History
								</h5>

								<table id="requestsTable" class="display table table-hover table-striped" width="100%">
									<thead>
										<tr>
											<th>Request ID</th>
											<th>Banner</th>
											<th>Request Date</th>
											<th>Status</th>
										</tr>
									</thead>
								</table>
							</div>

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

			// Toggle banner request area by clicking the "Request a banner now" checkbox.
			$('#eventRequestNow').change(function(){
				$('#eventRequestNowBlock').toggle(this.checked);
			})

			$('.event-rrule-options').change(function(){
				getRRuleByInput();
			})

			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			// Initialize the description box
			initDescriptionBox('#eventDescription');
		});

    </script>
</body>
</html>