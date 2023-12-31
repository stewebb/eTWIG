<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "DASHBOARD">

<!DOCTYPE html>
<html>
<head>
	<#include "./_includes/header.ftl">
	<title>Dashboard - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
	<#include "./_includes/sidebar.ftl">
	
	<div class="content-wrapper">
	</div>
	
	<#include "./_includes/footer.ftl">
</body>
</html>