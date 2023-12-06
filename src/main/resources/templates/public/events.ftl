<!DOCTYPE html>
<html>
	<head>
	<#include "../_header.ftl">
	
	
		<!-- CSS for public submodule pages -->
		<link rel="stylesheet" href="/static/css/public.css">
	
		<!-- CSS and JS for event calendar framework. https://github.com/vkurko/calendar -->
		<link rel="stylesheet" href="/static/css/event-calendar.min.css">
		<script src="/static/js/event-calendar.min.js"></script>
		
		<!-- CSS and JS for toastui date picker.-->
		<link rel="stylesheet" href="/static/css/tui-date-picker.min.css">
		<script src="/static/js/tui-date-picker.min.js"></script>
		
		<title>Event Calendar - eTWIG Public Module</title>
	</head>

	<body>
	<#include "./_navbar.ftl">
	<p>&nbsp;</p>
	
	<!-- Search box for event calendar. -->
	<#include "../_alerts.ftl">
    
	<main class="row etwig-main">
    	<div id="etwig-public-calendar" class="col"></div>
	</main>
	
	<!-- Custom JS for eTWIG calendar. -->
	<script src="/static/js/etwig-calendar.js"></script>
	
	<p>&nbsp;</p>
	<div id="event-search" class="etwig-main border border-public rounded">
		<div class="etwig-box">
			<h4><strong>Filter</strong></h4>
			
			<div class="container">
        		<div class="row">
        			<div class="col-md-6">
        				<div class="input-group mb-3">
  							<div class="input-group-prepend">
    							<span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-briefcase"></i></span>
  							</div>
  							<select class="form-control">
  								<option>Default select</option>
							</select>
						</div>            			
           			 </div>
           			 
           			 <div class="col-md-6">
           			 	<div class="input-group mb-3">
  							<div class="input-group-prepend">
    							<span class="input-group-text" id="basic-addon1"><i class="fa-regular fa-calendar"></i></span>
  							</div>
  							<input type="text" id="datepicker-input" aria-label="Date-Time" class="form-control">
						</div>
        				<div id="wrapper" style="margin-top: -1px;"></div>
            		</div>
        		</div>
        		
        		<div class="row">
        		<div class="col-md-2 col-10">
            			<button class="btn btn-outline-primary my-2 my-sm-0" type="submit"> <i class="fa-solid fa-filter"></i> &nbsp;Go</button>
					</div>
        		<div>
    	</div>

									
												
																		<!--
    
			<form>
			
				<table>
					<tr>
						<td>Event Week</td>
						<td>

        					<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
            					<input type="text" id="datepicker-input" aria-label="Date-Time">
            					<span class="tui-ico-date"></span>
        					</div>
        					<div id="wrapper" style="margin-top: -1px;"></div>
						</td>
					</tr>
					<tr>
						<td>Portfolio</td>
						<td>
							<select class="form-control">
  								<option>Default select</option>
							</select>
						</td>
				</table>
  
			
    			<button class="btn btn-outline-primary my-2 my-sm-0" type="submit"> <i class="fa-solid fa-filter"></i> &nbsp;Search</button>
  			</form>
  			-->
		</div>
	</div>
	
	<script>
	        var datepicker = new tui.DatePicker('#wrapper', {
            date: new Date(),
            input: {
                element: '#datepicker-input',
                format: 'yyyy-MM-dd'
            }
        });
    </script>
    
	
	<#include "../_footer.ftl">
</body>
</html>