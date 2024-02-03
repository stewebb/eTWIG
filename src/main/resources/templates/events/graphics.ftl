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
								
									<#-- Event info -->
									<#include "../_includes/events/graphics_info.ftl" >

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
										<#-- /Hidden inputs -->
									
										<#-- Permission check. -->
										<#if editPermission>
											<#include "../_includes/events/graphics_newRequest.ftl">
											
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

										<#-- No requests has been made. -->
										<#if count == 0>
											<div class="d-flex justify-content-center">
												<i class="fa-solid fa-circle-xmark big-icons"></i>
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
													<#-- Latest (The first occurrence). -->
													<#if displayCount == 1>
														<div class="time-label">
															<span class="bg-primary">Latest</span>
														</div>
													</#if>
													
													<#-- Previous (The second occurrence). -->
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
																	${request_info.approver.fullName} was <span class="text-${approvalIconColor} bold-text">${approvalStatus}</span> this request.
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
																		<div class="col-md-9">${request_info.requester.fullName}</div>
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
																			<div class="col-md-9">${request_info.approver.fullName}</div>
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
																				<div class="col-md-3 text-left bold-text col-form-label">Action</div>
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
		<#if editPermission>
			createDatePicker();
		</#if>
	</script>

</body>
</html>