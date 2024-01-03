<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The error page in HTML format.
   -->
   
 <!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header.ftl">
	
    <title>Http Status ${error.status} - ${error.error}</title>
</head>

<body>
	<#assign color = "info">
    <#if error.status?starts_with("5")>
    	<#assign color = "danger">
    <#elseif  error.status?starts_with("4")>
    	<#assign color = "warning">
    </#if>
    
	<#-- Submit Options -->
	<div class="wrapper">
    	<section class="content">
    		
    		<#-- Error information -->
			<div class="card card-${color} card-outline">
				<div class="card-header">
					<h3 class="card-title bold-text text-${color}">
						<i class="fa-solid fa-circle-exclamation"></i>&nbsp;
						HTTP Status ${error.status} - ${error.error}
					</h3>
				</div>
				<div class="card-body">
					
					<div class="mb-2">
						<span class="bold-text">Location: </span>
						<#if path?has_content>${path}<#else>Unknown location.</#if>
					</div>
					
					<div class="mb-2">
						<span class="bold-text">Messages: </span>
						<#if error.status?starts_with("5")>
							The eTWIG platform experienced an internal error. We apologize for the inconvenience. 	If problem persists, you can send the <span class="bold-text text-primary">technical details</span> to the site administrators.
						<#else>
							The eTWIG platform experienced an error. It may caused by your browser, or the platform itself. If problem persists, please contact the site administrators.
						</#if>
					</div>	
					
					<#--  Only display technical details when HTTP status code is 5xx (server error). -->
					<#if error.status?starts_with("5") && error.trace?has_content>
					
						<#-- Set the color of eTWIG-related classes to the primary color. -->
						<#assign trace = error.trace?replace("net.grinecraft.etwig", "<span class='bold-text text-primary'>net.grinecraft.etwig</span>")>
						<#assign trace = trace?replace("org.springframework", "<span class='bold-text text-olive'>org.springframework</span>")>
						<#assign trace = trace?replace("Caused by:", "\n<span class='bold-text text-danger'>Caused by:</span>")>
						
						<div clsss="mb-2">
						<span class="bold-text">Technical details: </span>
							<pre>${trace}</pre>
						</div>
					</#if>
					
				</div>
				<div class="mb-2 d-flex justify-content-center">
					<button class="btn btn-outline-primary mb-1 mr-1" onclick="window.history.back();">
						<i class="fa-solid fa-rotate-left"></i>&nbsp;Go Back
					</button>
				</div>
			</div>
		
	
</body>
</html>
