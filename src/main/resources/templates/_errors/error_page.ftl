<#-- 
	eTWIG - The event management software for university communities.
	copyright: Copyright (c) 2024 Steven Webb
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The error page in HTML format.
   -->
   
 <!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	
    <title>Http Status ${error.status} - ${error.error}</title>
</head>

<body>
	<#include "../_includes/header/body_start.ftl">

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
						
						<#-- Specific messages for common 4xx and 5xx errors -->
    					<#if error.status == 400>
        					Bad Request: The server cannot process the request due to a client error.
        				<#elseif error.status == 401>
            				Unauthorized: Authentication is required and has failed or not been provided.
    					<#elseif error.status == 403>
        					Forbidden: You do not have permission to access this resource.
   					 	<#elseif error.status == 404>
        					Not Found: The requested resource could not be found.
            			<#elseif error.status == 500>
                			Internal  Server Error: The eTWIG platform experienced an internal error. We apologize for the inconvenience.
            			<#elseif error.status == 501>
                			Not Implemented: The server does not support the functionality required.
            			<#elseif error.status == 502>
                			Bad Gateway: The server received an invalid response from the upstream server.
            			<#elseif error.status == 503>
                			Service Unavailable: The server is currently unable to handle the request.
            			<#elseif error.status == 504>
                			Gateway Timeout: The server did not receive a timely response from the upstream server.
            			<#else>
                			Error: There was an error with status code ${error.status}.
            			</#if>
    						Please contact the site administrators if problem persists.
					</div>
					
					<#--  Only display technical details when HTTP status code is in the following list. -->
					<#assign show_exception_codes = [400, 405, 500]>
					<#if show_exception_codes?seq_contains(error.status) && error.trace?has_content>
					
						<#-- Set the color of eTWIG-related classes to the primary color. -->
						<#assign trace = error.trace?replace("net.grinecraft.etwig", "<span class='bold-text text-primary'>net.grinecraft.etwig</span>")>
						<#assign trace = trace?replace("Caused by:", "\n<span class='bold-text text-danger'>Caused by:</span>")>
						
						<div clsss="mb-2">
						<span class="bold-text">Technical details: </span>
							<pre>${trace}</pre>
						</div>
					</#if>	
				</div>
				
				<div class="d-flex justify-content-center mb-3">
				
					<div class="btn-group">
						<button class="btn btn-outline-primary" onclick="window.history.back();">
							<i class="fa-solid fa-rotate-left"></i>&nbsp;Go Back
						</button>
						
						<#if embedded?has_content && embedded>
							<button class="btn btn-outline-secondary" onclick="parent.$('#etwigModal').modal('hide');">
								<i class="fa-solid fa-xmark"></i>&nbsp;Close				
								</button>
						</#if>
					</div>
					
				</div>
				
			</div>
		</section>
	</div>
	
	<#include "../_includes/header/body_end.ftl">
	
</body>
</html>
