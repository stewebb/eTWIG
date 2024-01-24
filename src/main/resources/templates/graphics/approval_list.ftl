<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "GRAPHICS_APPROVAL">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Graphics Requests Approval - ${app.appName}</title>
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
							<h1 class="bold-text">Graphics Request Approval</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="/graphics">Graphics</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/approval/list">Request Approval</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- Page header -->

			<#-- Main Content -->
			<section class="content">
			<div class="container-fluid">
				<div class="row">

					<#-- Pending requests -->
					<div class="col-md-12">
						<div class="card card-primary card-outline">
						
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-circle-question"></i>&nbsp;Pending requests
								</h3>
							</div>

							<div class="card-body table-responsive">
								
								<#-- Button Options -->
								<div class="btn-group mb-3">
									<button type="button" class="btn btn-outline-secondary" onclick = "dt.ajax.reload();">
										<i class="fa-solid fa-rotate"></i>&nbsp;Reload
									</button>
								</div>
								<#-- /Button Options -->

								<#-- Table Content -->
								<table id="pendingRequestsList" class="table table-hover table-striped">
									<thead>
										<tr>
											<th>id</th>
											<th>Event</th>
											<th>Requester</th>
											<th>Position</th>
											<th>Expect date</th>
											<th>Comments</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
								<#-- /Table Content -->

							</div>
						</div>
					</div>
					<#-- /Pending requests -->

					<#-- Finalized requests --
			
					<div class="col-md-6">
					</div>
					-- /Finalized requests -->
				</div>
			</div>

		</div>
		<#-- /Content Wrapper. -->
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<#-- JS for Graphics Approval List -->
	<script type="text/javascript" src="/static/js/etwig/graphics-approval.js"></script>
	
	<script>
    	var dt = pendingApprovalDataTable();
	</script>
</body>
</html>