<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "DASHBOARD">

<!DOCTYPE html>
<html>
<head>

	<#include "./_includes/header/head.ftl">
	<title>Dashboard - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "./_includes/header/body_start.ftl">
	<div class="wrapper">



	
		<#include "./_includes/navbar.ftl">

		<div class="content-wrapper">
			




			
		</div>
	</div>
	<#include "./_includes/footer.ftl">
	<#include "./_includes/header/body_end.ftl">
</body>
</html>