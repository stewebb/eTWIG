<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "HOME">

<!DOCTYPE html>
<html>
<head>

	<#include "./_includes/header/head.ftl">
	<title>Homepage - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "./_includes/header/body_start.ftl">
	<link rel="stylesheet" type="text/css" href="/static/css/menubar.css">
	
	<#-- Main Wrapper -->
	<div class="wrapper">
	
		<#include "./_includes/navbar.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">

    		<#-- Page header -->
    		<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="bold-text">Homepage | Welcome to eTWIG</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.HOME}">Home</a>
								</li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- /Page header -->

			<section class="content">

				<#-- Menu -->
				<div class="card card-primary card-outline">							
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-compass"></i>&nbsp;Menu
						</h3>
					</div>

					<div class="card-body">

						<#-- Event Management -->
						<h5>Event Management</h5>
						<div class="row menu-row">

							
							

							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">
								    <div><i class="fa fa-list fa-super"></i></div>
                    				<div>List</div>
								
								</div>
							</div>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">Option 2</div>
							</div>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">Option 3</div>
							</div>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">Option 4</div>
							</div>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">Option 5</div>
							</div>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3">Option 6</div>
							</div>
						</div>
					</div>
				</div>
			</section>

			<#--
			<a href="/user/tokenLogin.do?token=ewogICAgImNvbnRyb2xJRCI6ICJtanppV2RoayIsCiAgICAidGltZXN0YW1wIjogMTcxOTc1MTg1OCwKICAgICJ1c2VyRW1haWwiOiAidTc1NDQ5OThAYW51LmVkdS5hdSIsCiAgICAidXNlck5hbWUiOiAiU3RldmVuIFdlYmIiCn0=">aaa</a>
			-->
		</div>
		<#-- /Content Wrapper. -->
		
	</div>
	<#-- Main Wrapper -->
	
	<#include "./_includes/footer.ftl">
	<#include "./_includes/header/body_end.ftl">
</body>
</html>