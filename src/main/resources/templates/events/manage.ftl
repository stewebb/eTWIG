<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The add/edit event page.
   -->
   
<#assign navbar = "CALENDAR">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Manage Event - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	<#include "../_includes/header/body_start.ftl">

	<#-- Main Wrapper -->
	<div class="wrapper">

		<#-- Navbar -->
		<#include "../_includes/navbar.ftl">
		<#-- /Navbar -->

		<#-- Content Wrapper. -->
  		<div class="content-wrapper">
  	
			<#-- Page header -->
	    	<section class="content-header">
	      		<div class="container-fluid">
	        		<div class="row mb-2">
	          			<div class="col-sm-6">
	            			<h1 class="bold-text" id="eventPageTitle">
	            				Manage Event: ${eventInfo.name}
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/events/calendar">Events</a></li>
	              				<li class="breadcrumb-item active"><a href="/events/manage?eventId=${eventInfo.id}">Manage</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
    	
    		<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
				
					<div class="card card-primary card-outline">

						<#-- Tab header -->
						<div class="card-header p-0">
							<ul class="nav nav-pills ml-auto p-2">

								<#-- Back -->							
								<li class="nav-item">
									<a class="nav-link" href="/events/calendar">
										<i class="fa-solid fa-arrow-left"></i>&nbsp;Back
									</a>
								</li>
								<#-- Back -->	

								<#-- View/Edit -->	
								<li class="nav-item">
									<a class="nav-link active" href="#edit" data-toggle="tab">
										<i class="fa-solid fa-pen-to-square"></i>&nbsp;Edit
									</a>
								</li>
								<#-- /View/Edit -->	

								<#-- Graphics Request -->	
								<li class="nav-item">
									<a class="nav-link" href="#request" data-toggle="tab">
										<i class="fa-solid fa-image"></i>&nbsp;Graphics Request
									</a>
								</li>
								<#-- /Graphics Request -->	

								<!--<li class="nav-item"><a class="nav-link" href="#tab_3" data-toggle="tab">Tab 3</a></li>-->
							</ul>
						</div>
						<#-- Tab header -->

						<div class="card-body">
							<div class="tab-content">
								<div class="tab-pane active" id="edit"></div>

								<div class="tab-pane" id="request"></div>

								<!--<div class="tab-pane" id="tab_3"></div>-->

							</div>

						</div>
					</div>

				</div>
			</section>
			<#-- /Main area -->
		
		</div>
		<#-- /Content Wrapper -->

	</div>
	<#-- Main Wrapper -->
	
	<#-- Footer -->
	<#include "../_includes/footer.ftl">
	
	<#include "../_includes/header/body_end.ftl">
	
	<#-- Resources -->

	<#-- Custom JS for adding/editing events-->
	<script src="/static/js/etwig/events.js"></script>
	
   	<#-- Post Scripts -->
	<script>

		$(document).ready(function() {
		}
		
    </script>
</body>
</html>