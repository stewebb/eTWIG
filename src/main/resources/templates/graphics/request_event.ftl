<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "GRAPHICS_REQUEST_VIEW">
<#assign modeStr = (count == 0)?string("New Request", "Follow-up")>


<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Request - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">

	<#include "../_includes/header/body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<div class="content-wrapper">
	
		<#-- Page header -->
		<#-- <#if !embedded> -->
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
		<#-- </#if>-->
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
					
							<#-- Returning Date -->
							<div class="form-group">
								<label for="expectedDate">
									Expect date to get your graphics.&nbsp;<span class="required-symbol">*</span>
								</label>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-clock"></i>
										</span>
									</div>
									<input type="text" class="form-control" placeholder="Returning date for getting your graphic." id="returningDate">
								</div>
								<div id="eventEndWrapper" class="datepicker"></div>
							</div>			
							<#-- Returning Date -->
								
							<#-- Comment -->
							<div class="form-group">
								<label for="comment">Additional Comments</label>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-comment-dots"></i>
										</span>
									</div>
									<textarea class="form-control fixed-textarea" placeholder="Additional comments (Optional, maximum length is 255 characters.)" id="comment" maxlength="255" rows="5"></textarea>
								</div>
							</div>			
							<#-- /Comment -->
						
							<#-- Submit -->
							<div class="right-div" role="group">
								<button type="button" class="btn btn-outline-primary">
									<i class="fa-regular fa-check"></i>&nbsp;Submit
								</button>
							</div>
							<#-- /Submit -->
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
								<#assign displayCount = 1>
								<#list requestInfo as request_id, request_info>
								
									<#if request_info.approved?has_content>
										<#assign approvalIcon = request_info.approved?string("check", "xmark")>
										<#assign approvalIconColor = request_info.approved?string("success", "danger")>
									<#else>
										<#assign approvalIcon = "question">
										<#assign approvalIconColor = "warning">
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
									<span class="time"><i class="fas fa-clock"></i> 12:05</span>
									<h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
									<div class="timeline-body">
										xxxxxxx
									</div>

									<#-- Action Buttons. -->
									<div class="timeline-footer">
										<a class="btn btn-primary btn-sm">Read more</a>
										<a class="btn btn-danger btn-sm">Delete</a>
									</div>
									<#-- /Action Buttons. -->
								
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
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<script>
		updateTextColor($('#eventPortfolio'));
	</script>
</body>
</html>