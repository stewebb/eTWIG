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
							<#include "../_includes/events/addEdit_timing.ftl">	
						</div>

					</div>
					<#-- /Timing -->

					<#-- Additional Information -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fas fa-map-pin"></i>&nbsp;Additional Information
							</h3>
						</div>

						<div class="card-body">

							<#--
							<#include "../_includes/events/addEdit_additionalInfo.ftl">	
							-->
							<div class="table-responsive">
								<table id="requestsTable" class="display table table-hover table-striped" width="100%">
									<thead>
										<tr>
											<th>Request ID</th>
											<th>Expect Date</th>
											<th>Requester</th>
											<th>Request Time</th>
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
	
	<#-- bs stepper -->
	<#-- PENDING REMOVAL
	<link rel="stylesheet" href="/static/css/vendor/bs-stepper.min.css">
	<script src="/static/js/vendor/bs-stepper.min.js"></script>
	-->

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

			$('#eventRequestNow').change(function(){
				setGraphicsRequest(this.checked);
			})

			$('.event-rrule-options').change(function(){
				getRRuleByInput();
			})

			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			// Initialize the stepper
     		//eventStepper = new Stepper(document.querySelector('#eventStepper'), {
    		//	linear: false
  			//});

			// Initialize the description box
			initDescriptionBox('#eventDescription');
		});
		

		$(document).ready(function() {
			var urlParams = new URLSearchParams(window.location.search);
    var eventId = urlParams.get('eventId');

        $('#requestsTable').DataTable({
            "processing": true,
            "serverSide": true,
			"lengthMenu": [[3, 5, 10], [3, 5, 10]],
			"pageLength": 3,
			"searching": false, 
            "ajax": {
                "url": "/api/request/list?eventId=" + eventId + "&isApproved=na",
                "type": "GET",
                "data": function(d) {
                    return $.extend({}, d, {
                        "sortColumn": d.columns[d.order[0].column].data,
                        "sortDirection": d.order[0].dir
                    });
                }
            },
            "columns": [
                { "data": "id", "orderable": true},
                { "data": "expectDate", "orderable": false},
                { "data": "requesterName", "orderable": false},
				{ "data": "requestTime", "orderable": false},
				//{ "data": "requestComment", "orderable": false},
				//{ "data": "approved", "orderable": false},
				//{ "data": "approverName", "orderable": false},
				//{ "data": "responseTime", "orderable": false},
				//{ "data": "responseComment", "orderable": false},
				//{ "data": "assetId", "orderable": false},
            ]
        });
    });

    </script>
</body>
</html>