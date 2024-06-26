<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
<#assign navbar = "GRAPHICS_EVENTS">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Events - ${app.appName}</title>
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
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="bold-text">Event Graphics</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="/graphics/index.do">Graphics</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/eventsList.do">Event Graphics</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- /Page header -->

			<#-- DataTable -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<div class="card card-primary card-outline">
							
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-list-check"></i>&nbsp;List
									</h3>
								</div>
								
								<div class="card-body table-responsive">

									<#-- Table Content -->
									<table id="eventGraphics" class="table table-hover table-striped">
										<thead>
											<tr>
												<th colspan="3" style="vertical-align: middle;">Event Details</th>
												<th colspan="3" style="vertical-align: middle;">Statistics</th>
												<th rowspan="2" style="vertical-align: middle;">Last Modified</th>
												<th rowspan="2" style="vertical-align: middle;">Action</th>
											</tr>
											<tr>
												<th>ID</th>
												<th>Event Name</th>
												<th>Time</th>
												<th>TWIG Component</th>
												<th>Banner</th>
												<th>Pending Approval</th>
											</tr>
										</thead>
										<tbody></tbody>
									</table>
									<#-- /Table Content -->

								</div>

							</div>
						</div>
					</div>
				</div>
			</section>
			<#-- DataTable -->
			
		</div>
		<#-- /Content Wrapper. -->
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<script type="text/javascript" src="/static/js/etwig/graphics-events.js?ver=${app.appVersion}"></script>
	
	<script>
    	var dt = eventGraphicsDataTable();
		console.log(dt)
	</script>
</body>

</html>