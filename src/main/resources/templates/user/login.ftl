<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The login page.
   -->

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<title>Welcome to eTWIG</title>
</head>

<body class="hold-transition login-page">
	<div class="login-box">
	
		<#-- Logo -->
  		<div class="login-logo">
    		<p><img src="/static/images/eTWIG.png" class="img-fluid" alt="eTWIG logo" style="max-width: 400px; width:100%;"></p>
    		<h2 class="bold-text">Welcome to eTWIG</h2>
  		</div>
  		
  		<#-- Login Card -->
  		<div class="card">
    		<div class="card-body login-card-body">
    		
      			<p class="login-box-msg bold-text">
      				<i class="fa-solid fa-user-shield"></i>&nbsp;Administration Portal
      			</p>
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
  						<input type="checkbox" id="remember">
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
      		
			<#-- Public access links -->
			<div class="social-auth-links text-center mb-3">
        		<p class="login-box-msg bold-text">
        			<i class="fa-solid fa-earth-americas"></i>&nbsp;Public Access
        		</p>
        		<a href="/twig/main" class="btn btn-outline-secondary btn-block">
        			<i class="fa-regular fa-lightbulb mr-1"></i>&nbsp;View TWIG
        		</a>
      		</div>

    	</div>
  	</div>
</div>

<script>
	var searchParams = new URLSearchParams(window.location.search);
	
	// A warning toast when login failed.
	if (searchParams.get('success') == 'false'){
		warningToast("Login failed.", "Check the email address and password and try again.");
	}
</script>

</body>
</html>