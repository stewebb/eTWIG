<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
   
<#assign navbar = "GRAPHICS_EVENTS">

<!DOCTYPE html>
<html>
<head>

	<#include "../_includes/header/head.ftl">
	<title>Event Graphics View - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	
	<#include "../_includes/header/body_start.ftl">
	
	<#-- Main Wrapper -->
	<div class="wrapper">
		<#include "../_includes/navbar.ftl">
		<#include "../_includes/modal.ftl">

		<#-- Content Wrapper. -->
		<div class="content-wrapper">

			<#-- Page header -->
	    	<section class="content-header">
	      		<div class="container-fluid">
	        		<div class="row mb-2">
	          			<div class="col-sm-6">
	            			<h1 class="bold-text" id="eventPageTitle">
	            				Event Graphics View: ${eventInfo.name}
	            			</h1>
	          			</div>
	          			<div class="col-sm-6">
	            			<ol class="breadcrumb float-sm-right">
	              				<li class="breadcrumb-item"><a href="/graphics">Graphics</a></li>
								<li class="breadcrumb-item"><a href="/graphics/events/list">Event Graphics</a></li>
								<li class="breadcrumb-item active"><a href="/graphics/events/view?eventId=${eventInfo.id}">View</a></li>
	            			</ol>
	          			</div>
	        		</div>
	      		</div>
	    	</section>
			<#-- /Page header -->

			<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
					<div class="row col-12">

						<#-- Event Info -->
						<div class="col-md-6">
							<div class="card card-primary card-outline mb-3">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
									</h3>
								</div>
								<div class="card-body">

									
									<#include "../_includes/events/graphics_info.ftl">
								</div>
							</div>						
						</div>
						<#-- /Event Info -->
											
						<#-- Col 2 -->
						<div class="col-md-6">	

							<#-- Graphics -->
							<div class="card card-primary card-outline mb-3">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-image"></i>&nbsp;Graphics Preview
									</h3>
								</div>

								<div class="card-body table-responsive">


									<#-- Table Content -->
									<table class="table">
										<thead>
											<tr>
												<th>Id</th>
												<th>Thumbnail</th>
												<th>Operator</th>
												<th>Position</th>
												<th>Uploaded</th>
												<th>Action</th>
											</tr>
										</thead>

										<tbody>

											<#-- Graphics -->
											<tr>
												<th colspan="6">
													<span class="text-primary">
														<center>
															<i class="fa-solid fa-down-left-and-up-right-to-center"></i>
															&nbsp;TWIG Component(s) (Count: ${eventGraphics?size})
														</center>
													</span>
												</th>
											</tr>
											<#list eventGraphics as graphics>
												<#include "../_includes/graphics/event_graphics_table.ftl">
											</#list>
											<#-- /Graphics -->			

											<#-- Banners -->
											<tr>
												<th colspan="6">
													<span class="text-primary">
														<center>
															<i class="fa-solid fa-up-right-and-down-left-from-center"></i>
															&nbsp;Banner(s) (Count: ${eventBanners?size})
														</center>
													</span>
												</th>
											</tr>
											<#list eventBanners as graphics>
												<#include "../_includes/graphics/event_graphics_table.ftl">
											</#list>
											<#-- /Banners -->			

										</tbody>
									</table>
									<#-- /Table Content -->

								</div>		
							</div>
							<#-- /Graphics -->

							<#-- Add -->
							<div class="card card-primary card-outline">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fa-solid fa-plus"></i>&nbsp;Add Graphics
									</h3>
								</div>

								<div class="card-body">

									<#-- Role -->
									<div class="form-group row">
										<label for="operatorRole" class="col-sm-2 col-form-label">
											Role&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												
												<select class="form-control select2bs4" name="operatorRole" id="operatorRole"></select>
											</div>
											<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
										</div>
									</div>
									<#-- /Role -->

									<#-- Type -->
									<div class="form-group row">
										<label for="graphicsType" class="col-sm-2">
											Type&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="form-group clearfix">
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="graphicsTypeComponent" name="graphicsType" value="1">
													<label for="graphicsTypeComponent">TWIG Component</label>
												</div>
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="graphicsTypeBanner" name="graphicsType" value="0">
													<label for="graphicsTypeBanner">Banner</label>
												</div>
											</div>				
										</div>
									</div>
									<#-- /Type -->

									<#-- Assets -->
									<div class="form-group row" id="graphicsApprovalAssets">
										<label for="graphicsApprovalAssets" class="col-sm-2 col-form-label">
											Assets&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
										
											<div class="input-group mb-3">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-image"></i>
													</span>
												</div>
												<input type="text" class="form-control template-image-input" id="uploadCallback" readonly>
												
												<div class="input-group-append">
													<button type="button" id="graphicsApprovalAssetsBtn" class="btn btn-outline-secondary" onclick="selectUpload();">
														<i class="fa-regular fa-upload"></i>&nbsp;Select/Upload
													</button>		
												</div>
											</div>

											<img src="about:blank" class="img-fluid" id="uploadImage" />			
										</div>
									</div>
									<#-- /Assets -->	

									<#-- Submit -->
									<div class="right-div" role="group">
										<button type="button" class="btn btn-outline-primary" onclick="addGraphics();">
											<i class="fa-regular fa-check"></i>&nbsp;Submit
										</button>
									</div>
									<#-- /Submit -->

								</div>		
							</div>
							<#-- /Add -->

						</div>
						<#-- /Col 2 -->

					</div>
				</div>
				<#-- /Graphics -->

			</section>
			<#-- /Main area -->

		</div>
		<#-- /Content Wrapper. -->
		
	</div>
	<#-- Main Wrapper -->
	
	<#include "../_includes/footer.ftl">
	<#include "../_includes/header/body_end.ftl">

	<#-- JS for Graphics Approval -->
	<script type="text/javascript" src="/static/js/etwig/graphics-events.js"></script>
	
	<script>
	
		
		$(document).ready(function() {
			//$('input[type=radio][name=graphicsApprovalOption]').change(function() {
			//	setAssetsUpload(this.value);
			//});

			$('.select2bs4').select2({
      			theme: 'bootstrap4'
    		})

			var myPositions = getMyPositions();
			for (var key in myPositions) {
  				$("#operatorRole").append('<option value="' + myPositions[key].userRoleId + '">' + myPositions[key].position + ', ' + myPositions[key].portfolioName + '</option>');
			}
		});
		
	
	</script>

</body>
</html>


									

									

									<#-- Feedback --
									<div class="form-group row">
										<label for="graphicsApprovalComments" class="col-sm-2">Comments</label>
										<div class="col-sm-10">
											<textarea id="graphicsApprovalComments" class="form-control fixed-textarea" maxlength="255" rows="5"></textarea>
											<small class="form-text text-muted">Feedback to requester, up to 255 characters.</small>
										</div>
									</div>
									<#-- /Feedback -->

									

									