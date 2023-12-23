<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	<title>Edit Event - eTWIG Administration Portal</title>
</head>

<body>
	<#include "../_includes/sidebar.ftl">
	
	<#-- Content Wrapper -->
  	<div class="content-wrapper">
  	
    	<#-- Page header -->
    	<section class="content-header">
      		<div class="container-fluid">
        		<div class="row mb-2">
          			<div class="col-sm-6">
            			<h1 class="bold-text">Edit Event</h1>
          			</div>
          			<div class="col-sm-6">
            			<ol class="breadcrumb float-sm-right">
              				<li class="breadcrumb-item"><a href="/">Home</a></li>
              				<li class="breadcrumb-item">Events</li>
              				<li class="breadcrumb-item active"><a href="/events/edit">Edit Starter</a></li>
            			</ol>
          			</div>
        		</div>
      		</div>
    	</section>
    	
    	<#-- Main area -->
    	<section class="content">
      		<div class="container-fluid">
      			<div class="card">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-flag"></i>&nbsp;Starter
						</h3>
					</div>
					<div class="card-body">
					
						<#-- Reasons and suggestions -->
						<#if mode=="DEFAULT">
							<div class="callout callout-info">
								<h5 class="bold-text mb-3">Create an event</h5>
								You can click the <span class="theme-color-text"><i class="nav-icon fas fa-plus"></i>&nbsp;Add event</span> button in the left side-bar or 
								<a href="/events/edit?eventId=-1" class="theme-color-text">Here</a> to create a new event.
							</div>
						<#elseif mode=="INVALID_EVENT_ID">
							<div class="callout callout-danger">
								<h5 class="bold-text mb-3">Invalid Event ID</h5>
								The eventId=${eventId} is invalid. It must be a Long number.
								You may wish to <button type="button" onclick="history.back();" class="btn btn-sm btn-outline-danger">go back</button>.
							</div>
						<#else>
							<div class="callout callout-warning">
								<h5 class="bold-text mb-3">Event doesn't exist</h5>
								The eventId=${eventId} doesn't exist in the database.
								You may wish to <button type="button" onclick="history.back();" class="btn btn-sm btn-outline-warning">go back</button>.
							</div>
						</#if>
						
						<#-- Go to a new event by its Id -->
						<div class="callout callout-primary">
							<h5 class="bold-text mb-3">Retype eventId</h5>
							<form action="/events/edit" method="get">
								<div class="input-group">
  									<div class="input-group-prepend">
    									<span class="input-group-text" id="basic-addon1"><i class="fa-regular fa-id-card"></i></span>
  									</div>
  									<input type="text" name="eventId" class="form-control" required>
  									<span class="input-group-append">
										<input type="submit" class="btn btn-primary btn-flat" value="Go!"></input>
									</span>
								</div>
							</form>
						</div>
						
					</div>
				</div>
      		</div>
      	</div>
	</div>
</body>
</html>