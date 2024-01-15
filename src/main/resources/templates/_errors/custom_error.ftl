<#-- 
	eTWIG - The event management software for university communities.
	copyright: Copyright (c) 2024 Steven Webb
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
	<#include "../_includes/header/head.ftl">
	<title>Error - ${app.appName}</title>
</head>

<body class="sidebar-mini layout-fixed">
	<#include "../_includes/header/body_start.ftl">
	
	
	<#if embedded == false>
		<#assign navbar = "OTHER">
		<#include "../_includes/sidebar.ftl">
	</#if>
	 
	 
	<#-- Content Wrapper -->
  	<div class="<#if embedded == false>content-</#if>wrapper mt-3">
  	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
			
				<#-- Error description -->
				<div class="card card-warning card-outline">
				
					<div class="card-header">
						<h3 class="card-title bold-text text-warning">
							<i class="fa-solid fa-circle-exclamation"></i>&nbsp;An error happenes
						</h3>
					</div>
					
					<div class="card-body">
					
						<div class="mb-3">
							<span class="bold-text">Description:</span>&nbsp;
							<#if reason?has_content>${reason}</#if>
						</div>
						
						<div class="d-flex justify-content-center">
							<button class="btn btn-outline-primary" onclick="window.history.back();">
								<i class="fa-solid fa-rotate-left"></i>&nbsp;Go Back
							</button>
						</div>
						
					</div>
				</div>
			</div>
		</section>

	</div>
	
	<#include "../_includes/header/body_end.ftl">
	
</body>
</html>