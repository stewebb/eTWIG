<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The edit event page.
   -->
   
<#assign navbar = "OTHER">

<#-- Edit mode. -->
<#assign isEdit = true>
<#assign isEditStr = "true">

<#-- Convert the "Embedded" boolean to String -->
<#assign isEmbeddedStr = embedded ?string('true', 'false')>

<#-- In edit mode, inputs are enabled only the edit permissions are grant. -->
<#assign disabled = !editPermission>
<#assign disabledStr = editPermission ? string("", "disabled")>

<#-- In edit mode, mode are called "Edit" and "View", depends on edit permissions. -->
<#assign modeStr = editPermission ? string("Edit", "View")>

<#-- In edit mode, the link of this page is always "/event/edit", but two parameters eventId and embedded are given. -->
<#assign link = "edit?eventId=" + eventId + "&embedded=" + isEmbeddedStr>

 <#-- In edit mode, the default time unit and the default duration are based on the current choices.  -->
<#assign timeUnit = eventDetails.details.unit>
<#assign duration = eventDetails.details.duration>

 <#-- In add mode, the duration input will be hidden only when the time unit is "custom".  -->
<#assign durationHidden = timeUnit == "c">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header_head.ftl">

	<title>Edit Event - ${app.appName}</title>
</head>

<body class="sidebar-mini layout-fixed">

	<#if embedded == false>
		<#include "../_includes/sidebar.ftl">
	</#if>
	
	<#-- Content Wrapper -->
  	<div class="<#if embedded == false>content-</#if>wrapper">
  	
		<#-- Page header -->
		<#if embedded == false>
    	<#include "../_includes/event_addEdit_header.ftl">
    	</#if>
    	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
			
				<div class="row">
					<div class="col-md-6">
					
						<#-- Basic Information -->		
						<#include "../_includes/event_addEdit_basicInfo.ftl">
						
						<#-- Organizer -->		
						<#include "../_includes/event_addEdit_organizer.ftl">
					</div>
					
					<div class="col-md-6">
					
						<#-- Timing -->		
						<#include "../_includes/event_addEdit_timing.ftl">
						
						<#-- Properties -->
						<#include "../_includes/event_addEdit_properties.ftl">
						
					</div>
				</div>
				
				<#-- Footer -->
				<#include "../_includes/event_addEdit_footer.ftl">
				
			</div>
		</section>

	</div>
	


	<#-- Footer -->
	<#if embedded == false>
		<#include "../_includes/footer.ftl">
	</#if>
	
	<#include "../_includes/header_body.ftl">

	<#-- CSS and JS for summernote editor.-->
	<link rel="stylesheet" href="/static/css/summernote-bs4.min.css">
	<script src="/static/js/summernote-bs4.min.js"></script>
	
	<#-- Custom JS for adding events-->
	<script src="/static/js/etwig-events.js"></script>
	
	<#-- Post Scripts -->
	<#include "../_includes/event_addEdit_postScripts.ftl">
</body>
</html>