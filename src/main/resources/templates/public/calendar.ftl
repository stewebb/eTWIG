<!DOCTYPE html>
<html>
	<head>
	<#include "../_includes/_header.ftl">
	
	
		<!-- CSS for public submodule pages -->
		<link rel="stylesheet" href="/static/css/public.css">
	
		<!-- CSS and JS for event calendar framework. https://github.com/vkurko/calendar -->
		<link rel="stylesheet" href="/static/css/event-calendar.min.css">
		<script src="/static/js/event-calendar.min.js"></script>
		
		<!-- CSS and JS for toastUI date picker.-->
		<link rel="stylesheet" href="/static/css/tui-date-picker.min.css">
		<script src="/static/js/tui-date-picker.min.js"></script>
		
		<!-- Custom JS for eTWIG calendar and datepicker. -->
		<script src="/static/js/etwig-calendar.js"></script>
		
		<title>Event Calendar - eTWIG Public Module</title>
	</head>

	<body>
	<#include "./_navbar.ftl">
	
	<#include "../_includes/_alerts.ftl">
	<#include "../_includes/_modal.ftl">
	
	<!-- Event filter -->
	<div id="event-search" class="etwig-main border border-public rounded">
		<div class="etwig-box">
			<h4 class="header-text bold">
				<i class="fa-solid fa-filter"></i>&nbsp;Filter
			</h4>
			
			<form class="collapse show" id="collapseFilterBox">
  				<div class="form-group row">
    				<label for="inputEmail3" class="col-sm-2 col-form-label bold">Portfolio</label>
    				<div class="col-sm-10">
      					<div class="input-group mb-3">
  							<div class="input-group-prepend">
    							<span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-briefcase"></i></span>
  							</div>
  							<select class="form-control">
  								<option>Default select</option>
							</select>
						</div>  
    				</div>
  				</div>
  				
  				<div class="form-group row">
    				<label for="inputPassword3" class="col-sm-2 col-form-label bold">Date</label>
    				<div class="col-sm-10">
      					<div class="input-group mb-3">
  							<div class="input-group-prepend">
    							<span class="input-group-text" id="basic-addon1"><i class="fa-regular fa-calendar"></i></span>
  							</div>
  							<input type="text" id="datepicker-input" aria-label="Date-Time" class="form-control">
						</div>
        				<div id="wrapper" style="margin-top: -1px;   position: absolute;  z-index: 1;"></div>
    				</div>
  				</div>
  				
  				<fieldset class="form-group">
    				<div class="row">
      					<legend class="col-form-label col-sm-2 pt-0 bold">Recurring</legend>
      					<div class="col-sm-10">
        					<div class="form-check">
          						<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="all" checked>
          						<label class="form-check-label" for="gridRadios1">All events</label>
        					</div>
        					<div class="form-check">
          						<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="single">
         						<label class="form-check-label" for="gridRadios2">Single-time events only</label>
        					</div>
        					<div class="form-check">
          						<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios3" value="recurring">
          						<label class="form-check-label" for="gridRadios3">Recurring events only</label>
        					</div>
      					</div>
    				</div>
  				</fieldset>
			</form>
    		
    		<div style="text-align:right;">
    			<a class="btn btn-outline-secondary my-2 my-sm-0" data-toggle="collapse" href="#collapseFilterBox" role="button" aria-expanded="false" aria-controls="collapseFilterBox"> 
    				<i class="fa-solid fa-eye"></i> &nbsp;Show/Hide
    			</a>
    			<a class="btn btn-outline-success my-2 my-sm-0" href="#"> 
    				<i class="fa-solid fa-check"></i> &nbsp;Apply
    			</a>
    		</div>
    	</div>
	</div>
	
	<p>&nbsp;</p>
	<!-- Event portfolio -->
	<div id="event-portfolio" class="etwig-main border border-public rounded">
		<#include "./_portfolio.ftl">
    </div>
    
    <p>&nbsp;</p>
	<main class="row etwig-main border border-public rounded">
		<div id="etwig-public-calendar" class="col"></div>
	</div>
		
    	
	</main>
	
	<script>
		createDatePicker("#wrapper", "#datepicker-input");
		createPublicCalendar("etwig-public-calendar");
    </script>

	<#include "../_includes/_footer.ftl">
</body>
</html>