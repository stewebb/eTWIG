<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The event calendar/planner page.
   -->

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- CSS and JS for event calendar. https://github.com/vkurko/calendar -->
	<link rel="stylesheet" href="/static/css/event-calendar.min.css">
	<script src="/static/js/event-calendar.min.js"></script>
		
	<#-- Custom JS for eTWIG calendar and datepicker. -->
	<script src="/static/js/etwig-calendar.js"></script>
	
	<title>Event Calendar - eTWIG Administration Portal</title>
</head>

<body class="sidebar-mini layout-fixed">

	<#include "../_includes/sidebar.ftl">
	<#include "../_includes/modal.ftl">

	<#-- Content Wrapper. -->
  	<div class="content-wrapper">
  	
    	<#-- Page header -->
    	<section class="content-header">
      		<div class="container-fluid">
        		<div class="row mb-2">
          			<div class="col-sm-6">
            			<h1 class="bold-text">Event Calendar / Planner</h1>
          			</div>
          			<div class="col-sm-6">
            			<ol class="breadcrumb float-sm-right">
              				<li class="breadcrumb-item"><a href="/">Home</a></li>
              				<li class="breadcrumb-item">Events</li>
              				<li class="breadcrumb-item active"><a href="/events/calendar">Calendar</a></li>
            			</ol>
          			</div>
        		</div>
      		</div>
    	</section>

    	<#-- Options and Calendar -->
    	<section class="content">
      		<div class="container-fluid">
        		<div class="row">
          			<div class="col-md-3">
            			<div class="sticky-top mb-3">
              				
              				<#-- Options -->
              				<div class="card card-primary">
               					<div class="card-header">
                  					<h3 class="card-title">
                  						<i class="fa-regular fa-gear"></i>&nbsp;Options
                  					</h3>
                				</div>
                				<div class="card-body">
                					
                					<#-- Date Options -->
                  					<div class="mb-3 btn-group">
  										<button type="button" class="btn btn-outline-primary" onclick="currentDate=currentDate.last().month();changeCalendar();">
  											<i class="fa-solid fa-backward"></i>&nbsp;Last Month
  										</button>
  										<button type="button" class="btn btn-outline-primary" onclick="currentDate=Date.today();changeCalendar();">
  											<i class="fa-solid fa-rotate"></i>&nbsp;Reset
  										</button>
  										<button type="button" class="btn btn-outline-primary" onclick="currentDate=currentDate.next().month();changeCalendar();">
  											<i class="fa-solid fa-forward"></i>&nbsp;Next Month
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
        							
        							<#-- Add event -->
        							<hr class="mt-3 mb-3"/>
        							<button type="button" class="btn btn-block btn-outline-primary" onclick="addEventBtn();">
  										<i class="fa-solid fa-add"></i>&nbsp;Add Event
  									</button>
                				</div>
              				</div>
            			</div>
          			</div>
          
          			<#-- Calendar -->
          			<div class="col-md-9">
            			<div class="card card-primary">
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
        		</div>
			</div>
    	</section>
	</div>
  
  	<script>
		createDatePicker("#wrapper", "#datepicker-input", "#select-month");
		createCalendar("etwig-public-calendar", currentDate.toString('yyyy-MM-dd'));
    </script>
</body>
</html>