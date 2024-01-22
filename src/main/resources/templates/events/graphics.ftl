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
									<h5 class="mt-3 mb-2 bold-text">
										<i class="fa-solid fa-circle-info"></i>&nbsp;Event Information
									</h5>

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
											<td>${eventInfo.organizerName}</td>
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
									<#-- /Event Information -->

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
									
									<#-- Current Graphics -->
									<h5 class="mt-3 mb-2 bold-text">
										<i class="fa-solid fa-icons"></i>&nbsp;Current Graphics
									</h5>
								</div>
								<#-- Col 1 -->
								
							
								<#-- Col 2 -->
								<div class="col-md-6">
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
</body>
</html>