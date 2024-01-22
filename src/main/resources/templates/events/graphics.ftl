<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "CALENDAR">
<#assign modeStr = (count == 0)?string("New Request", "Follow-up")>

<#-- Edit permission check. -->
<#assign disabled = !editPermission>
<#assign disabledStr = editPermission ? string("", "disabled")>

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Management - ${app.appName}</title>
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
	            			<h1 class="bold-text" id="eventPageTitle">
	            				Event Graphics: ${eventInfo.name}
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item active"><a href="/events/calendar">Events</a></li>
	              				<li class="breadcrumb-item active"><a href="/events/graphics?eventId=${eventInfo.id}" id="eventPageLink">Graphics</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">

				<#-- Graphics -->
				<div class="container-fluid">
					<div class="card card-primary card-outline">

						<#-- Tabs -->
						<div class="card-header d-flex p-0">
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
									<a class="nav-link" href="/events/edit?eventId=${eventInfo.id}">
										<i class="fa-solid fa-pen-to-square"></i>&nbsp;Edit
									</a>
								</li>
								<#-- /View/Edit -->	

								<#-- Graphics Request -->	
								<li class="nav-item">
									<a class="nav-link active" href="/events/graphics?eventId=${eventInfo.id}">
										<i class="fa-solid fa-image"></i>&nbsp;Graphics
									</a>
								</li>
								<#-- /Graphics Request -->	

							</ul>
						</div>
						<#-- /Tabs -->
						
						<#-- Content -->
						<div class="card-body">
							<div class="row col-12">

								<#-- Col 1 -->
								<div class="col-md-6">
								
									<#-- Event Information -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
										</h5>

										<#-- Basic Info -->
										<table class="table table-bordered">
													
											<#-- Event Id -->
											<tr>
												<th scope="row">Event Id</th>
												<td>${eventInfo.id}</td>
											</tr>
											<#-- /Event Id -->
													
											<#-- Name -->
											<tr>
												<th scope="row">Name</th>
												<td>${eventInfo.name}</td>
											</tr>
											<#-- /Name -->
													
											<#-- Location -->
											<tr>
												<th scope="row">Location</th>
												<td><#if eventInfo.location?has_content>${eventInfo.location}</#if></td>
											</tr>
											<#--  /Location -->
													
											<#-- Type -->
											<tr>
												<th scope="row">Type</th>
												<td>${(eventInfo.recurrent)?string("Recurring","Single Time")} Event</td>
											</tr>
											<#-- /Type -->
													
											<#-- Start Time -->
											<tr>
												<th scope="row">Start Time</th>
												<td>${eventInfo.startTime}</td>
											</tr>
											<#-- /Start Time -->
													
											<#--  Organizer -->
											<tr>
												<th scope="row">Organizer</th>
												<td>${eventInfo.organizer.fullName}</td>
											</tr>
											<#--  /Organizer -->

											<#-- Position and Portfolio -->
											<tr>
												<th scope="row">Position and Portfolio</th>
												<td id="eventPortfolio" style="background-color:#${eventInfo.portfolio.color}">
													${eventInfo.organizerPosition}, ${eventInfo.portfolio.name}
												</td>
											</tr>
											<#-- /Position and Portfolio -->
													
										</table>
										<#-- /Basic Info -->

										<#-- Description -->
										<blockquote>
											<label>Description</label>
											<#if eventInfo.description?has_content>
												${eventInfo.description}
											<#else>
												<span>No description.</span>	
											</#if>
										</blockquote>
										<#-- /Description -->	

									</div>
									<#-- Event Information -->

									<#-- Current Graphics -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-icons"></i>&nbsp;Current Graphics
										</h5>
									</div>
									<#-- /Current Graphics -->

								</div>
								<#-- Col 1 -->
								
							
								<#-- Col 2 -->
								<div class="col-md-6">

									<#-- User Input -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-circle-plus"></i>&nbsp;${modeStr}
										</h5>

										<#-- Hidden inputs -->
										<input type="hidden" id="eventId" value="${eventInfo.id}" />
										<input type="hidden" id="requester" value="${eventInfo.organizer.id}" />
										<#-- /Hidden inputs -->
									
										<#-- Permission check. -->
										<#if editPermission>
										
											<#-- Returning Date -->
											<div class="form-group">
												<label for="returningDate">
													Returning date&nbsp;<span class="required-symbol">*</span>
												</label>
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-clock"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="returningDate" ${disabledStr}>
												</div>
												<div id="returningDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">Returning date for getting your graphic.</small>
											</div>			
											<#-- Returning Date -->
											
											<#-- Comment -->
											<div class="form-group">
											<label for="comment">Additional Comments</label>										
											<textarea id="requestComment" class="form-control fixed-textarea" id="comment" maxlength="255" rows="5" ${disabledStr}></textarea>
											<small class="form-text text-muted">Additional comments and requirements, up to 255 characters.</small>
											</div>			
											<#-- /Comment -->
									
											<#-- Submit -->
											<div class="right-div" role="group">
												<button type="button" class="btn btn-outline-primary" onclick="requestEvent();" ${disabledStr}>
													<i class="fa-regular fa-check"></i>&nbsp;Submit
												</button>
											</div>
											<#-- /Submit -->
										
										<#else>
											<#assign eventDetails = eventInfo>
											<#assign calloutTitle = "No graphics permission">
											<#include "../_includes/events/noPermission_callout.ftl">
										</#if>
									</div>
									<#-- /User Input -->

									<#-- Request history -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-clock-rotate-left"></i>&nbsp;Request history [Count: ${count}]
										</h5>
									</div>
									<#-- /Request history -->
									
								</div>
								<#-- /Col 2 -->

							</div>
						</div>
						<#-- /Content -->		

					</div>
				</div>
				<#-- /Graphics -->

			</section>
			<#-- /Main area -->

		</div>
		<#-- /Content Wrapper. -->
		
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- Custom JS for graphics request-->
	<script src="/static/js/etwig/graphics-request.js"></script>
	
	<script>
	
		$(document).ready(function() {
			updateTextColor($('#eventPortfolio'));
		});
		
		createDatePicker();
	
	</script>

</body>
</html>