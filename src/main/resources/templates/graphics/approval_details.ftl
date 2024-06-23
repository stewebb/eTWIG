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
	<title>Graphics Requests Approval - ${app.appName}</title>
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
	            				Graphics Request Approval
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/graphics/index.do">Graphics</a></li>
								<li class="breadcrumb-item"><a href="/graphics/approvalList.do">Request Approval</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/approval/approvalDetails.do?requestId=${requestInfo.id}">Decision</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">

					<#-- Row 1: Information -->
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

									<#-- Event Information -->
									<#include "../_includes/events/graphics_info.ftl">
									<#-- Event Information -->

									<#--
									<script>

										// Display duration
										var duration = ${eventInfo.duration};
										$("#eventDuration").text(formatTime(duration));

										var startTime = Date.parse("${eventInfo.startTime}");
										$("#eventStartTime").text(startTime.toString("ddd yyyy-MM-dd HH:mm"));
										$("#eventEndTime").text(startTime.addMinutes(duration).toString("ddd yyyy-MM-dd HH:mm"));

										<#if eventInfo.recurring>
											$("#eventStartTimeRow").hide();
											$("#eventEndTimeRow").hide();
										</#if>
									</script>
									-->

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

										<#-- Basic Info -->
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
												<td id="eventPortfolio">${requestInfo.requesterPosition}</td>
											</tr>
											<#-- /Position -->
													
										</table>
										<#-- /Basic Info -->

								</div>
							</div>
							<#-- /Request Info -->

							<#-- Response -->

							<#--
							<#assign disabledStr = (requestInfo.approved?has_content)?string("disabled","")>
							<#assign decisionStr = (requestInfo.approved?has_content)?string("View result","Make a decision")>
							-->


							<#if requestInfo.approved?has_content>
								<#assign disabledStr = "disabled">
								<#assign decisionStr = "View result">
								<#assign approveBtnVal = (requestInfo.approved)?string("checked", " ")>
								<#assign declineBtnVal = (requestInfo.approved)?string("", "checked")>
							<#else>
								<#assign disabledStr = "">
								<#assign decisionStr = "Make a decision">
								<#assign approveBtnVal = "">
								<#assign declineBtnVal = "">
							</#if>

							<div class="card card-primary card-outline col-12">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-gavel"></i>&nbsp;${decisionStr}
									</h3>
								</div>
								<div class="card-body">

									<#if requestInfo.approved?has_content>
										<div class="callout callout-primary mb-3">
											<h5 class="bold-text mb-2">Finalized request</h5>
											This banner request was finalized. You cannot make any changes.
										</div>
									</#if>

									<#-- Decision -->
									<div class="form-group row">
										<label for="graphicsApprovalOption" class="col-sm-2">
											Decision&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="form-group clearfix">
												<div class="icheck-success d-inline mr-2">
													<input type="radio" id="graphicsApprovalApproved" name="graphicsApprovalOption" value="1" ${disabledStr} ${approveBtnVal}>
													<label for="graphicsApprovalApproved">Approve</label>
												</div>
												<div class="icheck-danger d-inline mr-2">
													<input type="radio" id="graphicsApprovalDeclined" name="graphicsApprovalOption" value="0" ${disabledStr} ${declineBtnVal}>
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
											<textarea id="graphicsApprovalComments" class="form-control fixed-textarea" maxlength="255" rows="5" ${disabledStr}>${requestInfo.responseComment}</textarea>
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

										<button type="button" class="btn btn-outline-primary" onclick="decide();" ${disabledStr}>
											<i class="fa-regular fa-check"></i>&nbsp;Submit
										</button>
									</div>
									<#-- /Submit -->

								</div>
							</div>
						<#-- Response -->

						</div>
						<#-- /Col 2 -->

					</div>
					<#-- /Row 1: Information -->

				</div>
			</section>
			<#-- /Main area -->

		</div>
		<#-- /Content Wrapper. -->
		
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- JS for Graphics Approval -->
	<script type="text/javascript" src="/static/js/etwig/graphics-approval.js?ver=${app.appVersion}"></script>
	
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