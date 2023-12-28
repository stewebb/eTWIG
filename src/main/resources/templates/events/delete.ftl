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

<body class="sidebar-mini layout-fixed">

	<#if embedded == false>
		<#include "../_includes/sidebar.ftl">
	</#if>
	<#assign addOnClickOption = embedded ? then("true", "false")>
	<#assign link = "delete?eventId=" + eventId + "&embedded=" + addOnClickOption>
	
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
				<div class="card card-danger card-outline mb-3">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-eraser"></i>&nbsp;Delete Event
						</h3>
					</div>

					<div class="card-body">
						<div class="mb-3 bold-text text-danger">
							This operation will delete the event and all related data PERMANETLY. 
						</div>
						<div class="callout callout-danger">
							<h5 class="bold-text mb-3">Be Careful!</h5>
							The deletion operation cannot be undone.
						</div>
						
						<div class="btn-group" role="group" style="float: right;">
						
							<#-- Delete -->
							<button type="button" class="btn btn-outline-danger" onclick="deteteEvent(${addOnClickOption});">
								<i class="fa-regular fa-trash"></i>&nbsp;Delete event
							</button>

							<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;Cancel
							</button>
						</div>
					</div>
				</div>	
				
		</section>

	</div>

	<#-- Footer -->
	<#if embedded == false>
		<#include "../_includes/footer.ftl">
	</#if>
</body>
</html>