<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "ADMIN_PORTFOLIO">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Portfolio Management - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<input type="hidden" id="approvalDetailsLink" value="${ENDPOINTS.GRAPHICS_APPROVAL_DETAILS}"> 
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
							<h1 class="bold-text">Portfolio Management</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.ADMIN_USER_LIST}">User</a>&nbsp|&nbsp
									<a href="${ENDPOINTS.ADMIN_PORTFOLIO_LIST}">Portfolio</a>
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

					<#-- Portfolio List -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-list-check"></i>&nbsp;List
							</h3>
						</div>

						<div class="card-body table-responsive">
							<table id="portfoliosTable" class="display table table-hover table-striped" width="100%">
								<thead>
									<tr>
										<th>Portfolio ID</th>
										<th>Name</th>
										<th>Color</th>
										<th>Abbreviation</th>
										<th>Separated Calendar</th>
										<th>Action</th>
									</tr>
								</thead>
							</table>
						</div>

					</div>
					<#-- /Portfolio List -->

				</div>
			</section>
			<#-- /Main Content -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<script type="text/javascript" src="/static/js/etwig/admin-portfolio.js?ver=${app.appVersion}"></script>
	
	<script>
		portfolioListTable();
	</script>
</body>
</html>