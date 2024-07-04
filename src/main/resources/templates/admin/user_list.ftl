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
									<a href="${ENDPOINTS.ADMIN_PORTFOLIO_LIST}">Portfolio</a>
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

					<#-- User List -->
					<div class="card card-primary card-outline mb-3">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-users-viewfinder"></i>&nbsp;User List
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
					<#-- /User List -->

					<#-- Add User -->
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-solid fa-user-plus"></i>&nbsp;Add User
							</h3>
						</div>

						<div class="card-body">
							<div class="row col-12 mb-3">

								<#-- Col 1: User Details -->
								<div class="col-md-6">
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-circle-info"></i>&nbsp;New User Details
									</h5>

									<#-- Notice -->
									<div class="callout callout-primary mb-3">
										<h5 class="bold-text mb-3">Assign multiple positions to a new user?</h5>
										You need to add an initial position here, then add more positions in the user details page.
									</div>
									<#-- /Notice -->

									<#-- Full Name -->
									<div class="form-group row">
										<label for="userFullName" class="col-lg-3 col-form-label">Full Name</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userFullName" placeholder="John Doe" maxlength="63">
											</div>
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Full Name -->

									<#-- Email -->
									<div class="form-group row">
										<label for="userEmail" class="col-lg-3 col-form-label">Email</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-at"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userEmail" placeholder="me@example.com" maxlength="63">
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
												<input type="text" class="form-control" id="userPassword" autocomplete="new-password">
											</div>
											<small class="form-text text-muted">
												Must be at least 8 characters long and include uppercase, lowercase and numbers.
											</small>										
										</div>
									</div>
									<#-- /Password -->

								</div>
								<#-- /Col 1: User Details -->

								<#-- Col 2: Position Information -->
								<div class="col-md-6">
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-user-tag"></i>&nbsp;Initial Position
									</h5>

									<#-- eTWIG Role -->
									<div class="form-group row">
										<label for="userSystemRole" class="col-lg-3 col-form-label">eTWIG Role</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-key"></i>
													</span>
												</div>
												<select class="form-control select2bs4" id="userSystemRole">
													<option value="1">Event Manager</option>
													<option value="2">Graphics Manager</option>
													<option value="3">Administrator</option>
												</select>	
											</div>
											<small class="form-text text-muted">The role used in eTWIG system.</small>
										</div>
									</div>
									<#-- /eTWIG Role -->

									<#-- Portfolio -->
									<div class="form-group row">
										<label for="userPortfolio" class="col-lg-3 col-form-label">Portfolio</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-briefcase"></i>
													</span>
												</div>
									
												<select class="form-control select2 select2bs4" name="userPortfolio" id="userPortfolio">
        											<#list portfolios as portfolio>
														<option value="${portfolio.id}">${portfolio.name}												</option>
													</#list>
      											</select>
											</div>
											<small class="form-text text-muted">The portfolio assigned by Griffin Hall.</small>
										</div>
									</div>
									<#-- /Portfolio -->		

									<#-- Portfolio Email -->
									<div class="form-group row">
										<label for="userPortfolioEmail" class="col-lg-3 col-form-label">Portfolio Email</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-envelope"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userPortfolioEmail" placeholder="me@example.com" maxlength="63">
											</div>
											<small class="form-text text-muted">Must be a valid email format and up to 63 characters. Must be differeent than any other existing records.</small>
										</div>
									</div>
									<#-- /Portfolio Email -->

									<#-- Position -->
									<div class="form-group row">
										<label for="userPosition" class="col-lg-3 col-form-label">Position</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="userPosition" placeholder="e.g., Arts Rep" maxlength="63">
											</div>
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Position -->

									<#-- Submit -->
									<button type="button" class="btn btn-outline-primary right-div" onclick="addUser();">
                						<i class="fa-solid fa-check"></i>&nbsp;
										<span id="submitText">Submit</span>
                					</button>
									<#-- /Submit -->

								</div>
								<#-- /Col 2: Position Information -->
								
							</div>
						</div>
					</div>
					<#-- /Add User -->

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