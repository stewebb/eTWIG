<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	
	<title>Login - ${app.appName}</title>
</head>

<body class="hold-transition login-page">
	
	<#include "../_includes/header/body_start.ftl">
	<div class="login-box">
  		
  		<#-- Login Card -->
  		<div class="card">
    		<div class="card-body login-card-body">

				<#-- Logo -->
  				<div class="login-logo">
    				<p><img src="/static/images/eTWIG.png" class="img-fluid" alt="eTWIG logo" style="max-width: 300px; width:100%;"></p>
  				</div>
				<#-- /Logo -->

				<#-- Login form -->
				<p class="login-box-msg bold-text">Login</p>
				<form action="/user/login" method="post">
					
					<#-- Username input -->
					<div class="input-group mb-3">
						<input type="text" class="form-control" name="username" placeholder="Email" required />
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-envelope"></span>
							</div>
						</div>
					</div>
			
					<#-- Password input -->
					<div class="input-group mb-3">
						<input type="Password" class="form-control" name="password" placeholder="Password" required />
						<div class="input-group-append">
							<div class="input-group-text">
								<span class="fas fa-lock"></span>
							</div>
						</div>
					</div>
					
					<#-- Remember me checkbox-->
					<div class="row mb-3">
						<div class="icheck-primary mb-1 col-12" style="text-align:right;">
							<input type="checkbox" id="remember" name="remember-me" checked>
							<label for="remember">Remember Me</label>
						</div>
					</div>
					
					<#-- Login button-->
					<p class="mb-3">
						<button type="submit" class="btn btn-outline-primary btn-block">
							<i class="fa-regular fa-right-to-bracket mr-1"></i>&nbsp;Login
						</button>
					</p>
				</form>
				<#-- /Login form -->

    	</div>
  	</div>
</div>

<#include "../_includes/header/body_end.ftl">

<script>
	var searchParams = new URLSearchParams(window.location.search);
	if (searchParams.get('success') == 'false'){
		warningPopup("Login failed.", "Check the email address and password and try again.");
	}
</script>

</body>
</html>