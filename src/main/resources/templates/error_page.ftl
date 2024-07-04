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

	<#assign navbar = "ERROR">
	
<!DOCTYPE html>
<html>
<head>
	<#include "./_includes/header/head.ftl">
	<title>Error - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	<#include "./_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">

		<#-- Navbar -->
		<#include "./_includes/navbar.ftl">
		<#-- /Navbar -->

		<#-- Content Wrapper. -->
  		<div class="content-wrapper">
  	
			<#-- Error description -->
			<div class="card card-warning card-outline">
				
				<div class="card-header">
					<h3 class="card-title bold-text text-warning">
						<i class="fa-solid fa-circle-exclamation"></i>&nbsp;An error happens
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
		<#-- /Content Wrapper -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "./_includes/header/body_end.ftl">
	
</body>
</html>