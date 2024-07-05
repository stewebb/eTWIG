<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "EVENTS_BANNER_REQUEST">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Banner Requests Details - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">
		<#include "../_includes/navbar.ftl">
		<#include "../_includes/modal.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">

			<#-- Page header -->
	    	<section class="content-header">
	      		<div class="container-fluid">
	        		<div class="row mb-2">
	          			<div class="col-sm-6">
	            			<h1 class="bold-text" id="eventPageTitle">
	            				Banner Request Details
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item">
									<a href="${ENDPOINTS.EVENTS_CALENDAR}">Calendar</a>&nbsp|&nbsp
									<a href="${ENDPOINTS.EVENTS_LIST}">List</a>
								</li>
	              				<li class="breadcrumb-item">
									<a id="eventPageLink" href="${ENDPOINTS.EVENTS_EDIT}?eventId=${requestInfo.eventId}">Edit</a>
								</li>
								<li class="breadcrumb-item active">
									<a id="eventPageLink" href="${ENDPOINTS.EVENTS_BANNER_REQUEST}?requestId=${requestInfo.id}">Banner Request</a>
								</li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
					<div class="row col-12">

						<#-- Col 1: Request Info -->
						<div class="col-md-6">
							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-hand"></i>&nbsp;Request Information
									</h3>
								</div>
								<div class="card-body">

									<#-- Request Info -->
									<table class="table table-bordered">
													
										<#-- Request Id -->
										<tr>
											<th scope="row">Request ID</th>
											<td id="requestId">${requestInfo.id}</td>
										</tr>
										<#-- /Request Id -->

										<#-- Request Time -->
										<tr>
											<th scope="row">Request Time</th>
											<td>${requestInfo.requestTime?replace("T", " ")}</td>
										</tr>
										<#-- /Request Time -->
													
										<#-- Expect Date -->
										<tr>
											<th scope="row">Expect Date</th>
											<td>${requestInfo.expectDate}</td>
										</tr>
										<#-- /Expect Date -->
													
										<#-- Comment -->
										<tr>
											<th scope="row">Comment</th>
											<td><#if requestInfo.requestComments?has_content>${requestInfo.requestComments}</#if></td>
										</tr>
										<#-- /Comment -->
														
										<#-- Requester -->
										<tr>
											<th scope="row">Requester</th>
											<td>${requestInfo.requesterName}</td>
										</tr>
										<#-- /Requester -->

										<#-- Position -->
										<tr>
											<th scope="row">Position</th>
											<td style="color:#FFFFFF; background-color:#${requestInfo.requesterPortfolioColor}">
												${requestInfo.requesterPosition}
											</td>
										</tr>
										<#-- /Position -->

										<#-- Event Name -->
										<tr>
											<th scope="row">Event Name</th>
											<td>${requestInfo.eventName}</td>
										</tr>
										<#-- /Event Name -->
													
									</table>
									<#-- /Request Info -->

								</div>
							</div>
						</div>
						<#-- /Col 1: Request Info -->
											
						<#-- Col 2: Response Info -->
						<div class="col-md-6">	

							<#-- Response Info -->
							<div class="card card-primary card-outline col-12">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-gavel"></i>&nbsp;Response
									</h3>
								</div>
								<div class="card-body">
									<table class="table table-bordered mb-3">	

										<#-- Status -->
										<tr>
											<th scope="row">Status</th>
											<#if requestInfo.approved?has_content>
												<#if requestInfo.approved>
													<td class="text-success bold-text">Approved</td>
												<#else>
													<td class="text-danger bold-text">Declined</td>
												</#if>
											<#else>
												<td class="text-warning bold-text">Pending</td>
											</#if>
										</tr>
										<#-- /Status -->

										<#if requestInfo.approved?has_content>

											<#-- Response Time -->
											<tr>
												<th scope="row">Response Time</th>
												<td>${requestInfo.responseTime?replace("T", " ")}</td>
											</tr>
											<#-- /Request Time -->
													
											<#-- Comment -->
											<tr>
												<th scope="row">Comment</th>
												<td><#if requestInfo.responseComment?has_content>${requestInfo.responseComment}</#if></td>
											</tr>
											<#-- /Comment -->
														
											<#-- Approver -->
											<tr>
												<th scope="row">Approver</th>
												<td>${requestInfo.approverName}</td>
											</tr>
											<#-- /Requester -->

											<#-- Position -->
											<tr>
												<th scope="row">Position</th>
												<td style="color:#FFFFFF; background-color:#${requestInfo.approverPortfolioColor}">
													${requestInfo.approverPosition}
												</td>
											</tr>
											<#-- /Position -->
											
											<#-- Banner -->
											<#if requestInfo.approved && requestInfo.assetId?has_content>
												<tr>
													<th scope="row">
														<span class="mb-2">Banner</span><br>
														<a class="btn btn-outline-primary btn-sm mb-2" href="${ENDPOINTS.ASSETS_CONTENT}?assetId=${requestInfo.assetId}&download=true">
															<i class="fa-solid fa-download"></i>&nbsp;Download
														</a>

														<br>
														<a class="btn btn-outline-primary btn-sm" href="${ENDPOINTS.ASSETS_CONTENT}?assetId=${requestInfo.assetId}&download=false" target="_blank">
															<i class="fa-solid fa-magnifying-glass"></i>&nbsp;View
														</a>
													</th>
													<td>
														<img src="${ENDPOINTS.ASSETS_CONTENT}?assetId=${requestInfo.assetId}" class="table-img" style="max-width:100%; height:auto;">				
													</td>
												</tr>
											</#if>
											<#-- /Banner -->
										</#if>
													
										</table>
										<#-- /Response Info -->

										<#-- Button -->
										<div class="d-flex justify-content-between" role="group">
											<a class="btn btn-outline-secondary" href="${ENDPOINTS.EVENTS_EDIT}?eventId=${requestInfo.eventId}">
												<i class="fa-regular fa-arrow-left"></i>&nbsp;Back to event
											</a>

											<#--
											<button type="button" class="btn btn-outline-danger confirm-btn" data-action='{"functionName": "removeBannerRequest", "params": [${requestInfo.id}]}'>
												<i class="fa-solid fa-trash"></i>&nbsp;Delete
											</button>
											-->
										</div>
										<span class="confirmation-text"></span>
										<#-- /Button -->

									<#-- /Finalized request -->

									

								</div>
							</div>
							<#-- Response Info -->
						<#-- Response -->

						</div>
						<#-- /Col 2: Response Info -->

					</div>
				</div>
			</section>
			<#-- /Main area -->

		</div>
		<#-- /Content Wrapper. -->
		
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- JS for Banner Request Approval -->
	<#-- 
	<script type="text/javascript" src="/static/js/etwig/banner-approval.js?ver=${app.appVersion}"></script>
	
	<script>
	

		$(document).ready(function() {
			$('input[type=radio][name=graphicsApprovalOption]').change(function() {
				setAssetsUpload(this.value);
			});

			// Generate a banner-friendly event timeframe.
			var startTime = Date.parse("${eventInfo.startTime}");
			var endTime = Date.parse("${eventInfo.startTime}").addMinutes(duration);
			$("#toBannerRow").show();
			$("#eventToBanner").text(formatEventDates(startTime, endTime));
		});
	
	</script>
	-->

</body>
</html>