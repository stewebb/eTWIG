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
	<title>Banner Requests Approval - ${app.appName}</title>
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
							<h1 class="bold-text">Banner Request Approval</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="/graphics/approvalList.do">Approval</a>&nbsp|&nbsp
									<a href="/graphics/summaryList.do">Summary</a>
								</li>
								<#--
								<li class="breadcrumb-item"><a href="/graphics/index.do">Graphics</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/approvalList.do">Banner Request Approval</a></li>
								-->
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
	
	<#-- JS for Banner Request Approval -->
	<script type="text/javascript" src="/static/js/etwig/banner-approval.js?ver=${app.appVersion}"></script>
	
	<script>
		bannerRequestListTable();
	</script>
</body>
</html>