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
		<#include "../_includes/navbar.ftl">

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
								<li class="breadcrumb-item active"><a href="/events/calendar.do">Events</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- /Page header -->
			
			<#-- Functions -->
			<section class="content">
				<div class="container-fluid" id="eventMainComponent">
					<div class="row col-12">

						<#-- Left Bar -->
						<div class="col-md-3" id="left-column">
							<div class="mb-3">

								<#-- Options -->
								<div class="card card-primary card-outline">
									<div class="card-header">
										<h3 class="card-title">
											<i class="fa-regular fa-gear"></i>&nbsp;Options
										</h3>
									</div>
									<div class="card-body">
										
										<#-- Date Options -->
										<div class="mb-3 btn-group w-100">
											<button type="button" class="btn btn-outline-secondary" onclick="changeCurrentDate(-1);">
												<i class="fa-solid fa-backward"></i>&nbsp;Last
											</button>
											<button type="button" class="btn btn-outline-secondary" onclick="changeCurrentDate(0);">
												<i class="fa-solid fa-rotate"></i>&nbsp;Reset
											</button>
											<button type="button" class="btn btn-outline-secondary" onclick="changeCurrentDate(1);">
												<i class="fa-solid fa-forward"></i>&nbsp;Next
											</button>
										</div>
										<#-- /Date Options -->

										<#-- Calendar View -->
										<div class="form-group row">
											<label for="eventRecurrence" class="col-xl-4 col-form-label">View</label>
											<div class="col-xl-8">
												<div class="form-group clearfix">

													<#-- Weekly -->
													<div class="icheck-primary">
														<input type="radio" id="weeklyView" name="calendarView" value="0" checked="">
														<label for="weeklyView">Weekly</label>
													</div>
													<#-- /Weekly -->

													<#-- Monthly -->
													<div class="icheck-primary">
														<input type="radio" id="monthlyView" name="calendarView" value="1">
														<label for="monthlyView">Monthly</label>
													</div>
													<#-- /Monthly -->

												</div>
											</div>
										</div>
										<#-- /Calendar View -->

										<#-- Date Options -->	
										<div class="form-group row">
											<label for="eventRecurrence" class="col-xl-4 col-form-label">Month</label>
											<div class="col-xl-8">
												<div class="input-group mb-2">
													<div class="input-group-prepend">
														<span class="input-group-text" id="basic-addon1"><i class="fa-regular fa-calendar-days"></i></span>
													</div>
													<input type="text" id="datepicker-input" aria-label="Date-Time" class="form-control">
												</div>
												<div id="wrapper" class="datepicker"></div>
											</div>
										</div>

										<#-- /Date Options -->	

										<#-- Portfolio -->
										<div class="form-group row">
											<label for="eventPortfolio" class="col-xl-4 col-form-label">Portfolio</label>
											<div class="col-xl-8 input-group">
									
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-briefcase"></i>
														</span>
													</div>
									
													<select class="form-control select2" name="eventPortfolio" id="eventPortfolio" onchange="updatePortfolio();">
        												<option value="-1">(All portfolios)</option>
														<#if portfolios?has_content>
        													<#list portfolios as portfolio>
																<option value="${portfolio.id}">
																	${portfolio.name}												
																</option>
															</#list>
        												</#if>
      												</select>
											</div>
										</div>
										<#-- /Portfolio -->		

										<#-- Recurrence-->
										<div class="form-group row">
											<label for="eventRecurrence" class="col-xl-4 col-form-label">Recurrence</label>
											<div class="col-xl-8">
												<div class="form-group clearfix">
										
													<#-- All-->
													<div class="icheck-primary">
														<input type="radio" id="allEvents" name="eventRecurrence" checked value="0">
														<label for="allEvents">All events</label>
													</div>
													<#-- /All-->
											
													<#-- Single time-->
													<div class="icheck-primary">
														<input type="radio" id="singleTime" name="eventRecurrence" value="-1">
														<label for="singleTime">Single Time</label>
													</div>
													<#-- /Single time-->

													<#-- Recurring-->
													<div class="icheck-primary">
														<input type="radio" id="recurring" name="eventRecurrence" value="1">
														<label for="recurring">Recurring</label>
													</div>
													<#-- /Recurring-->

												</div>
											</div>
										</div>
										<#-- Recurrence-->							
										
										<#-- Event Options-->
										<#if userPermission.eventsAccess>
											<hr class="mt-3 mb-3"/>
											<div class="btn-group w-100">
												<button type="button" class="btn btn-outline-primary" onclick="location.href='/events/add.do';">
													<i class="fa-solid fa-calendar-plus"></i>&nbsp;New Event
												</button>
												
												<button type="button" class="btn btn-outline-primary" onclick="location.href='/events/import.do';">
													<i class="fa-solid fa-file-import"></i>&nbsp;Import
												</button>
											</div>
												
										</#if>
										<#-- /Event Options-->
										
									</div>
								</div>
								<#-- /Options -->

								<#-- Legend -->
								<div class="card card-primary card-outline">
									<div class="card-header">
										<h3 class="card-title">
											<i class="fa-solid fa-location-crosshairs"></i>&nbsp;Legend
										</h3>
									</div>
									<div class="card-body">
										
										<#-- Portfolio -->
										<div class="row">
											<#list portfolios as portfolio>
												<div class="col-lg-6">
													<div class="legend-item" style="border: 1px solid #${portfolio.color};">
														<span style="color: #${portfolio.color}">
															${portfolio.name}
															<#if portfolio.abbreviation?has_content>
																&nbsp;(${portfolio.abbreviation})
															</#if>
														</span>
													</div>
												</div>
											</#list>
										</div>
										<#-- /Portfolio -->

										<div class="row bold-text">
											<div class="col-lg-6">Single Time Event</div>
											<div class="col-lg-6 font-italic">Recurring Event</div>
										</div>

									</div>
								</div>
								<#-- /Legend -->
								
							</div>
						</div>
						<#-- Left Bar -->

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
			<#-- /Functions -->

		</div>
  	</div>

  	<#include "../_includes/footer.ftl">
  	
  	<#include "../_includes/header/body_end.ftl">
  	
  	<#-- CSS and JS for event calendar. https://github.com/vkurko/calendar -->
	<link rel="stylesheet" href="/static/css/vendor/event-calendar.min.css">
	<script src="/static/js/vendor/event-calendar.min.js"></script>

	<#-- rrule.js -->
   	<script src="/static/js/etwig/recurrent.min.js?ver=${app.appVersion}"></script>
		
	<#-- Custom JS for eTWIG calendar and datepicker. -->
	<script src="/static/js/etwig/calendar.js?ver=${app.appVersion}"></script>
	
  	<script>
		createDatePicker("#wrapper", "#datepicker-input", "#select-month");
		createCalendar("etwig-public-calendar", currentDate.toString('yyyy-MM-dd'));

		$('#eventPortfolio').select2({
    		theme: 'bootstrap4'
		});

		$('input[type=radio][name=calendarView]').change(function() {
			calendarView = parseInt(this.value);
			changeCalendar();
		});

		$('input[type=radio][name=eventRecurrence]').change(function() {
			recurrenceMode = parseInt(this.value);
			changeCalendar();
		});
    </script>
    
</body>
</html>
