<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "ADMIN_USER_LIST">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>User Management - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#--
	<input type="hidden" id="approvalDetailsLink" value="${ENDPOINTS.GRAPHICS_APPROVAL_DETAILS}"> 
	-->
	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">

	
		<#include "../_includes/navbar.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">
			
			<#-- Page header -->
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="bold-text">User and Position Management</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.ADMIN_USER_LIST}">User</a>&nbsp|&nbsp
									<!--<a href="${ENDPOINTS.GRAPHICS_SUMMARY_LIST}">Summary</a>-->
								</li>
							</ol>
						</div>
					</div>
				</div>
			</section>
			<#-- Page header -->

			<#-- Main Content -->
			<section class="content">
				<div class="container-fluid">

					<#-- Request List -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-list-check"></i>&nbsp;List
							</h3>
						</div>

						<div class="card-body table-responsive">
							<table id="usersTable" class="display table table-hover table-striped" width="100%">
								<thead>
									<tr>
										<th colspan="4" style="vertical-align: middle; text-align: center;">User</th>
										<th colspan="3" style="vertical-align: middle; text-align: center;">Position</th>
										<th rowspan="2" style="vertical-align: middle; text-align: center;">Action</th>
									</tr>
									<tr>
										<th>ID</th>
										<th>Name</th>
										<th>Email</th>
										<th>Last Login</th>
										<th>Position</th>
										<th>Portfolio</th>
										<th>Email</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>

					</div>
					<#-- /Request List -->

				</div>
			</section>
			<#-- /Main Content -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<#-- JS for Banner Request Approval -->
	<#--
	<script type="text/javascript" src="/static/js/etwig/banner-approval.js?ver=${app.appVersion}"></script>
	
	<script>
		bannerRequestListTable();
	</script>
	-->

	<script>
	$('#usersTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/api/user/list",
            "type": "GET",
            "data": function(d) {
                // Add any additional parameters here
                //d.portfolioId = $('#portfolioId').val();
                //d.roleId = $('#roleId').val();
                d.start = d.start;
                d.length = d.length;
                d.draw = d.draw;
                d.sortColumn = d.columns[d.order[0].column].data;
                d.sortDirection = d.order[0].dir;
            }
        },
        "columns": [
            { "data": "userId" },
            { "data": "userName" },
            { 
                "data": null,
                "defaultContent": "" // Placeholder for email column
            },
            { 
                "data": null,
                "defaultContent": "" // Placeholder for last login column
            },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.name).join("<br>");
                }
            },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.portfolio).join("<br>");
                }
            },
            { 
                "data": "userPositions",
                "render": function(data, type, row) {
                    return data.map(pos => pos.email).join("<br>");
                }
            },
            {
                "data": null,
                "defaultContent": "<button class='btn btn-outline-primary btn-sm'>Action</button>"
            }
        ]
    });
	</script>
</body>
</html>