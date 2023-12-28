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
  	<div class="<#if embedded == false>content-</#if>wrapper">
  	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid" id="addEventForm" action="/events/add" method="post">
			
				</div>
		</section>

	</div>
</body>
</html>