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
					<h3 class="card-title">
						<i class="fa-solid fa-circle-exclamation"></i>&nbsp;Oops! Something went wrong.&nbsp;
					</h3>
				</div>
				<div class="card-body">
					<div class="mb-2 text-${color} bold-text">
						HTTP Status ${error.status} - ${error.error}
					</div>
					
					<div class="mb-2">
						<span class="bold-text">Location: </span>
						<#if path?has_content>${path}<#else>Unknown location.</#if>
					</div>
					
					<div class="mb-2">
						<span class="bold-text">Messages provided by server: </span>
						<#if error.message?has_content>${error.message}<#else>No message was given.</#if>
					</div>
					<div class="mb-2">
						<span class="bold-text">Additional messages: </span>
						<#if error.status?starts_with("5")>
							The eTWIG platform experienced an internal error. We apologize for the inconvenience.
						<#else>
							The eTWIG platform experienced an error. This error may caused by your browser, or the platform itself.
						</#if>
						If problem persists, please click the <span class="bold-text text-primary">technical details</span> button to reveal those details, and send them to the developers.
					</div>	

				</div>
			</div>
			
			<#-- Options -->
			<div class="card">
				<div class="card-body mr-2">
				
					<a href="/" class="btn btn-outline-primary mb-1 mr-1">
						<i class="fa-solid fa-home"></i>&nbsp;Home
					</a>
					
					<button id="showTechnicalDetails" class="btn btn-outline-info mb-1 mr-1">
						<i class="fa-solid fa-bomb"></i>&nbsp;Technical Details
					</button>
					
					<button class="btn btn-outline-secondary mb-1 mr-1" onclick="window.history.back();">
						<i class="fa-solid fa-rotate-left"></i>&nbsp;Back
					</button>
					
					<button class="btn btn-outline-secondary mb-1 mr-1" onclick="window.location.reload();">
							<i class="fa-solid fa-rotate"></i>&nbsp;Refresh
					</button>
					
					<a href="/user/login" class="btn btn-outline-secondary mb-1 mr-1">
						<i class="fa-solid fa-right-from-bracket"></i>&nbsp;Re-login
					</a>
				</div>
			</div>
			
			<#-- Technical Details -->
			<div class="card card-primary card-outline" id="technicalDetails" style="display:none">
				<div class="card-header">
					<h3 class="card-title">
						<i class="fa-solid fa-bug"></i>&nbsp;Technical Details
					</h3>
				</div>
				<div class="card-body">
					<#if error.trace?has_content>
						<#assign trace = error.trace?replace("net.grinecraft.etwig", "<span class='bold-text text-primary'>net.grinecraft.etwig</span>")>
						<#assign trace = trace?replace("Caused by:", "<span class='bold-text text-danger'>Caused by:</span>")>
						<pre>${trace}</pre>
					</#if>
				</div>
			</div>

		</section>
	</div>
	
	<script>
		$(document).ready(function() {
    		$('#showTechnicalDetails').click(function() {
        		$('#technicalDetails').toggle();
    		});
		});
	</script>
</body>
</html>
