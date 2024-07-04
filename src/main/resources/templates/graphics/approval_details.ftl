<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "GRAPHICS_APPROVAL">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Banner Requests Approval - ${app.appName}</title>
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
	            				Banner Request Approval
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="${ENDPOINTS.GRAPHICS_APPROVAL_LIST}">Approval</a></li>
								<li class="breadcrumb-item active"><a href="${ENDPOINTS.GRAPHICS_APPROVAL_DETAILS}?requestId=${requestInfo.id}">Details</a></li>
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

						<#-- Information area -->
						<div class="col-md-6">

							<#-- Event Info -->
							<div class="card card-primary card-outline mb-3">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
									</h3>
								</div>
								<div class="card-body">

									<#-- Event Information /-->
									<#include "../_includes/events/graphics_info.ftl">

								</div>
							</div>
							<#-- /Event Info -->
	
						</div>
						<#-- /Information area -->
											
						<#-- Col 2 -->
						<div class="col-md-6">	

							<#-- Request Info -->
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
												<td style="color:#FFFFFF; background-color:#${eventInfo.portfolioColor}">
													${requestInfo.requesterPosition}
												</td>
											</tr>
											<#-- /Position -->
													
										</table>
										<#-- /Request Info -->

								</div>
							</div>
							<#-- /Request Info -->

							<#-- Response -->
							<div class="card card-primary card-outline col-12">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-gavel"></i>&nbsp;Decision
									</h3>
								</div>
								<div class="card-body">

									<#-- Finalized request -->
									<#if requestInfo.approved?has_content>
										<div class="callout callout-primary mb-3">
											<h5 class="bold-text mb-2">Finalized Request</h5>
											This banner request was finalized. You cannot make any changes.
										</div>

										<#-- Response Info -->
										<table class="table table-bordered mb-3">	

											<#-- Status -->
											<tr>
												<th scope="row">Status</th>
												<td class="text-${(requestInfo.approved)?string("success","danger")} bold-text">
													${(requestInfo.approved)?string("Approved","Declined")}
												</td>
											</tr>
											<#-- /Status -->

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
														<a class="btn btn-outline-primary btn-sm mb-2" href="/assets/content.do?assetId=${requestInfo.assetId}&download=true">
															<i class="fa-solid fa-download"></i>&nbsp;Download
														</a>

														<br>
														<a class="btn btn-outline-primary btn-sm" href="/assets/content.do?assetId=${requestInfo.assetId}&download=false" target="_blank">
															<i class="fa-solid fa-magnifying-glass"></i>&nbsp;View
														</a>
													</th>
													<td>
														<img src="/assets/content.do?assetId=${requestInfo.assetId}" class="table-img" style="max-width:100%; height:auto;">				
													</td>
												</tr>
											</#if>
											<#-- /Banner -->
													
										</table>
										<#-- /Response Info -->

										<#-- Button -->
										<div class="d-flex justify-content-between" role="group">
											<a class="btn btn-outline-secondary" href="/graphics/approvalList.do">
												<i class="fa-regular fa-arrow-left"></i>&nbsp;Back to list
											</a>

											<button type="button" class="btn btn-outline-danger confirm-btn" data-action='{"functionName": "removeBannerRequest", "params": [${requestInfo.id}]}'>
												<i class="fa-solid fa-trash"></i>&nbsp;Delete
											</button>
										</div>
										<span class="confirmation-text"></span>
										<#-- /Button -->

									<#-- /Finalized request -->

									<#-- Pending request -->
									<#else>

										<#-- Decision -->
										<div class="form-group row">
											<label for="graphicsApprovalOption" class="col-sm-2">
												Decision&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-10">
												<div class="form-group clearfix">
													<div class="icheck-success d-inline mr-2">
														<input type="radio" id="graphicsApprovalApproved" name="graphicsApprovalOption" value="1">
														<label for="graphicsApprovalApproved">Approve</label>
													</div>
													<div class="icheck-danger d-inline mr-2">
														<input type="radio" id="graphicsApprovalDeclined" name="graphicsApprovalOption" value="0">
														<label for="graphicsApprovalDeclined">Decline</label>
													</div>
												</div>				
											</div>
										</div>
										<#-- /Decision -->

										<#-- Feedback -->
										<div class="form-group row">
											<label for="graphicsApprovalComments" class="col-sm-2">Comments</label>
											<div class="col-sm-10">
												<textarea id="graphicsApprovalComments" class="form-control fixed-textarea" maxlength="255" rows="5"></textarea>
												<small class="form-text text-muted">Feedback to requester, up to 255 characters.</small>
											</div>
										</div>
										<#-- /Feedback -->

										<#-- Assets -->
										<div class="form-group row" id="graphicsApprovalAssets" style="display:none;">
											<label for="graphicsApprovalAssets" class="col-sm-2 col-form-label">
												Assets&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-10">
											
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-image"></i>
														</span>
													</div>
													<input type="text" class="form-control template-image-input" id="uploadCallback" readonly>
													
													<div class="input-group-append">
														<button type="button" id="graphicsApprovalAssetsBtn" class="btn btn-outline-secondary" onclick="selectUpload();">
															<i class="fa-regular fa-upload"></i>&nbsp;Select/Upload
														</button>		
													</div>
												</div>

												<img src="about:blank" class="img-fluid" id="uploadImage" />			
											</div>
										</div>
										<#-- /Assets -->	

										<#-- Submit -->
										<div class="d-flex justify-content-between" role="group">

											<a class="btn btn-outline-secondary" href="/graphics/approvalList.do">
												<i class="fa-regular fa-arrow-left"></i>&nbsp;Back to list
											</a>

											<button type="button" class="btn btn-outline-danger confirm-btn" data-action='{"functionName": "removeBannerRequest", "params": [${requestInfo.id}]}'>
												<i class="fa-solid fa-trash"></i>&nbsp;Delete
											</button>

											<button type="button" class="btn btn-outline-primary" onclick="decide();">
												<i class="fa-regular fa-check"></i>&nbsp;Submit
											</button>
										</div>
										<#-- /Submit -->

									</#if>
									<#-- /Pending request -->

								</div>
							</div>
						<#-- Response -->

						</div>
						<#-- /Col 2 -->

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

</body>
</html>