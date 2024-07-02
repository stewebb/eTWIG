<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "ADMIN_USER_LIST">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>User Management - ${app.appName}</title>
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
							<h1 class="bold-text">User and Position Management</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.ADMIN_USER_LIST}">User</a>&nbsp|&nbsp
									<!--<a href="${ENDPOINTS.GRAPHICS_SUMMARY_LIST}">Summary</a>-->
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
							<table id="usersTable" class="display table table-hover table-striped" width="100%">
								<thead>
									<tr>
										<th colspan="4" style="vertical-align: middle; text-align: center;">User</th>
										<th colspan="3" style="vertical-align: middle; text-align: center;">Position</th>
										<th rowspan="2" style="vertical-align: middle; text-align: center;">Action</th>
									</tr>
									<tr>
										<th>ID</th>
										<th>Name</th>
										<th>Email</th>
										<th>Last Login</th>
										<th>Name</th>
										<th>Portfolio</th>
										<th>Email</th>
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
	<#--
	<script type="text/javascript" src="/static/js/etwig/banner-approval.js?ver=${app.appVersion}"></script>
	
	<script>
		bannerRequestListTable();
	</script>
	-->
</body>
</html>