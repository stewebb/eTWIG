<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The TWIG template design page.
   -->
   
<#assign navbar = "TWIG_TEMPLATE_EDIT">

<#-- Edit mode. -->
<#assign isEdit = true>
<#assign isEditStr = "true">
<#assign modeStr = "Edit">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">

	<title>Edit TWIG Template - ${app.appName}</title>
</head>

<body class="sidebar-mini layout-fixed">
	<#include "../_includes/header/body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<#-- Content Wrapper -->
  	<div class="content-wrapper">
  		<#include "../_includes/twigTemplate/addEdit_header.ftl">
  		
    	<#-- Main area (only basic information) -->
    	<section class="content">
			<div class="container-fluid">
				<#include "../_includes/twigTemplate/addEdit_basicInfo.ftl">
			</div>
		</section>
	</div>
	
	<#-- Footer -->
	<#include "../_includes/footer.ftl">

	<#include "../_includes/header/body_end.ftl">
	<#include "../_includes/twigTemplate/addEdit_postScripts.ftl">

</body>
</html>