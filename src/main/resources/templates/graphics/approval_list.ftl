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
								<li class="breadcrumb-item"><a href="/graphics/index.do">Graphics</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/approvalList.do">Request Approval</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- Page header -->

			<#-- Main Content -->
			<section class="content">
				<div class="container-fluid">

					<#-- Pending requests -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-list-check"></i>&nbsp;List
							</h3>
							<!--
							<div class="card-tools">
								<button type="button" class="btn btn-tool" onclick="pendingDT.ajax.reload();">
									<i class="fas fa-sync-alt"></i>
								</button>
							</div>
							-->
						</div>

						<div class="card-body table-responsive">
							<table id="requestsTable" class="display table table-hover table-striped" width="100%">
								<thead>
									<tr>
										<th>Request ID</th>
										<th>Event</th>
										<th>Request Time</th>
										<th>Requester</th>
										<th>Expect Date</th>
										<th>Status</th>
										<th>Approver</th>
										<th>Response Time</th>
										<th>Action</th>
									</tr>
								</thead>
							</table>

							<#-- Table Content -->
							<!--
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
							-->
							<#-- /Table Content -->

						</div>
					</div>
					<#-- /Pending requests -->

					<#-- Finalized requests -->
					<!--
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-thumbs-up"></i>&nbsp;Finalized requests
							</h3>
							<div class="card-tools">
								<button type="button" class="btn btn-tool" onclick="finalizedDT.ajax.reload();">
									<i class="fas fa-sync-alt"></i>
								</button>
							</div>
						</div>

						<div class="card-body table-responsive">
									
							<#-- Table Content -->
							<table id="finalizedRequestsList" class="table table-hover table-striped">
								<thead>
									<tr>
										<th>id</th>
										<th>Event</th>
										<th>Requester</th>
										<th>Position</th>
										<th>Expect date</th>
										<th>Approved</th>
										<th>Approver</th>
										<th>Position</th>
										<th>Response time</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
							<#-- /Table Content -->

						</div>
					</div>
					-->
					<#-- /Finalized requests -->

				</div>
			</section>
			<#-- /Main Content -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<#-- JS for Graphics Approval -->
	<script type="text/javascript" src="/static/js/etwig/graphics-approval.js?ver=${app.appVersion}"></script>
	
	<script>
    	//var pendingDT = pendingApprovalDataTable();
		//var finalizedDT = finalizedApprovalDataTable();
		bannerRequestListTable();

	</script>
</body>
</html>