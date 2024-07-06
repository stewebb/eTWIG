<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	
	<title>Logout - ${app.appName}</title>
</head>

<body class="hold-transition login-page">
	
	<#include "../_includes/header/body_start.ftl">
	<div class="login-box">
  		
  		<#-- Logout Card -->
  		<div class="card">
    		<div class="card-body login-card-body">

				<#-- Logo -->
  				<div class="login-logo">
    				<p><img src="/static/images/eTWIG.png" class="img-fluid" alt="eTWIG logo" style="max-width: 300px; width:100%;"></p>
  				</div>
				<#-- /Logo -->

				<#-- Logout message -->
				<div class="login-box-msg bold-text">Logout</div>
				<div class="mb-3">You have successfully logged out of the system. Thank you for using eTWIG.</div>
				<#-- /Logout message -->
				
				<#-- Re-login button -->
				<a href="${ENDPOINTS.USER_LOGIN}" class="btn btn-outline-primary btn-block">
						<i class="fa-regular fa-right-to-bracket"></i>&nbsp;Re-Login
				</a>
				<#-- Re-login button -->

			</div>
    	</div>
		<#-- /Logout Card -->

  	</div>
</div>

<#include "../_includes/header/body_end.ftl">

<script>
	$( document ).ready(function() {
    	$.ajax({ url: "/user/logout" });	// logout processing url
	});
</script>

</body>
</html>