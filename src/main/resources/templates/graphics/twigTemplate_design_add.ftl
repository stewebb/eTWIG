<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The TWIG template design page.
   -->
   
<#assign navbar = "TWIG_TEMPLATE_DESIGNER">

<#-- Add mode. -->
<#assign isEdit = false>
<#assign isEditStr = "false">
<#assign modeStr = "Add">

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header_head.ftl">

	<title>TWIG Template Designer - ${app.appName}</title>
</head>

<body class="sidebar-mini layout-fixed">
	<#include "../_includes/header_body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<#-- Content Wrapper -->
  	<div class="content-wrapper">
  	
  		<#include "../_includes/twigTemplate_addEdit_header.ftl">

		
    	
    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">
			
				<#include "../_includes/twigTemplate_addEdit_basicInfo.ftl">
				
				<div class="row">
					<div class="col-md-6">

						<#-- Background -->		
						<#include "../_includes/twigTemplate_addEdit_background.ftl">
					</div>
					
					<div class="col-md-6">
					
						<#-- Logo -->		
						<#include "../_includes/twigTemplate_addEdit_logo.ftl">
						
						<#-- Properties -->
						<#--<#include "../_includes/event_addEdit_properties.ftl">-->		
						
						<#-- Footer -->
						<#--<#include "../_includes/event_addEdit_footer.ftl">-->		
					</div>
				</div>
				

				
			</div>
		</section>

	</div>
	


	<#-- Footer -->
	<#include "../_includes/footer.ftl">

	<#include "../_includes/header_body_end.ftl">
	<#include "../_includes/twigTemplate_addEdit_postScripts.ftl">

</body>
</html>