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

					<#-- Col 1: Edit User -->
					<div class="row col-12">
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
									<#--
									<button type="button" class="btn btn-outline-primary right-div" onclick="updateUserDetails();">
                						<i class="fa-solid fa-check"></i>&nbsp;
										<span id="submitText">Submit</span>
                					</button>
									-->
									<#-- /Submit -->

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
								
								<#-- /Col 1: User Details -->

								<#-- Col 2: Position Information -->
								<#--
								
									<h5 class="mb-3 bold-text text-primary">
										<i class="fa-solid fa-user-tag"></i>&nbsp;Initial Position
									</h5>

									-- eTWIG Role --
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
									-- /eTWIG Role --

									-- Portfolio --
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
									-- /Portfolio --	

									-- Portfolio Email --
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
									-- /Portfolio Email -

									-- Position --
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
									-- /Position --

									-- Submit --
									<button type="button" class="btn btn-outline-primary right-div" onclick="addUser();">
                						<i class="fa-solid fa-check"></i>&nbsp;
										<span id="submitText">Submit</span>
                					</button>
									-- /Submit --

								
								-->
								<#-- /Col 2: Position Information -->
								
							
						</div>
					</div>
					<#-- /Add User -->

					</div>
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