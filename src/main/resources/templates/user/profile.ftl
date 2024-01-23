<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "USER">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>User Profile - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">

	
		<#include "../_includes/navbar.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">
			
			<#-- Page header -->
			<section class="content-header">
				<div class="container-fluid">
					<h1 class="bold-text">User Settings: ${user.fullName}</h1>
				</div>
			</section>
			<#-- /Page header -->

			<#-- Main area -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">

						<#-- Left column -->
						<div class="col-md-3">

							<#-- Profile -->
							<div class="card card-primary card-outline">
								<div class="card-body box-profile">

									<#-- Avatar -->
									<div class="text-center">
										<img class="profile-user-img img-fluid img-circle" src="https://www.gravatar.com/avatar/">
									</div>
									<#-- /Avatar -->
									
									<#-- Name and role -->
									<h3 class="profile-username text-center">${user.fullName}</h3>
									<p class="text-muted text-center">
										<#list access.myRoles as role>
											${role.name}&nbsp;
										</#list>		
									</p>
									<#-- /Name and role -->
									
									<#-- User basic info -->
									<ul class="list-group list-group-unbordered mb-3">
										<li class="list-group-item">
											<b>User ID</b> <a class="float-right">${user.id}</a>
										</li>
										<li class="list-group-item">
											<b>Email</b> <a class="float-right">${user.email}</a>
										</li>
										<li class="list-group-item">
											<b>Last login</b> <a class="float-right"></a>
										</li>
									</ul>
									<#-- /User basic info -->
									
									<a href="/user/logout" class="btn btn-outline-primary btn-block">
										<i class="fa-solid fa-right-from-bracket"></i>&nbsp;Logout
									</a>
								</div>
							</div>
							<#-- Profile -->

						</div>
						<#-- /Left column -->
					
						<#-- Right column -->
						<div class="col-md-9">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-user-gear"></i>&nbsp;Information and Settings
									</h3>
								</div>

								<div class="card-body">
								The body of the card
								</div>

							</div>
						</div>
						<#-- /Right column -->

					</div>
				</div>
			</section>
			<#-- /Main area -->


		</div>
		<#-- /Content Wrapper. -->
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
</body>
</html>