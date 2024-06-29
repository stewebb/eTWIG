<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->

<#assign navbar = "ASSETS">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Asset List - ${app.appName}</title>
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
							<h1 class="bold-text">Asset Management</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="/assets/index.do">Assets</a></li>
								<#--
								<li class="breadcrumb-item active"><a href="/assets/list.do">List</a></li>
								-->
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
									<table id="assetsList" class="display table table-hover table-striped" width="100%">
										<thead>
											<tr>
												<th>ID</th>
												<th>Name</th>
												<th>Type</th>
												<th>Uploader</th>
												<th>Last Modified</th>
												<th>Preview</th>
												<th>Action</th>
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

	<script type="text/javascript" src="/static/js/etwig/asset.js?ver=${app.appVersion}"></script>
	<script>
		assetListTable();
	</script>
</body>
</html>