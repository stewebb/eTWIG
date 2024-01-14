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
  	
  		<#-- Page header -->
		<section class="content-header">
			<div class="container-fluid">
				<div class="row mb-2">
					<div class="col-sm-6">
						<h1 class="bold-text">TWIG Template: Design</h1>
					</div>
					<div class="col-sm-6">
						<ol class="breadcrumb float-sm-right">
							<li class="breadcrumb-item"><a href="/">Home</a></li>
							<li class="breadcrumb-item">Graphics</li>
							<li class="breadcrumb-item">
								<a href="/graphics/twigTemplate">TWIG Template</a>
							</li>
							<li class="breadcrumb-item">
								<a href="/graphics/twigTemplate/design">Design</a>
							</li>
						</ol>
					</div>
				</div>
			</div>
		</section>

    	<#-- Main area -->
    	<section class="content">
			<div class="container-fluid">


				<#-- Options (Stepper) -->
				<div class="card card-primary card-outline">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-wand-magic-sparkles"></i>&nbsp;Design
						</h3>
					</div>

					<div class="card-body">	
        				<div id="twigTemplateDesignStepper" class="bs-stepper">
        				
        					<#-- Header -->
         					<div class="bs-stepper-header" role="tablist">
         					
         					     <#-- Header 1: Background -->
            					<div class="step" data-target="#twigTemplateBackground">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateBackground">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-image" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Background</span>
              						</button>
            					</div>
            					<div class="bs-stepper-line"></div>
            					
            					<#-- Header 2: Logo -->
            					<div class="step" data-target="#twigTemplateLogo">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateLogo">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-star" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Logo</span>
              						</button>
            					</div>
            					<div class="bs-stepper-line"></div>
            					
            					<#-- Header 3: Title -->
            					<div class="step" data-target="#twigTemplateTitle">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateTitle">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-heading" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Title</span>
              						</button>
            					</div>
            					<div class="bs-stepper-line"></div>
            					
            					<#-- Header n: Submit -->
            					<div class="step" data-target="#twigTemplateSubmit">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateSubmit">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-save" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Submit</span>
              						</button>
            					</div>
          					</div>
          					
          					<#-- Content -->
          					<div class="bs-stepper-content">
            					<form onSubmit="return false">
            					
            						<#-- Content 1: Background -->
              						<div id="twigTemplateBackground" role="tabpanel" class="bs-stepper-pane" aria-labelledby="">
                						<#include "../_includes/twigTemplate_design_background.ftl">
                						<button class="btn btn-outline-primary right-div" onclick="stepper2.next()">
                							<i class="fa-solid fa-forward"></i>&nbsp;Next
                						</button>
              						</div>
              						
              						<#-- Content 2: Logo -->
              						<div id="twigTemplateLogo" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper2trigger2">
                						<div class="form-group">
                  							<label for="exampleInpuAddress1">Address</label>
                 							<input type="email" class="form-control" id="exampleInpuAddress1" placeholder="Enter your address">
                						</div>
                						
                						<#include "../_includes/stepper_btn.ftl">
              						</div>
              						
              						<#-- Content 3: Title -->
              						<div id="twigTemplateTitle" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper2trigger2">
                						<div class="form-group">
                  							<label for="exampleInpuAddress1">Address</label>
                 							<input type="email" class="form-control" id="exampleInpuAddress1" placeholder="Enter your address">
                						</div>
                						
                						<#include "../_includes/stepper_btn.ftl">
              						</div>
              						
              						<#-- Content n: Submit -->
              						<div id="twigTemplateSubmit" role="tabpanel" class="bs-stepper-pane text-center" aria-labelledby="stepper2trigger3">
                						<button type="submit" class="btn btn-primary mt-5">Submit</button>
              						</div>
            					</form>
          					</div>
          					
        				</div>
      				</div>
				</div>
			</div>
			
			<#-- Preview -->
			<#--  --include "../_includes/twigTemplate_addEdit_preview.ftl"> -->
		</section>

	</div>
	


	<#-- Footer -->
	<#include "../_includes/footer.ftl">

	<#include "../_includes/header_body_end.ftl">
	
	<#-- bs stepper -->
	<link rel="stylesheet" href="/static/css/bs-stepper.min.css">
	<script src="/static/js/bs-stepper.min.js"></script>
	
	<#-- Bootstrap color picker -->
	<link rel="stylesheet" href="/static/css/bootstrap-colorpicker.min.css">
	<script src="/static/js/bootstrap-colorpicker.min.js"></script>
	
	<#-- jQuery inputmask -->
   	<script src="/static/js/jquery.inputmask.min.js"></script>
   
   	<#-- Post Scripts -->
	<script>
	
		
		    $('.my-colorpicker2').colorpicker()

    $('.my-colorpicker2').on('colorpickerChange', function(event) {
      $('.my-colorpicker2 .fa-palette').css('color', event.color.toString());
    })
    
      $("#position").inputmask('99,99', {placeholder: "__,__"});
      
     stepper2 = new Stepper(document.querySelector('#twigTemplateDesignStepper'), {
    linear: false
  })
    </script>

</body>
</html>