<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "GRAPHICS_REQUEST_VIEW">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Request - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">

	<#include "../_includes/header/body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<div class="content-wrapper">
	
		<#-- Page header -->
		<#-- <#if !embedded> -->
    		<section class="content-header">
      			<div class="container-fluid">
        			<div class="row mb-2">
          				<div class="col-sm-6">
            				<h1 class="bold-text">Graphics (Banner) Request</h1>
          				</div>
          				<div class="col-sm-6">
            				<ol class="breadcrumb float-sm-right">
              					<li class="breadcrumb-item"><a href="/">Home</a></li>
              					<li class="breadcrumb-item">Graphics</li>
              					<li class="breadcrumb-item"><a href="/graphics/request/view">Graphics Request</a></li>
              					<li class="breadcrumb-item active"><a href="/graphics/request/event">Event</a></li>
            				</ol>
          				</div>
        			</div>
      			</div>
    		</section>
		<#-- </#if>-->
		<#-- /Page header -->
		
	    <#-- Main area -->
    	<section class="content">
			<div class="row">
		
				<#-- Event Information -->
				<div class="col-md-6">
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-circle-info"></i>&nbsp;Event Information
							</h3>
						</div>
							
						<div class="card-body">
								
						</div>
					</div>
				</div>
				<#-- /Event Information -->		
					
				<#-- New request/Follow-up -->		
				<div class="col-md-6">
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-plus"></i>&nbsp;New request
							</h3>
						</div>
							
						<div class="card-body">
					
							<#-- Returning Date -->
							<div class="form-group">
								<label for="expectedDate">
									Expect date to get your graphics.&nbsp;<span class="required-symbol">*</span>
								</label>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-clock"></i>
										</span>
									</div>
									<input type="text" class="form-control" placeholder="Returning date for getting your graphic." id="returningDate">
								</div>
								<div id="eventEndWrapper" class="datepicker"></div>
							</div>			
							<#-- Returning Date -->
								
							<#-- Comment -->
							<div class="form-group">
								<label for="comment">Additional Comments</label>
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-comment-dots"></i>
										</span>
									</div>
									<textarea class="form-control fixed-textarea" placeholder="Additional comments (Optional, maximum length is 511 characters.)" id="comment" maxlength="511" rows="8"></textarea>
								</div>
							</div>			
							<#-- /Comment -->
						
							<#-- Submit -->
							<div class="right-div" role="group">
								<button type="button" class="btn btn-outline-primary">
									<i class="fa-regular fa-check"></i>&nbsp;Submit
								</button>
							</div>
							<#-- /Submit -->
						</div>
					</div>
				</div>
				<#-- /New request/Follow-up -->
				
			</div>
					
			<#-- Request history -->
			
			<div class="card card-primary card-outline">
				<div class="card-header">
					<h3 class="card-title">
						<i class="fa-solid fa-clock-rotate-left"></i>&nbsp;Request history
					</h3>
				</div>
							
				<div class="card-body">
					<div class="timeline">

						<#-- Latest -->
						<div class="time-label">
							<span class="bg-primary">Latest</span>
						</div>

						<div>
							<i class="fas fa-envelope bg-primary"></i>
							<div class="timeline-item">
								<span class="time"><i class="fas fa-clock"></i> 12:05</span>
								<h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
								<div class="timeline-body">
									xxxxxxx
								</div>

								<#-- Action Buttons. -->
								<div class="timeline-footer">
									<a class="btn btn-primary btn-sm">Read more</a>
									<a class="btn btn-danger btn-sm">Delete</a>
								</div>
								<#-- /Action Buttons. -->
								
							</div>
						</div>

					</div>			
				</div>
			</div>	
			<#-- /Request history -->
				
		</section>
		<#-- /Main area -->

	</div>
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
</body>
</html>