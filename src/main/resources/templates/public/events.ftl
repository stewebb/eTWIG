<!DOCTYPE html>
<html>
	<head>
	<#include "../_header.ftl">
	
	
		<!-- CSS for public submodule pages -->
		<link rel="stylesheet" href="/static/css/public.css">
	
		<!-- CSS and JS for event calendar framework. https://github.com/vkurko/calendar -->
		<link rel="stylesheet" href="/static/css/event-calendar.min.css">
		<script src="/static/js/event-calendar.min.js"></script>
		
		<title>Event Calendar - eTWIG Public Module</title>
	</head>

	<body>
	<#include "./_navbar.ftl">
	<p>&nbsp;</p>
	
	<main class="row etwig-main">
    	<div id="etwig-public-calendar" class="col"></div>
	</main>
	
	<!-- Custom JS for eTWIG calendar. -->
	<script src="/static/js/etwig-calendar.js"></script>
	
	<#include "../_footer.ftl">
</body>
</html>