<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "OTHER">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header_head.ftl">
	<title>Asset selector - ${app.appName}</title>
</head>

<body>

	<#include "../_includes/header_body_start.ftl">
	
	<div class="wrapper">

		<#-- Asset Selector -->
		<section class="content">
			<div class="container-fluid">
			
				<#-- Data Table -->
				<div class="card card-primary card-outline">
						
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-list"></i>&nbsp;Asset List
						</h3>
					</div>

					<div class="card-body table-responsive">
								
						<#-- Button Options -->
						<div class="btn-group mb-3">
							<button type="button" class="btn btn-outline-primary">
								<i class="fa-solid fa-upload"></i>&nbsp;Upload
							</button>
									
							<button type="button" class="btn btn-outline-secondary" onclick = "dt.ajax.reload();">
								<i class="fa-solid fa-rotate"></i>&nbsp;Reload
							</button>
						</div>
							
						<#-- Table Content -->
						<table id="assetSelector" class="table table-hover table-striped">
							<thead>
								<tr>
									<th>AssetId</th>
									<th>Name</th>
									<th>Type</th>
									<th>Category</th>
									<th>Size</th>
									<th>Uploader</th>
									<th>Last Modified</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
					
				<#-- Preview -->
				<div class="card card-primary card-outline">	
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-eye"></i>&nbsp;Preview
						</h3>
					</div>

					<div class="card-body">
						<div id="previewContent"></div>
					</div>
				</div>
			</div>
		</section>
	
	</div>
	
	<#include "../_includes/header_body_end.ftl">

	<#--  jQuery DataTables plugin -->
	<link rel="stylesheet" type="text/css" href="/static/css/dataTables.bootstrap4.min.css">
    <script type="text/javascript" src="/static/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/static/js/dataTables.bootstrap4.min.js"></script>
	
	<#--  JS for Asset Selector options. -->
	<script type="text/javascript" src="/static/js/etwig-asset-management.js"></script>
	
	<script>
    	var dt = assetSelectorDataTable();
	</script>
</body>
</html>