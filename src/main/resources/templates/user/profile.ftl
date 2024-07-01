<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "USER">
<#assign nameParts = userBasicInfo.fullName?split(" ")>
<#assign firstName = nameParts[0]>

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>User Profile - ${app.appName}</title>
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
					<h1 class="bold-text">User Settings: ${userBasicInfo.fullName}</h1>
				</div>
			</section>
			<#-- /Page header -->

			<#-- Main area -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">

						<#-- Left column -->
						<div class="col-md-3">

							<#-- Profile -->
							<div class="card card-primary card-outline">
								<div class="card-body box-profile">
									
									<#-- Name and role -->
									<h3 class="profile-username text-center">${userBasicInfo.fullName}</h3>
									<p class="text-muted text-center">
										<#list userPermission.myRoles as role>
											${role.name}&nbsp;
										</#list>		
									</p>
									<#-- /Name and role -->
									
									<#-- User basic info -->
									<ul class="list-group list-group-unbordered mb-3">
										<li class="list-group-item">
											<b>User ID</b> <a class="float-right">${userBasicInfo.id}</a>
										</li>
										<li class="list-group-item">
											<b>Email</b> <a class="float-right">${userBasicInfo.email}</a>
										</li>
										<li class="list-group-item">
											<b>Last login</b> <a class="float-right"></a>
										</li>
									</ul>
									<#-- /User basic info -->
									
									<a href="/user/logout" class="btn btn-outline-primary btn-block">
										<i class="fa-solid fa-right-from-bracket"></i>&nbsp;Logout
									</a>
								</div>
							</div>
							<#-- Profile -->

						</div>
						<#-- /Left column -->
					
						<#-- Right column -->
						<div class="col-md-9">
							<div class="card card-outline card-primary">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-user-gear"></i>&nbsp;Information and Settings
									</h3>
								</div>

								<div class="card-body">

									<#-- My Access -->
									<div class="mb-3">
										<h5 class="bold-text mb-2">My eTWIG Access</h5>

										<div class="table-responsive">
											<table class="table table-head-fixed text-nowrap table-striped table-hover" id="eventRRuleAllDates">
												<thead>
													<tr>
														<th rowspan="2" style="vertical-align: middle; text-align: center;">Access</th> <!-- This header spans two rows -->
														<th rowspan="2" style="vertical-align: middle; text-align: center;">Status</th> <!-- This header spans two rows -->
														<th colspan="9" style="vertical-align: middle; text-align: center;">Detail</th> <!-- This header spans nine columns for specific permissions -->
													</tr>
													<tr>
														<!-- These are the sub-columns under 'Detail' -->
														<th>Event<br>Add</th>
														<th>Event<br>View</th>
														<th>Event<br>Edit</th>
														<th>Event<br>Delete</th>
														<th>Graphics<br>Banner</th>
														<th>Graphics<br>View</th>
														<th>Asset<br>Add</th>
														<th>Asset<br>View</th>
														<th>Asset<br>Delete</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Event Management</td>
														<td class="table-${userPermission.eventsAccess?string('success', 'danger')}">
															<#if userPermission.eventsAccess>
																<i class="fa-solid fa-check text-success"></i></td>
															<#else>
																<i class="fa-solid fa-xmark text-danger"></i></td>
															</#if>
														</td> <!-- Status -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Edit -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Delete -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics Banner -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset View -->
														<td><i class="fa-solid fa-xmark"></i></td> <!-- Asset Delete -->
													</tr>
													<tr>
														<td>Graphics Management</td>
														<td class="table-${userPermission.graphicsAccess?string('success', 'danger')}">
															<#if userPermission.graphicsAccess>
																<i class="fa-solid fa-check text-success"></i></td>
															<#else>
																<i class="fa-solid fa-xmark text-danger"></i></td>															
															</#if>
														</td> <!-- Status -->
														<td><i class="fa-solid fa-xmark"></i></td> <!-- Event Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event View -->
														<td><i class="fa-solid fa-xmark"></i></td> <!-- Event Edit -->
														<td><i class="fa-solid fa-xmark"></i></td> <!-- Event Delete -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics Banner -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset Delete -->

													</tr>
													<tr>
														<td>Administrator</td>
														<td class="table-${userPermission.adminAccess?string('success', 'danger')}">
															<#if userPermission.adminAccess>
																<i class="fa-solid fa-check text-success"></i></td>
															<#else>
																<i class="fa-solid fa-xmark text-danger"></i></td>															
															</#if>			
														</td> <!-- Status -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Edit -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Event Delete -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics Banner -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Graphics View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset Add -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset View -->
														<td><i class="fa-solid fa-check"></i></td> <!-- Asset Delete -->
													</tr>
												</tbody>
											</table>
										</div>

									</div>
									<#-- /My Access -->

									<#-- My Position -->
									<div class="mb-3">
										<h5 class="bold-text mb-2">My Position</h5>

										<div class="table-responsive" style="max-height: 300px;">
											<table class="table table-head-fixed text-nowrap table-striped table-hover" id="eventRRuleAllDates">
												<thead>
													<tr>
														<th rowspan="2" style="vertical-align: middle; text-align: center;">Position</th>
														<th colspan="3" style="vertical-align: middle; text-align: center;">Portfolio</th>
													</tr>
													<tr>
														<th>Name</th>
														<th>Email</th>
														<th>Color</th>
													</tr>
												</thead>
												<tbody>
													<#list userPosition.myPositions as position>		
														<tr <#if position.userRoleId == userPosition.myCurrentPosition.userRoleId>class="bold-text"</#if>>	
															<td>${position.position}</td>
															<td>${position.portfolioName}</td>
															<td>
																<a href="mailto:${position.portfolioEmail};">${position.portfolioEmail}</a>
															</td>
															<td style="color:#${position.portfolioColor};">
																<i class="fa-solid fa-square fa-xl"></i>&nbsp;#${position.portfolioColor}
															</td>
															<#--	
															<#if position.userRoleId == userPosition.myCurrentPosition.userRoleId>
																<option value="${position.userRoleId}" selected>${position.position}</option>
															<#else>
																<option value="${position.userRoleId}">${position.position}</option>
															</#if>	
															-->

														</tr>		
													</#list>
												</tbody>
											</table>
											<small class="form-text text-muted">
												<b>Row with bold text</b> indicates current selected position.
												You can switch positions by clicking the <b><i class="fa-solid fa-user fa-sm"></i>&nbsp;User</b> icon on the NavBar.
											</small>

										</div>
									</div>
									<#-- /My Position -->

									<#-- Change password. -->
									<div class="mb-2">
										<h5 class="bold-text mb-2">Change password</h5>

										<#-- Current pwd -->
										<div class="form-group row">
											<label for="currentPassword" class="col-lg-3 col-form-label">
												Current password&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-key"></i>
														</span>
													</div>
													<input type="password" class="form-control" id="currentPassword">
												</div>
											</div>
										</div>
										<#-- /Current pwd -->

										<#-- New pwd -->
										<div class="form-group row">
											<label for="newPassword" class="col-lg-3 col-form-label">
												New password&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-key"></i>
														</span>
													</div>
													<input type="password" class="form-control" id="newPassword">
												</div>
												<small class="form-text text-muted">
													Must be at least 8 characters long and include uppercase, lowercase and numbers.
												</small>
											</div>
										</div>
										<#-- /New pwd -->

										<#-- Confirm new pwd -->
										<div class="form-group row">
											<label for="confirmNewPassword" class="col-lg-3 col-form-label">
												Confirm new password&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-key"></i>
														</span>
													</div>
													<input type="password" class="form-control" id="confirmNewPassword">
												</div>
											</div>
										</div>
										<#-- /Confirm new pwd -->
										
										<#-- Submit -->
										<button type="button" class="btn btn-outline-primary right-div" onclick = "changePassword();">
                							<i class="fa-solid fa-check"></i>&nbsp;Submit
                						</button>
										<#-- /Submit -->

									</div>
									<#-- /Change password. -->
									
								</div>

							</div>
						</div>
						<#-- /Right column -->

					</div>
				</div>
			</section>
			<#-- /Main area -->


		</div>
		<#-- /Content Wrapper. -->
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- Custom JS for profile management-->
	<script src="/static/js/etwig/user.js?ver=${app.appVersion}"></script>
</body>
</html>