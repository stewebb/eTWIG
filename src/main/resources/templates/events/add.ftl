<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The add event page.
   -->
   
<#assign navbar = "CALENDAR">

<#-- Add mode. -->
<#assign isEdit = false>
<#assign isEditStr = "false">

<#-- In add mode, inputs are also enabled, -->
<#assign disabled = false>
<#assign disabledStr ="">

<#-- In add mode, mode is always called "Add". -->
<#assign modeStr = "Add">

<#-- In add mode, the link of this page is always "/event/add". -->
<#assign link = "add">

<#-- In add mode, the default time unit is always h (hour) and the default duration is always 1.  -->
<#assign timeUnit = "h">
<#assign duration = 1>

<#-- In add mode, the duration input will never been hidden.  -->
<#assign durationHidden = false>

<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header/head.ftl">
	<title>Add Event - ${app.appName}</title>
</head>

<body class="hold-transition layout-top-nav">
	<#include "../_includes/header/body_start.ftl">

	<#-- Main Wrapper -->
	<div class="wrapper">

		<#-- Navbar -->
		<#include "../_includes/navbar.ftl">
		<#-- /Navbar -->

		<#-- Content Wrapper. -->
  		<div class="content-wrapper">
  	
			<#-- Page header -->
    		<#include "../_includes/events/addEdit_header.ftl">
    		<#-- /Page header -->
    	
    		<#-- Main area -->
    		<section class="content">
				<div class="container-fluid">
				
					<#--
					<div class="card card-primary card-outline">
						<div class="card-header">
							<h3 class="card-title">
								<i class="fa-regular fa-gear"></i>&nbsp;Options
							</h3>
						</div>
						<div class="card-body">
						</div>
					</div>
					-->

				
			
					<div id="stepper2" class="bs-stepper bg-white">
          <div class="bs-stepper-header" role="tablist">
            <div class="step" data-target="#test-nl-1">
              <button type="button" class="step-trigger" role="tab" id="stepper2trigger1" aria-controls="test-nl-1">
                <span class="bs-stepper-circle">
                  <span class="fas fa-user" aria-hidden="true"></span>
                </span>
                <span class="bs-stepper-label">Name</span>
              </button>
            </div>
            <div class="bs-stepper-line"></div>
            <div class="step" data-target="#test-nl-2">
              <button type="button" class="step-trigger" role="tab" id="stepper2trigger2" aria-controls="test-nl-2">
                <span class="bs-stepper-circle">
                  <span class="fas fa-map-marked" aria-hidden="true"></span>
                </span>
                <span class="bs-stepper-label">Address</span>
              </button>
            </div>
            <div class="bs-stepper-line"></div>
            <div class="step" data-target="#test-nl-3">
              <button type="button" class="step-trigger" role="tab" id="stepper2trigger3" aria-controls="test-nl-3">
                <span class="bs-stepper-circle">
                  <span class="fas fa-save" aria-hidden="true"></span>
                </span>
                <span class="bs-stepper-label">Submit</span>
              </button>
            </div>
          </div>
          <div class="bs-stepper-content">
            <form onSubmit="return false">
              <div id="test-nl-1" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper2trigger1">
                <div class="form-group">
                  <label for="exampleInputName1">Name</label>
                  <input type="email" class="form-control" id="exampleInputName1" placeholder="Enter your name">
                </div>
                <button class="btn btn-primary" onclick="stepper2.next()">Next</button>
              </div>
              <div id="test-nl-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper2trigger2">
                <div class="form-group">
                  <label for="exampleInpuAddress1">Address</label>
                  <input type="email" class="form-control" id="exampleInpuAddress1" placeholder="Enter your address">
                </div>
                <button class="btn btn-primary" onclick="stepper2.next()">Next</button>
              </div>
              <div id="test-nl-3" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="stepper2trigger3">
                <button type="submit" class="btn btn-primary mt-5">Submit</button>
              </div>
            </form>
          </div>
        </div>
				
				</div>
			</section>
			<#-- /Main area -->
		
		</div>
		<#-- /Content Wrapper -->

	</div>
	<#-- Main Wrapper -->
	
	<#-- Footer -->
	<#include "../_includes/footer.ftl">
	
	<#include "../_includes/header/body_end.ftl">
	
	<#-- Post Scripts -->
	<#include "../_includes/events/addEdit_postScripts.ftl">
</body>
</html>