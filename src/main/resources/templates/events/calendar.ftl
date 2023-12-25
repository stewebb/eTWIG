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

    	<#-- Filter, Options and Calendar -->
    	<section class="content">
      		<div class="container-fluid">
        		<div class="row">
          			<div class="col-md-3">
            			<div class="sticky-top mb-3">
            			
            				<#-- Filter -->
              				<div class="card">
                				<div class="card-header">
                  					<h3 class="card-title">
                  						<i class="fa-solid fa-filter"></i>&nbsp;Filter
                  					</h3>
                				</div>
                				<div class="card-body">

              					</div>
              				</div>
              				
              				<#-- Date Options -->
              				<div class="card card">
               					<div class="card-header">
                  					<h3 class="card-title">
                  						<i class="fa-regular fa-clock"></i>&nbsp;Date Options
                  					</h3>
                				</div>
                				<div class="card-body">
                					
                  					<div class="mb-3 btn-group">
  										<button type="button" class="btn btn-outline-primary" id="last-mth-btn">
  											<i class="fa-solid fa-backward"></i>&nbsp;Last Month
  										</button>
  										<button type="button" class="btn btn-outline-primary" id="reset-btn">
  											<i class="fa-solid fa-rotate"></i>&nbsp;Reset
  										</button>
  										<button type="button" class="btn btn-outline-primary" id="next-mth-btn">
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
                				</div>
              				</div>
            			</div>
          			</div>
          
          			<#-- Calendar -->
          			<div class="col-md-9">
            			<div class="card">
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
		createPublicCalendar("etwig-public-calendar", currentDate);
		dateOptions("#last-mth-btn", "#reset-btn", "#next-mth-btn");
    </script>
</body>
</html>