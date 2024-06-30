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

	<#-- Check if this page is included in another page, by checking whether jQuery is loaded. -->
	<script>
	if (typeof jQuery == 'undefined') {
		alert("Warning: This page is not supposed to be opened directly.");
		window.location.href = '/';
	}
	</script>
	
	<div class="wrapper">

		<#-- Asset Selector -->
		<section class="content">
			<div class="container-fluid">

				<#-- Upload Area -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">
							<i class="fa-solid fa-file-arrow-up"></i>
						</span>
					</div>				
									
					<#-- File upload box -->
					<div class="custom-file">
						<input type="file" class="custom-file-input" id="fileUploadSelector">
							<label class="custom-file-label" for="exampleInputFile">Choose a file</label>
						</div>
										
						<div class="input-group-append">
							<button type="button" class="btn btn-outline-primary" onclick="uploadFile(false, 'fileUploadSelector', 'assetSelector');" id="uploadSingleFileBtn">
							<i class="fa-solid fa-upload"></i>&nbsp;Upload
						</div>
					</div>
					<#-- Upload Area -->
				</div>

				<#-- List -->
				<div class="table-responsive mb-3">
					<table id="assetSelector" class="table table-hover table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Uploader</th>
								<th>Preview</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				<#-- /List -->

				<#-- Action -->
				<div class="d-flex justify-content-between">
					<button type="button" class="btn btn-outline-secondary" onclick="$('#etwigModal').modal('hide');">
						<i class="fa-solid fa-xmark"></i>&nbsp;Close
					</button>
					<button type="button" class="btn btn-outline-primary" id="selectBtn" onclick="" disabled="disabled">
						<i class="fa-regular fa-check"></i>&nbsp; Select
					</button>
							
				</div>
				<#-- /Action -->

			</div>
		</section>
		<#-- /Asset Selector -->
	
	</div>
		
	<#--  JS for Asset Selector options. -->
	<script type="text/javascript" src="/static/js/etwig/asset.js?ver=${app.appVersion}"></script>
	
	<script>
    	assetSelectorDataTable();

		// By default, no asset is selected.
    	//previewAsset(null);
	</script>
</body>
</html>