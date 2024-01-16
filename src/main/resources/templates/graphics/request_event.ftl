<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "GRAPHICS_REQUEST_VIEW">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Dashboard - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">

	<#include "../_includes/header/body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<div class="content-wrapper">
	</div>
	
	<#include "../_includes/footer.ftl">
<#include "../_includes/header/body_end.ftl">
</body>
</html>