<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The delete event page.
   -->

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">

	<#-- Custom JS for events operations-->
	<script src="/static/js/etwig-events.js"></script>

	<title>Delete Event - ${app.appName}</title>
</head>

<#-- Convert the "Embedded" boolean to String -->
<#assign isEmbeddedStr = embedded ?string('true', 'false')>

<#-- In delete mode, the link of this page is always "/event/delete", but two parameters eventId and embedded are given. -->
<#assign link = "delete?eventId=" + eventId + "&embedded=" + isEmbeddedStr>

<#assign cancelBtn = embedded ? then("Close", "Cancel")>

<body class="sidebar-mini layout-fixed">

	<#if embedded == false>
		<#include "../_includes/sidebar.ftl">
	</#if>
	
	<#-- Content Wrapper -->
  	<div class="<#if embedded == false>content-</#if>wrapper">
  	
		<#-- Page header -->
		<#if embedded == false>
    		<section class="content-header">
      			<div class="container-fluid">
        			<div class="row mb-2">
          				<div class="col-sm-6">
            				<h1 class="bold-text">Delete Event</h1>
          				</div>
          				<div class="col-sm-6">
            				<ol class="breadcrumb float-sm-right">
              					<li class="breadcrumb-item"><a href="/">Home</a></li>
              					<li class="breadcrumb-item">Events</li>
              					<li class="breadcrumb-item active"><a href="/events/${link}">Delete Event</a></li>
            				</ol>
          				</div>
        			</div>
      			</div>
    		</section>
    	</#if>
    	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
			
				<#-- Delete -->
				<div class="card card-primary card-outline mb-3">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-eraser"></i>&nbsp;Delete Event
						</h3>
					</div>

					<div class="card-body">
					
						<#-- Warning information -->
						<div class="mb-3 bold-text text-danger">
							This operation will delete the event and all related data PERMANENTLY. 
						</div>
						
						<#-- Event information -->
						<div class="mb-3 table-responsive">
							<p>The event that you are going to delete: </p>
							<table class="table table-bordered table-nonfluid d-flex justify-content-center">
								
								<#--  Event Id -->
    							<tr>
      								<th scope="row">Event Id</th>
      								<td>${eventDetails.details.Id}</td>
    							</tr>
    							
    							<#--  Event Name -->
 								<tr>
      								<th scope="row">Event Name</th>
      								<td>${eventDetails.details.name}</td>
    							</tr>
    							
    							<#--  Event Start Time -->
    							<tr>
      								<th scope="row">Event Start Time</th>
      								<td>${eventDetails.details.startDateTime}</td>
    							</tr>
    							
    							<#--  Portfolio -->
    							<tr>
      								<th scope="row">Portfolio</th>
      								<td style="background-color:#${eventDetails.portfolio.color}">${eventDetails.portfolio.name}</td>
    							</tr>
    							
    							<#--  Organizer -->
    							<tr>
      								<th scope="row">Organizer</th>
      								<td>${eventDetails.user.fullName}</td>
    							</tr>
							</table>
						</div>
						
						<#-- Display different callouts based on delete permission -->
						<#if editPermission>
							<div class="callout callout-danger">
								<h5 class="bold-text mb-3">Be Careful!</h5>The deletion operation cannot be undone.
							</div>
						<#else>
							<#assign calloutTitle = "No delete permission">
							<#include "../_includes/event_noPermission_callout.ftl">
						</#if>
						
						<#-- Confirm checkbox -->
						<#if editPermission>
							<div class="icheck-primary d-inline">
								<input type="checkbox" id="confirmDeletion">
								<label for="confirmDeletion"">Confirm deletion</label>
							</div>
						</#if>
						
						<#-- Delete option buttons -->
						<div class="btn-group" role="group" style="float: right;">
						
							<#-- Delete -->
								<#if editPermission>
								<button type="button" class="btn btn-outline-danger" id="deleteEventBtn" onclick="deleteEvent(${isEmbeddedStr});" disabled>
									<i class="fa-regular fa-trash"></i>&nbsp;Delete event
								</button>
							</#if>
							
							<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;${cancelBtn}
							</button>
						</div>
					</div>
				</div>	
				
		</section>
	</div>

	<script>
		deleteEventCheckboxOnChange();
	</script>
	<#-- Footer -->
	<#if embedded == false>
		<#include "../_includes/footer.ftl">
	</#if>
</body>
</html>