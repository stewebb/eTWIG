<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "GRAPHICS_APPROVAL">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Requests Approval - ${app.appName}</title>
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
	            				Graphics Request Approval
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/graphics">Graphics</a></li>
								<li class="breadcrumb-item"><a href="/graphics/approval/list">Request Approval</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/approval/decision?requestId=${requestInfo.id}">Decision</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
					<div class="row col-12">

						<#-- Information area -->
						<div class="col-md-6">

							<#-- Event Info -->
							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
									</h3>
								</div>
								<div class="card-body">

									<#assign eventInfo = requestInfo.event>
									<#include "../_includes/events/graphics_info.ftl">
								</div>
							</div>
							<#-- /Event Info -->
							

							
						</div>
						<#-- /Information area -->
											
						<#-- Col 2 -->
						<div class="col-md-6">	

						</div>
						<#-- /Col 2 -->

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

	<#-- Custom JS for graphics request
	<script src="/static/js/etwig/graphics-request.js"></script> -->
	
	<script>
	

		//$(document).ready(function() {
		//	updateTextColor($('#eventPortfolio'));
		//});
	
	</script>

</body>
</html>