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

				<#-- List -->
				<div class="table-responsive">
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