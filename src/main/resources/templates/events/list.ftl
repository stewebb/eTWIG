<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "EVENTS_LIST">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Event List - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#--
	<input type="hidden" id="approvalDetailsLink" value="${ENDPOINTS.GRAPHICS_APPROVAL_DETAILS}"> 
	-->

	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">

		<#include "../_includes/navbar.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">
			
			<#-- Page header -->
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="bold-text">Event List</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.GRAPHICS_APPROVAL_LIST}">Approval</a>&nbsp|&nbsp
									<a href="${ENDPOINTS.GRAPHICS_SUMMARY_LIST}">Summary</a>
								</li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- Page header -->

			<#-- Main Content -->
			<section class="content">
				<div class="container-fluid">

					<#-- Request List -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-list-check"></i>&nbsp;List
							</h3>
						</div>

						<div class="card-body table-responsive">
							<table id="eventsTable" class="display table table-hover table-striped" width="100%">
								<thead>
									<tr>
										<th>ID</th>
										<th>Name</th>
										<th>Location</th>
										<th>Recurring</th>
										<th>Start Time</th>
										<th>Duration</th>
										<th>Organizer</th>
										<th>Last Modified</th>
										<th>Action</th>
									</tr>
								</thead>
							</table>
						</div>

					</div>
					<#-- /Request List -->

				</div>
			</section>
			<#-- /Main Content -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- rrule.js -->
   	<script type="module" src="/static/js/etwig/recurrent.min.js?ver=${app.appVersion}"></script>
	
	<script type="text/javascript" src="/static/js/etwig/events.js?ver=${app.appVersion}"></script>
	
	<script>
		eventListTable();
	</script>
</body>
</html>