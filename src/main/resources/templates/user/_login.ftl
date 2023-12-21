<!DOCTYPE html>
<html>
	<head>
	<#include "../_includes/header.ftl">
	
	<!--<link rel="stylesheet" href="/static/css/welcome.css"> -->
	<title>Welcome to eTWIG</title>
</head>

<body>
	<#include "../_includes/body_common.ftl">
	
	<#-- Login Box -->
	<div class="container-fluid d-flex justify-content-center align-items-center vh-100">
		<form action="/user/login" method="post">
		
			<#-- Logo and welcome label -->
    		<div class="row mb-3">
        		<div class="form-group col-12">
        			<p><img src="/static/images/eTWIG.png" class="img-fluid" alt="eTWIG logo" style="max-width: 400px; width:100%;"></p>
        			<center><h2 style="font-weight: bold;">Welcome to eTWIG</h2></center>
     			</div>
			</div>
			
			<#-- Username input -->
			<div class="row mb-3">
				<div class="input-group mb-21 col-12">
  					<div class="input-group-prepend">
    					<span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-envelope"></i></span>
  					</div>
  					<input type="text" class="form-control" name="username" placeholder="Username" required />
				</div>
			</div>
			
			<#-- Password input -->
			<div class="row mb-3">
				<div class="input-group mb-1 col-12">
  					<div class="input-group-prepend">
    					<span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-lock"></i></span>
  					</div>
  					<input type="Password" class="form-control" name="password" placeholder="Password" required />
				</div>  
			</div>	
			
			<#-- Remember me and-->
			<div class="row mb-3">
				<div class="form-check mb-1 col-12" style="text-align:right;">
  					<input class="form-check-input" type="checkbox" value="" id="rememeberMe">
  					<label class="form-check-label" for="rememeberMeLabel">Remember me</label>
				</div>
			</div>
			
			<#-- Login button -->
			<div class="row">
				<div class="form-group col-12">
        			<button type="submit" class="btn btn-outline-primary btn-lg btn-block"><i class="fa-regular fa-right-to-bracket"></i>&nbsp;Login</button>
      			</div>
      		</div>
      		
      		<#-- Forget password link -->
      		<div class="row mb-3">
				<div class="mb-3 col-12">
  					<center><a href="#">Forget password?</a></center>
				</div>
			</div>
				
			<#-- "OR" Divider -->
      		<div class="row mb-3">
				<div class="col-12" style="color:gray;">
        			<center>OR View</center>
      			</div>
      		</div>
      		<hr>

			<#-- View links -->
			<div class="row mb-3">
				<div class="form-group col-6">
					<a href="#" class="btn btn-outline-success btn-lg btn-block"><i class="fa-regular fa-lightbulb"></i>&nbsp;TWIG</a>
				</div>
				<div class="form-group col-6">
					<a href="#" class="btn btn-outline-warning btn-lg btn-block"><i class="fa-solid fa-circle-question"></i>&nbsp;Help</a>
				</div>
			</div>
    	</form>
	</div>

	<#include "../_includes/footer.ftl">
</body>
</html>