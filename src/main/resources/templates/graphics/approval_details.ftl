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

									<#assign eventInfo = requestInfo.event>

									<#--
									<#include "../_includes/events/graphics_info.ftl">
									-->

									<#-- Event Information -->
									<div class="mb-2">

										<#--
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
										</h5>
										-->

										<#-- Basic Info -->
										<table class="table table-bordered">

											<#-- Basic /-->
											<tr><th scope="row" colspan="2" class="table-title">Basic</th></tr>
													
											<#-- Event Id /-->
											<tr><th scope="row">Event ID</th><td>${eventInfo.id}</td></tr>
													
											<#-- Name /-->
											<tr><th scope="row">Name</th><td>${eventInfo.name}</td></tr>
													
											<#-- Location -->
											<tr>
												<th scope="row">Location</th>
												<td><#if eventInfo.location?has_content>${eventInfo.location}</#if></td>
											</tr>
											<#--  /Location -->

											<#--  Organizer /-->
											<tr><th scope="row">Organizer</th><td>${eventInfo.organizer.fullName}</td></tr>

											<#-- Position and Portfolio -->
											<tr>
												<th scope="row">Position and Portfolio</th>
												<td id="eventPortfolio" style="color:#FFFFFF; background-color:#${eventInfo.portfolio.color}">
													${eventInfo.organizerPosition}, ${eventInfo.portfolio.name}
												</td>
											</tr>
											<#-- /Position and Portfolio -->

											<#-- Timing /-->
											<tr><th scope="row" colspan="2" class="table-title">Timing</th></tr>
													
											<#-- Type -->
											<tr>
												<th scope="row">Type</th>
												<td>${(eventInfo.recurrent)?string("Recurring","Single Time")} Event</td>
											</tr>
											<#-- /Type -->
													
											<#-- Start Time /-->
											<tr id="eventStartTimeRow"><th scope="row">Start Time</th><td id="eventStartTime"></td></tr>

											<#-- Duration /-->
											<tr><th scope="row">Duration</th><td id="eventDuration"></td></tr>

											<#-- End Time /-->
											<tr id="eventEndTimeRow"><th scope="row">End Time</th><td id="eventEndTime"></td></tr>

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

									<script>

										// Display duration
										var duration = ${eventInfo.duration};
										$("#eventDuration").text(formatTime(duration));


										var startTime = Date.parse("${eventInfo.startTime}");
										$("#eventStartTime").text(startTime.toString("ddd yyyy-MM-dd HH:mm"));
										$("#eventEndTime").text(startTime.addMinutes(duration).toString("ddd yyyy-MM-dd HH:mm"));

										<#-- Hide the start and end time for recurring events. -->
										<#if eventInfo.recurrent>
											$("#eventStartTimeRow").hide();
											$("#eventEndTimeRow").hide();
										</#if>
									</script>

								</div>
							</div>
							<#-- /Event Info -->

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
												<th scope="row">Request Id</th>
												<td id="requestId">${requestInfo.id}</td>
											</tr>
											<#-- /Request Id -->


											<#-- Request Time -->
											<tr>
												<th scope="row">Request Time</th>
												<td></td>
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
												<td>${requestInfo.requester.fullName}</td>
											</tr>
											<#-- /Requester -->

											<#-- Position and Portfolio -->
											<tr>
												<th scope="row">Position and Portfolio</th>
												<td id="eventPortfolio" style="background-color:#${requestInfo.requesterPortfolio.color}">
													${requestInfo.requesterPosition}, ${requestInfo.requesterPortfolio.name}
												</td>
											</tr>
											<#-- /Position and Portfolio -->
													
										</table>
										<#-- /Basic Info -->

								</div>
							</div>
							<#-- /Request Info -->
							
						</div>
						<#-- /Information area -->
											
						<#-- Col 2 -->
						<div class="col-md-6">	

						<#-- Response -->
							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-reply"></i>&nbsp;Make a decision
									</h3>
								</div>
								<div class="card-body">

									<#-- Approver Role -->
									<div class="form-group row">
										<label for="approverRole" class="col-sm-2 col-form-label">
											Role&nbsp;<span class="required-symbol">*</span>
											</label>
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												
												<select class="form-control select2bs4" name="approverRole" id="approverRole"></select>
											</div>
											<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
										</div>
									</div>
									<#-- Approver Role -->

									<#-- Decision -->
									<div class="form-group row">
										<label for="graphicsApprovalOption" class="col-sm-2">
											Decision&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="form-group clearfix">
												<div class="icheck-success d-inline mr-2">
													<input type="radio" id="graphicsApprovalApproved" name="graphicsApprovalOption" value="1">
													<label for="graphicsApprovalApproved">Approved</label>
												</div>
												<div class="icheck-danger d-inline mr-2">
													<input type="radio" id="graphicsApprovalDeclined" name="graphicsApprovalOption" value="0">
													<label for="graphicsApprovalDeclined">Declined</label>
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
									<div class="right-div" role="group">
										<button type="button" class="btn btn-outline-primary" onclick="decide();">
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

	<#-- JS for Graphics Approval -->
	<script type="text/javascript" src="/static/js/etwig/graphics-approval.js?ver=${app.appVersion}"></script>
	
	<script>
	

		$(document).ready(function() {
			$('input[type=radio][name=graphicsApprovalOption]').change(function() {
				setAssetsUpload(this.value);
			});

			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			var myPositions = getMyPositions();
			for (var key in myPositions) {
  				$("#approverRole").append('<option value="' + myPositions[key].userRoleId + '">' + myPositions[key].position + ', ' + myPositions[key].portfolioName + '</option>');
			}
		});
	
	</script>

</body>
</html>