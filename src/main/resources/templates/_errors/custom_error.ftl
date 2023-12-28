<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The custom error page.
	This page display errors that not caused by the software itself, but mainly the user input.
	e.g., invalid eventId
   -->

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<title>Error - ${app.appName}</title>
</head>

<body class="sidebar-mini layout-fixed">

	<#if embedded == false>
		<#include "../_includes/sidebar.ftl">
	</#if>
	
	<#-- Content Wrapper -->
  	<div class="<#if embedded == false>content-</#if>wrapper mt-3">
  	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
			
				<#-- Error reason -->
				<div class="card card-warning card-outline">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-circle-exclamation"></i>&nbsp;An error happens.
						</h3>
					</div>
					<div class="card-body">
						<span class="bold-text">Reason:</span>&nbsp;
						<#if reason?has_content>${reason}</#if>
					</div>
				</div>
				
				<#-- Options -->
				<div class="card">
					<div class="card-body mr-2 d-flex justify-content-sm-center">
				
						<a href="/" class="btn btn-outline-primary mb-1 mr-1">
							<i class="fa-solid fa-home"></i>&nbsp;Home
						</a>
					
						<button class="btn btn-outline-secondary mb-1 mr-1" onclick="window.history.back();">
							<i class="fa-solid fa-rotate-left"></i>&nbsp;Back
						</button>
						
						<button class="btn btn-outline-secondary mb-1 mr-1" onclick="window.location.reload();">
							<i class="fa-solid fa-rotate"></i>&nbsp;Refresh
						</button>
					</div>
				</div>
			
			</div>
		</section>

	</div>
</body>
</html>