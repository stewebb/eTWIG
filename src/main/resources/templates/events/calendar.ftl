<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The event calendar/planner page.
   -->
   
<#assign navbar = "CALENDAR">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Event Calendar - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
  	<#include "../_includes/header/body_start.ftl">

	<#-- Main Wrapper -->
	<div class="wrapper">

		<#-- Navbar -->
		<#if !embedded>
			<#include "../_includes/navbar.ftl">
		</#if>
		<#-- /Navbar -->
	
		<#include "../_includes/modal.ftl">

		<#-- Content Wrapper. -->
  		<div class="content-wrapper">
  	
    		<#-- Page header -->
    		<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="bold-text">Event Calendar</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active"><a href="/events/calendar">Events</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- /Page header -->
			
			<#-- Options and Calendar -->
			<section class="content">
				<div class="container-fluid">
					<div class="row col-12">

						<#-- Options -->
						<div class="col-md-3">
							<div class="sticky-top mb-3">

								<div class="card card-primary card-outline">
									<div class="card-header">
										<h3 class="card-title">
											<i class="fa-regular fa-gear"></i>&nbsp;Options
										</h3>
									</div>
									<div class="card-body">
										
										<#-- Date Options -->
										<div class="mb-3 btn-group w-100">
											<button type="button" class="btn btn-outline-primary" onclick="currentDate=currentDate.last().month();changeCalendar();">
												<i class="fa-solid fa-backward"></i>&nbsp;Last
											</button>
											<button type="button" class="btn btn-outline-primary" onclick="currentDate=Date.today();changeCalendar();">
												<i class="fa-solid fa-rotate"></i>&nbsp;Reset
											</button>
											<button type="button" class="btn btn-outline-primary" onclick="currentDate=currentDate.next().month();changeCalendar();">
												<i class="fa-solid fa-forward"></i>&nbsp;Next
											</button>
										</div>
										
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text" id="basic-addon1"><i class="fa-regular fa-calendar-days"></i></span>
											</div>
											<input type="text" id="datepicker-input" aria-label="Date-Time" class="form-control">
											<span class="input-group-append">
												<button type="button" class="btn btn-primary btn-flat" id="select-month">Go!</button>
											</span>
										</div>
										<div id="wrapper" class="datepicker"></div>
										<#-- /Date Options -->
										
										<#-- Event Options-->
										<hr class="mt-3 mb-3"/>
										
										<#if true>
											<button type="button" class="btn btn-block btn-outline-primary mb-3" onclick="addEventBtn();">
												<i class="fa-solid fa-add"></i>&nbsp;Add Event
											</button>	
										</#if>
										<#-- /Event Options-->
										
									</div>
								</div>
							</div>
						</div>
						<#-- Options -->

						<#-- Calendar -->
						<div class="col-md-9">
							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-regular fa-calendar"></i>&nbsp;Calendar
									</h3>
								</div>
								<div class="card-body p-0 row">
									<div id="etwig-public-calendar" class="col"></div>
								</div>
							</div>
						</div>
						<#-- Calendar -->
						
					</div>
				</div>
			</section>
		</div>
	
  </div>
  	<#include "../_includes/footer.ftl">
  	
  	<#include "../_includes/header/body_end.ftl">
  	
  	<#-- CSS and JS for event calendar. https://github.com/vkurko/calendar -->
	<link rel="stylesheet" href="/static/css/vendor/event-calendar.min.css">
	<script src="/static/js/vendor/event-calendar.min.js"></script>
		
	<#-- Custom JS for eTWIG calendar and datepicker. -->
	<script src="/static/js/etwig/calendar.js"></script>
	
  	<script>
		createDatePicker("#wrapper", "#datepicker-input", "#select-month");
		createCalendar("etwig-public-calendar", currentDate.toString('yyyy-MM-dd'));
    </script>
    
</body>
</html>