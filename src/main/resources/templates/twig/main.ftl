<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The main public TWIG page with filter.
   -->
   
<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- Custom CSS for public TWIG. -->
	<link rel="stylesheet" href="/static/css/etwig-twig.css">
	<title>TWIG</title>
</head>

<body style="height: 100%;">
	
	<iframe src="/twig/content" class="fullscreen-iframe"></iframe>
	
	<#-- The floating settings button -->
	<div class="setting-button">
    	<button id="settingsButton" class="btn btn-outline-primary">
        	<i class="fa fa-gear fa-xl fa-beat" id="cogIcon"></i>
    	</button>
	</div>

	<#-- The setting and filter box -->
    <div class="card settings-box card-primary card-outline" id="settingsBox">
		<div class="card-header">
			<h3 class="card-title">
				<i class="fa-solid fa-screwdriver-wrench"></i>&nbsp;Settings
			</h3>
		</div>
			
		<div class="card-body">
								
		</div>
	</div>

<script>
    $(document).ready(function() {
        $('#settingsButton').click(function() {
            $('#settingsBox').toggle();
        });
    });
</script>
</body>
</html>