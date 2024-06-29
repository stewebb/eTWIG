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

			<section class="content">
				<div class="container-fluid">

					<#-- Upload -->
					<div class="row mb-3">

						<#-- Single File -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-cloud-arrow-up"></i>&nbsp;Single File
									</h3>
								</div>

								<div class="card-body">

									<#--
									<div class="callout callout-primary mb-3">
										<h5 class="bold-text">Upload single file</h5>
										<p>You can only upload one file each time in this area.</p>
									</div>
									-->
									<p>
										You can only upload <b>one file each time</b> in this area and
										a compressed folder will also be treated as a single file.
									</p>

									<#-- Upload Area -->
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-file-arrow-up"></i>
											</span>
										</div>				
									
										<#-- File upload box -->
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="fileUploadSingle">
											<label class="custom-file-label" for="exampleInputFile">Choose a file</label>
										</div>
										
										<div class="input-group-append">
											<button type="button" class="btn btn-outline-primary" onclick="uploadFile(false, 'fileUploadSingle', 'assetsList');" id="uploadSingleFileBtn">
												<i class="fa-solid fa-upload"></i>
										</div>
									</div>
									<#-- Upload Area -->

								</div>
							</div>
						</div>
						<#-- /Single File -->

						<#-- Result -->
						<div class="col-md-6">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-layer-group"></i>&nbsp;Multiple Files
									</h3>
								</div>

								<div class="card-body">

									<#--
									<div class="callout callout-primary mb-3">
										<h5 class="bold-text">Upload multiple files</h5>
										<p>You can</p>
									</div>
									-->
									<p>You can only upload <b>multiple files</b> in this area.</p>

									<#-- Upload Area -->
									<#--
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-file-arrow-up"></i>
											</span>
										</div>				
									
										<-- File upload box --
										
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="fileUploadSingle">
											<label class="custom-file-label" for="exampleInputFile">Choose a file</label>
										</div>
										
										<div class="input-group-append">
											<button type="button" class="btn btn-outline-primary" onclick="uploadFile(false, 'fileUploadSingle', 'assetsList');" id="uploadSingleFileBtn">
												<i class="fa-solid fa-upload"></i>
										</div>
										
									</div>
									-->
									<#-- Upload Area -->

								</div>

							</div>
						</div>
						<#-- /Result -->

					</div>

					<#-- Asset List -->
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
					<#-- /Asset List -->

				</div>
				<#-- /Upload -->

			</section>
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