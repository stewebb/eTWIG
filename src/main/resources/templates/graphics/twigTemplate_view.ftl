<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
<#assign navbar = "TWIG_TEMPLATE_VIEW">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header_head.ftl">
	<title>TWIG Template - ${app.appName}</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">

	<#include "../_includes/header_body_start.ftl">
	<#include "../_includes/sidebar.ftl">
	
	<#-- Content Wrapper. -->
	<div class="content-wrapper">
		
		<#-- Page header -->
		<section class="content-header">
			<div class="container-fluid">
				<div class="row mb-2">
					<div class="col-sm-6">
						<h1 class="bold-text">TWIG Template: View</h1>
					</div>
					<div class="col-sm-6">
						<ol class="breadcrumb float-sm-right">
							<li class="breadcrumb-item"><a href="/">Home</a></li>
							<li class="breadcrumb-item">Graphics</li>
							<li class="breadcrumb-item active">
								<a href="/graphics/twigTemplate">TWIG Template</a>
							</li>
						</ol>
					</div>
				</div>
			</div>
		</section>

		<#-- TWIG Template - DataTable -->
		<section class="content">
			<div class="container-fluid">
				<div class="row">
					<div class="col-12">
						<div class="card card-primary card-outline">
						
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-binoculars"></i>&nbsp;View
								</h3>
							</div>

							<div class="card-body table-responsive">
								
								<#-- Button Options -->
								<div class="btn-group mb-3">
									<a href="/graphics/twigTemplate/add" class="btn btn-outline-primary">
										<i class="fa-solid fa-add"></i>&nbsp;Add
									</a>
									
									<button type="button" class="btn btn-outline-secondary" onclick = "dt.ajax.reload();">
										<i class="fa-solid fa-rotate"></i>&nbsp;Reload
									</button>
								</div>
							
								<#-- Table Content -->
								<table id="twigTemplate" class="table table-hover table-striped">
									<thead>
										<tr>
											<th>TemplateId</th>
											<th>Name</th>
											<th>Portfolio(s)</th>
											<th>Available From</th>
											<th>Available To</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header_body_end.ftl">
	
	<#--  jQuery DataTables plugin -->
	<link rel="stylesheet" type="text/css" href="/static/css/dataTables.bootstrap4.min.css">
    <script type="text/javascript" src="/static/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/static/js/dataTables.bootstrap4.min.js"></script>
	
	<#--  JS for TWIG template options. -->
	<script type="text/javascript" src="/static/js/etwig-twig-template-main.js"></script>
	
	<script>
    	var dt = twigTemplateDataTable();
	</script>
</body>
</html>