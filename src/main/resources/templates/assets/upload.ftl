<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "ASSETS_UPLOAD">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Upload Asset(s) - ${app.appName}</title>
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
							<h1 class="bold-text">Upload Asset(s)</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="/assets/index.do">Assets</a></li>
								<li class="breadcrumb-item active"><a href="/assets/upload.do">Upload</a></li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
					<div class="row">

						<#-- \ Single File -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-cloud-arrow-up"></i>&nbsp;Single File
									</h3>
								</div>

								<div class="card-body">

								</div>
							</div>
						</div>
						<#-- /Upload -->

						<#-- Result -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-circle-check"></i>&nbsp;Result
									</h3>
								</div>

								<#--
								<div class="card-body table-responsive">
									<table id="importResult" class="table table-striped table-hover">

										<thead>
										<tr>
											<th>Row Number</th>
											<th>Status</th>
										</tr>
										</thead>

										<tbody>
										</tbody>
									</table>
								</div>
								-->

							</div>
						</div>
						<#-- /Result -->

					</div>
				</div>
			</section>
			<#-- /Main area -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<script type="text/javascript" src="/static/js/etwig/asset.js?ver=${app.appVersion}"></script>
</body>
</html>