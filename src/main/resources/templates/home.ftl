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
							<i class="fa-solid fa-compass"></i>&nbsp;Quick Link
						</h3>
					</div>

					<div class="card-body">

						<#-- Event Management -->
						<#-- <h5><i class="fa-solid fa-lightbulb"></i>&nbsp;Event Management</h5> -->
						<div class="row menu-row">

							<#-- Event List -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='${ENDPOINTS.EVENTS_LIST}';">
								    <div><i class="fa fa-list fa-super"></i></div>
                    				<div>Event List</div>
								</div>
							</div>
							<#-- /Event List -->

							<#-- Event Calendar -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='${ENDPOINTS.EVENTS_CALENDAR}';">
								    <div><i class="fa fa-calendar-check fa-super"></i></div>
                    				<div>Event Calendar</div>
								</div>
							</div>
							<#-- /Event Calendar -->

							<#-- Add Event -->
							<#if userPermission.eventsAccess>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='${ENDPOINTS.EVENTS_ADD}';">
								    <div><i class="fa fa-calendar-plus fa-super"></i></div>
                    				<div>Add Event</div>
								</div>
							</div>
							<#-- /Add Event-->

							<#-- Import Event -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='${ENDPOINTS.EVENTS_IMPORT}';">
								    <div><i class="fa-solid fa-cloud-arrow-up fa-super"></i></div>
                    				<div>Import Event</div>
								</div>
							</div>
							</#if>
							<#-- /Import Event -->

							<#-- Event Graphics Summary -->
          					<#if userPermission.graphicsAccess>
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='#';">
								    <div><i class="fa fa-rectangle-list fa-super"></i></div>
                    				<div>Event Graphics Summary</div>
								</div>
							</div>
							<#-- /Event Graphics Summary -->

							<#-- Banner Request Approval -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='#';">
								    <div><i class="fa fa-circle-check fa-super"></i></div>
                    				<div>Banner Request Approval</div>
								</div>
							</div>
							</#if>							
							<#-- /Banner Request Approval -->

							<#-- Asset Management -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='#';">
								    <div><i class="fa fa-file fa-super"></i></div>
                    				<div>Asset Management</div>
								</div>
							</div>
							<#-- /Asset Management -->

							<#-- Public TWIG -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='#';">
									<!--
								    <div><i class="fa fa-t fa-super"></i></div>
									-->
									<div><img class="icon" src="/static/images/GHLogo.min.svg" alt="Custom Icon"> <!-- External SVG --></div>
                    				<div>Public TWIG</div>
								</div>
							</div>
							<#-- /Public TWIG -->

							<#-- User Profile -->
							<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2 menu-col">
								<div class="menu-option p-3" onclick="window.location.href='#';">
								    <div><i class="fa fa-id-badge fa-super"></i></div>
                    				<div>User Profile</div>
								</div>
							</div>
							<#-- /User Profile -->

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