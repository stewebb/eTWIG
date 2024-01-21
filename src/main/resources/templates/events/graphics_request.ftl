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

<#-- Convert the "Embedded" boolean to String -->
<#assign isEmbeddedStr = embedded ?string('true', 'false')>

<#-- Edit permission check. -->
<#assign disabled = !editPermission>
<#assign disabledStr = editPermission ? string("", "disabled")>

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Request - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
	<#include "../_includes/header/body_start.ftl">
	
	<#if !embedded>
		<#include "../_includes/sidebar.ftl">
	</#if>
	
	<div class="<#if !embedded>content-</#if>wrapper">
	
		<#-- Page header -->
		<#if !embedded>
    		<section class="content-header">
      			<div class="container-fluid">
        			<div class="row mb-2">
          				<div class="col-sm-6">
            				<h1 class="bold-text">Graphics (Banner) Request</h1>
          				</div>
          				<div class="col-sm-6">
            				<ol class="breadcrumb float-sm-right">
              					<li class="breadcrumb-item"><a href="/">Home</a></li>
              					<li class="breadcrumb-item">Graphics</li>
              					<li class="breadcrumb-item"><a href="/graphics/request/view">Graphics Request</a></li>
              					<li class="breadcrumb-item active"><a href="/graphics/request/event">Event</a></li>
            				</ol>
          				</div>
        			</div>
      			</div>
    		</section>
		</#if>
		<#-- /Page header -->
		
	    <#-- Main area -->
    	<section class="content">
			<div class="row">
		
				<#-- Event Information -->
				<div class="col-md-6">
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-circle-info"></i>&nbsp;Event Information
							</h3>
						</div>
							
						<div class="card-body">
							<table class="table table-bordered">
								
								<#--  Event Id -->
    							<tr>
      								<th scope="row">Event Id</th>
      								<td>${eventInfo.id}</td>
    							</tr>
    							<#--  /Event Id -->
    							
    							<#--  Name -->
 								<tr>
      								<th scope="row">Name</th>
      								<td>${eventInfo.name}</td>
    							</tr>
    							<#--  /Name -->
    							
    							<#--  Location -->
 								<tr>
      								<th scope="row">Location</th>
      								<td>${eventInfo.location}</td>
    							</tr>
    							<#--  /Location -->
    							
    							<#--  Type -->
 								<tr>
      								<th scope="row">Type</th>
      								<td>${(eventInfo.recurrent)?string("Recurrent","Single Time")} Event</td>
    							</tr>
    							<#--  /Name -->
    							
    							<#-- Start Time -->
    							<tr>
      								<th scope="row">Start Time</th>
      								<td>${eventInfo.startTime}</td>
    							</tr>
    							<#--  /Start Time -->
    							
    							<#--  Portfolio -->
    							<tr>
      								<th scope="row">Portfolio</th>
      								<td id="eventPortfolio" style="background-color:#${eventInfo.portfolio.color}">${eventInfo.portfolio.name}</td>
    							</tr>
    							<#--  /Portfolio -->
    							
    							<#--  Organizer -->
    							<tr>
      								<th scope="row">Organizer</th>
      								<td>${eventInfo.organizer}</td>
    							</tr>
    							<#--  /Organizer -->
    							
							</table>
						
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
					</div>
				</div>
				<#-- /Event Information -->		
					
				<#-- New request/Follow-up -->		
				<div class="col-md-6">
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-plus"></i>&nbsp;${modeStr}
							</h3>
						</div>
							
						<div class="card-body">
						
							<#-- Hidden inputs -->
							<input type="hidden" id="eventId" value="${eventInfo.id}" />
							<input type="hidden" id="requester" value="${user.userId}" />
							<#-- /Hidden inputs -->
							
							<#-- Users are not allowed to add a followup if there has a pending request. -->
							<#if hasPending>
								<div class="callout callout-warning">
									<h5 class="bold-text mb-3">This event has a pending request.</h5>	
									Please wait the approver to make a decision before you make a follow-up request. 
									However, you can <span class="text-primary bold-text">remind the approver</span> at any time.
								</div>
								
							<#-- Also permission check. -->
							<#elseif editPermission>
							
								<#-- Returning Date -->
								<div class="form-group">
									<label for="returningDate">
										Expect date to get your graphics.&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-clock"></i>
											</span>
										</div>
										<input type="text" class="form-control" placeholder="Returning date for getting your graphic." id="returningDate" ${disabledStr}>
									</div>
									<div id="returningDateWrapper" class="datepicker"></div>
								</div>			
								<#-- Returning Date -->
								
								<#-- Comment -->
								<div class="form-group">
								<label for="comment">Additional Comments</label>										
									<textarea id="requestComment" class="form-control fixed-textarea" placeholder="Additional comments (Optional, maximum length is 255 characters.)" id="comment" maxlength="255" rows="5" ${disabledStr}></textarea>
								</div>			
								<#-- /Comment -->
						
								<#-- Submit -->
								<div class="right-div" role="group">
									<button type="button" class="btn btn-outline-primary" onclick="requestEvent(${isEmbeddedStr});" ${disabledStr}>
										<i class="fa-regular fa-check"></i>&nbsp;Submit
									</button>
								</div>
								<#-- /Submit -->
							
							<#else>
								<#assign eventDetails = eventInfo>
								<#assign calloutTitle = "No graphics request permission">
								<#include "../_includes/events/noPermission_callout.ftl">
							</#if>
							
							<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<#assign cancelBtn = embedded ? then("Close", "Cancel")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;${cancelBtn}
							</button>
							
						</div>
					</div>
				</div>
				<#-- /New request/Follow-up -->
				
			</div>
					
			<#-- Request history -->
			
			<div class="card card-primary card-outline">
				<div class="card-header">
					<h3 class="card-title">
						<i class="fa-solid fa-clock-rotate-left"></i>&nbsp;
						Request history [Count: ${count}]
					</h3>
				</div>
							
				<div class="card-body">
				
					<#-- No requests has been made. -->
					<#if count == 0>
						<div class="d-flex justify-content-center">
							<i class="fa-regular fa-face-dizzy big-icons"></i>
						</div>
									
						<div class="d-flex justify-content-center bold-text text-secondary">
							No previous requests.
						</div>
					
					<#-- Has at least 1 request -->
					<#else>
						<div class="timeline">

							<#if requestInfo?has_content>
							
								<#-- Each request, displayCount++ -->
								<#assign displayCount = 1>
								<#list requestInfo as request_id, request_info>
								
									<#-- Attributes for the request has a result. -->
									<#if request_info.approved?has_content>
										<#assign approvalIcon = request_info.approved?string("check", "xmark")>
										<#assign approvalIconColor = request_info.approved?string("success", "danger")>
										<#assign operationTimeStr = request_info.responseTimeStr>
										<#assign approvalStatus = request_info.approved?string("approved", "declined")>
									
									<#-- Attributes for the request doesn't have a result. -->
									<#else>
										<#assign approvalIcon = "question">
										<#assign approvalIconColor = "warning">
										<#assign operationTimeStr = request_info.requestTimeStr>
										<#assign approvalStatus = "pending">
									</#if>
									
								<#-- Time Label -->
								<#-- Latest (The first occurance). -->
								<#if displayCount == 1>
									<div class="time-label">
										<span class="bg-primary">Latest</span>
									</div>
								</#if>
								
								<#-- Previous (The second occurance). -->
								<#if displayCount == 2>
									<div class="time-label">
										<span class="bg-secondary">Previous</span>
									</div>
								</#if>
								<#-- /Time Label -->
								
								<div>
									<i class="fas fa-${approvalIcon} bg-${approvalIconColor}"></i>
									<div class="timeline-item">
										<span class="time bold-text">
											<i class="fa-regular fa-clock"></i>&nbsp;${operationTimeStr}
										</span>
										
										<#-- Request status -->
										<h3 class="timeline-header">
											<#if request_info.approved?has_content>
												${request_info.approverName} was <span class="text-${approvalIconColor} bold-text">${approvalStatus}</span> this request.
											<#else>
												This request is <span class="text-${approvalIconColor} bold-text">${approvalStatus}</span>.
											</#if>
										</h3>
										<#-- /Request status -->
										
										<#-- Request related info -->
										<div class="timeline-body">
										
											<#-- Request-->
											<div class="container-fluid">
											
												<#-- Requester -->
    											<div class="row col-12 mb-2">
        											<div class="col-md-3 text-left bold-text">Requester</div>
       												<div class="col-md-9">${request_info.requestorName}</div>
    											</div>
    											<#-- /Requester -->
    											
												<#-- Request Time -->
    											<div class="row col-12 mb-2">
        											<div class="col-md-3 text-left bold-text">Request Time</div>
       												<div class="col-md-9">${request_info.requestTime} (${request_info.requestTimeStr})</div>
    											</div>
    											<#-- Request Time -->
    											
    											<#-- Expect return date -->
    											<div class="row col-12 mb-2">
        											<div class="col-md-3 text-left bold-text">Expect Return Date</div>
       												<div class="col-md-9">${request_info.expectDate}</div>
    											</div>
    											<#-- /Expect return date -->
    											
    											<#-- Additional Comments -->
    											<div class="row col-12 mb-2">
        											<div class="col-md-3 text-left bold-text">Additional Comments</div>
       												<div class="col-md-9">
       													<#if request_info.requestComment?has_content>
       														${request_info.requestComment}
       													<#else>
       														<span class="text-secondary">No comments</span>
       													</#if>
       												</div>
    											</div>
    											<#-- /Additional Comments -->
    											
												<#-- Action for pending requests. -->
												<#if !request_info.approved?has_content>
													<div class="row col-12 mb-2">
        												<div class="col-md-3 text-left bold-text">Action</div>
       													<div class="col-md-9">
															<button type="button" class="btn btn-outline-primary" ${disabledStr}>
																<i class="fa-solid fa-bell"></i>&nbsp;Remind Approver
															</button>
														</div>
    												</div>
    											</#if>
    											<#-- /Action for pending requests. -->
    											
											</div>						
											<#-- /Request-->
											
											<#if request_info.approved?has_content>
												<hr />

												<#-- Response-->
												<div class="container-fluid">
											
													<#-- Approver -->
    												<div class="row col-12 mb-2">
        												<div class="col-md-3 text-left bold-text">Approver</div>
       													<div class="col-md-9">${request_info.approverName}</div>
    												</div>
    												<#-- /Approver -->
    											
													<#-- Response Time -->
    												<div class="row col-12 mb-2">
        												<div class="col-md-3 text-left bold-text">Response Time</div>
       													<div class="col-md-9">${request_info.responseTime} (${request_info.responseTimeStr})</div>
    												</div>
    												<#-- Response Time -->
    											
    												<#-- Comments -->
    												<div class="row col-12 mb-2">
        												<div class="col-md-3 text-left bold-text">Comments</div>
       													<div class="col-md-9">
       														<#if request_info.responseComment?has_content>
       															${request_info.responseComment}
       														<#else>
       															<span class="text-secondary">No comments</span>
       														</#if>
       													</div>
    												</div>
    												<#-- /Comments -->
    											
    												<#-- Asset and Action-->
    												<#if request_info.approved>
    													<div class="row col-12 mb-2">
        													<div class="col-md-3 text-left bold-text">Asset</div>
       														<div class="col-md-9">
       															<img src="/assets/getPublicAsset?assetId=${request_info.assetId}" class="img-fluid" />	
       														</div>
    													</div>
    													
    													<div class="row col-12 mb-2">
        													<div class="col-md-3 text-left bold-text">Action</div>
       														<div class="col-md-9">
																<a href="/assets/getPublicAsset?assetId=${request_info.assetId}&download=true" class="btn btn-outline-primary">
																	<i class="fa-solid fa-download"></i>&nbsp;Download Asset
																</a>
															</div>
    													</div>
    												</#if>
    												<#-- /Asset -->
    												
												</div>						
												<#-- /Response-->
												
											</#if>	
										</div>
										<#-- /Request related info -->

									</div>		
								</div>
								<#assign displayCount = displayCount + 1>
								</#list>
							</#if>
						</div>		
					</#if>		
					
				</div>
			</div>	
			<#-- /Request history -->
				
		</section>
		<#-- /Main area -->

	</div>
	
	<#if !embedded>
		<#include "../_includes/footer.ftl">
	</#if>
	
	<#include "../_includes/header/body_end.ftl">
	
	<#-- Custom JS for graphics request-->
	<script src="/static/js/etwig/graphics-request.js"></script>
	
	<script>
	
		$(document ).ready(function() {
			updateTextColor($('#eventPortfolio'));
		});
		
		<#-- Only load datepicker when new request/follow-up is allowed. -->
		<#if !hasPending>
			createDatePicker();
		</#if>
	
	</script>
</body>
</html>