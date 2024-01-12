<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The TWIG template design page.
   -->
   
<#assign navbar = "TWIG_TEMPLATE_DESIGNER">

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
			
				<#-- Basic Information -->
				<div class="card card-primary card-outline">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-circle-info"></i>&nbsp;Basic Information
						</h3>
					</div>

					<div class="card-body">
						<div class="row">
							<div class="col-md-6">
<div class="form-group">
<label>Minimal</label>
<select class="form-control select2" style="width: 100%;">
<option selected="selected">Alabama</option>
<option>Alaska</option>
<option>California</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>

<div class="form-group">
<label>Disabled</label>
<select class="form-control select2" disabled="disabled" style="width: 100%;">
<option selected="selected">Alabama</option>
<option>Alaska</option>
<option>California</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>

</div>

<div class="col-md-6">
<div class="form-group">
<label>Multiple</label>
<select class="select2" multiple="multiple" data-placeholder="Select a State" style="width: 100%;">
<option>Alabama</option>
<option>Alaska</option>
<option>California</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>

<div class="form-group">
<label>Disabled Result</label>
<select class="form-control select2" style="width: 100%;">
<option selected="selected">Alabama</option>
<option>Alaska</option>
<option disabled="disabled">California (disabled)</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>

</div>

</div>

<h5>Custom Color Variants</h5>
<div class="row">
<div class="col-12 col-sm-6">
<div class="form-group">
<label>Minimal (.select2-danger)</label>
<select class="form-control select2 select2-danger" data-dropdown-css-class="select2-danger" style="width: 100%;">
<option selected="selected">Alabama</option>
<option>Alaska</option>
<option>California</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>

</div>

<div class="col-12 col-sm-6">
<div class="form-group">
<label>Multiple (.select2-purple)</label>
<div class="select2-purple">
<select class="select2" multiple="multiple" data-placeholder="Select a State" data-dropdown-css-class="select2-purple" style="width: 100%;">
<option>Alabama</option>
<option>Alaska</option>
<option>California</option>
<option>Delaware</option>
<option>Tennessee</option>
<option>Texas</option>
<option>Washington</option>
</select>
</div>
</div>

</div>

</div>

</div>

<div class="card-footer">
Visit <a href="https://select2.github.io/">Select2 documentation</a> for more examples and information about
the plugin.
</div>
</div>
				
				<div class="row">
										
					
				</div>
				
				<div class="row">
					<div class="col-md-6">
					

						
						<#-- Organizer -->		
						<#--<#include "../_includes/event_addEdit_organizer.ftl">-->		
					</div>
					
					<div class="col-md-6">
					
						<#-- Timing -->		
						<#--<#include "../_includes/event_addEdit_timing.ftl">-->		
						
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

</body>
</html>