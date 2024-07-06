<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The twig template page.
   -->
   
<#assign navbar = "ADMIN_USER">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>User Details - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#--
	<input type="hidden" id="userDetailsLink" value="${ENDPOINTS.ADMIN_USER_DETAILS}"> 
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
							<h1 class="bold-text">User and Position Details: ${selectedUserDetails.fullName}</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item">
									<a href="${ENDPOINTS.ADMIN_USER_LIST}">User</a>
								</li>
								<li class="breadcrumb-item active">
									<a href="${ENDPOINTS.ADMIN_USER_DETAILS}?userId=${selectedUserDetails.id}">Details</a>
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
					<div class="row col-12">

						<#-- Col 1: Edit User -->
						<div class="col-md-6">

							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-user-pen"></i>&nbsp;Edit User Information
									</h3>
								</div>

								<div class="card-body">

									<#-- userId -->
									<div class="form-group row">
										<label for="userId" class="col-lg-3">User ID</label>
										<div class="col-lg-9" id="userId">${selectedUserDetails.id}</div>
									</div>
									<#-- /userId -->
												
									<#-- Full Name -->
									<div class="form-group row">
										<label for="userFullName" class="col-lg-3 col-form-label">
											Full Name&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userFullName" placeholder="John Doe" maxlength="63" value="${selectedUserDetails.fullName}">
											</div>
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Full Name -->

									<#-- Email -->
									<div class="form-group row">
										<label for="userEmail" class="col-lg-3 col-form-label">
											Email&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-at"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userEmail" placeholder="me@example.com" maxlength="63" value="${selectedUserDetails.email}">
											</div>
											<small class="form-text text-muted">Must be a valid email format and up to 63 characters.</small>
										</div>
									</div>
									<#-- /Email -->

									<#-- Password -->
									<div class="form-group row">
										<label for="userPassword" class="col-lg-3 col-form-label">Password</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-lock"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userPassword" autocomplete="new-password" placeholder="Keep blank if you don't want to change user's password.">
											</div>
											<small class="form-text text-muted">
												Must be at least 8 characters long and include uppercase, lowercase and numbers.
											</small>										
										</div>
									</div>
									<#-- /Password -->

									<#-- Last Login -->
									<div class="form-group row">
										<label for="userLastLogin" class="col-lg-3">Last Login</label>
										<div class="col-lg-9" id="userLastLogin">${selectedUserDetails.lastLogin?replace("T", " ")}</div>
									</div>
									<#-- /Last Login -->

									<#-- Submit -->
									<div class="d-flex justify-content-between" role="group">

										<a class="btn btn-outline-secondary" href="${ENDPOINTS.ADMIN_USER_LIST}">
											<i class="fa-regular fa-arrow-left"></i>&nbsp;Back to list
										</a>
										
										<#--
										<button type="button" class="btn btn-outline-danger confirm-btn" data-action='{"functionName": "removeBannerRequest", "params": [${requestInfo.id}]}'>
											<i class="fa-solid fa-trash"></i>&nbsp;Delete
										</button>
										-->

										<button type="button" class="btn btn-outline-primary" onclick="updateUserDetails();">
											<i class="fa-regular fa-check"></i>&nbsp;Submit
										</button>
									</div>
									<#-- /Submit -->	

								</div>
							</div>
						</div>							
						<#-- / Col 1: Add User -->

						<#-- Col 2: Edit Role(s) -->
						<div class="col-md-6">

							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-user-tag"></i>&nbsp;Edit User Role(s)
									</h3>
								</div>

								<div class="card-body">

									<#-- User Role List -->
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-map-pin"></i>&nbsp;Basic
									</h5>

									<div class="table-responsive">
										<table class="display table table-hover table-striped" width="100%">
											<thead>
												<tr>
													<th>Role ID</th>
													<th>Position</th>
													<th>Portfolio Name</th>
													<th>Portfolio Email</th>
													<th>eTWIG Role</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<#list selectedUserRoles as role>
													<tr>
														<td>${role.id}</td>
														<td>${role.position}</td>
														<td>Portfolio Name</td>
														<td>Portfolio Email</td>
														<td>eTWIG Role</td>
														<td>Action</td>
													</tr>
												</#list>
											</tbody>
										</table>
									</div>
									<#-- /User Role List -->

								</div>
							</div>
						</div>
						<#-- /Col 2: Edit Role(s) -->

					</div>
				</div>
			</section>
			<#-- /Main Content -->

		</div>
		<#-- /Content Wrapper. -->

	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">
	
	<script type="text/javascript" src="/static/js/etwig/admin-user.js?ver=${app.appVersion}"></script>
	
	<script>
		userListTable();
	</script>
</body>
</html>