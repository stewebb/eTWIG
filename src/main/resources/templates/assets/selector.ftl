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

	<#-- include "../_includes/header/head.ftl" -->
	<title>Asset selector - ${app.appName}</title>
</head>

<body>

	<#-- Check whether this page is included in a normal page, by checking whether jQuery is loaded. -->
	<script>
	if (typeof jQuery == 'undefined') {
		alert("Warning: This page is not supposed to be opened directly.");
		window.location.href = '/';
	}
	</script>

	<#--include "../_includes/header/body_start.ftl"-->
	
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
								
						<#-- Upload Options -->
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">
									<i class="fa-solid fa-file-arrow-up"></i>
								</span>
							</div>				
						
							<#-- File upload box -->
							<div class="custom-file">
								<input type="file" class="custom-file-input" id="fileUpload">
								<label class="custom-file-label" for="exampleInputFile">Choose file</label>
							</div>
							
							<div class="input-group-append">
							
								<#-- Upload file button -->
								<button type="button" class="btn btn-outline-primary" onclick="uploadFile();" id="uploadFileBtn" disabled>
									<i class="fa-solid fa-upload"></i>
								</button>
								
								<#-- Reset file button -->
								<button type="button" class="btn btn-outline-secondary" onclick = "resetFile();">
									<i class="fa-solid fa-xmark"></i>
								</button>
								
								<#--Reload table button -->
								<button type="button" class="btn btn-outline-secondary" onclick = "dt.ajax.reload();">
									<i class="fa-solid fa-rotate"></i>
								</button>
							</div>
						</div>
						<#-- /Upload Options -->
							
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
					
						<#-- Preview content / -->
						<div id="previewContent"></div>
						
						<#-- Submit options -->
						<div class="btn-group mt-3" role="group" style="float: right;">
						
							<#-- Download -->
							<button id="downloadBtn" class="btn btn-outline-success" onclick="" disabled>
								<i class="fa-regular fa-download"></i>&nbsp;Download
							</button>
							<#-- /Download -->
							
							<#-- Cancel -->
							<button type="button" class="btn btn-outline-secondary" onclick="$('#etwigModal').modal('hide');">
								<i class="fa-solid fa-xmark"></i>&nbsp;Close
							</button>
							<#-- /Cancel -->
							
							<#-- Select -->
							<button type="button" class="btn btn-outline-primary" id="selectBtn">
								<i class="fa-regular fa-check"></i>&nbsp; Select
							</button>
							<#-- /Select -->
							
						</div>
						<#-- /Submit options -->
						
					</div>
					
					</div>
				</div>
				<#-- Preview -->
				
			</div>
		</section>
	
	</div>
		
	<#--  JS for Asset Selector options. -->
	<script type="text/javascript" src="/static/js/etwig/asset.js?ver=${app.appVersion}"></script>
	
	<script>
    	assetSelectorDataTable();

		// By default, no asset is selected.
    	previewAsset(null);
	</script>
</body>
</html>