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
	<#include "../_includes/modal.ftl">
	
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
								<a href="/graphics/twigTemplate/view">TWIG Template</a>
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


				<#-- Card -->
				<div class="card card-primary card-outline">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-wand-magic-sparkles"></i>&nbsp;Design
						</h3>
					</div>

					<#-- Card Body -->
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
            					<#-- /Header 1: Background -->
            					
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
            					<#-- /Header 2: Logo -->
            					
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
            					<#-- /Header 3: Title -->
            					
            					<#-- Header 4:Week -->
            					<div class="step" data-target="#twigTemplateWeek">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateWeek">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-calendar-week" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Week Name</span>
              						</button>
            					</div>
            					<div class="bs-stepper-line"></div>
            					<#-- Header 4:Week -->
            					
            					<#-- Header n: Submit -->
            					<div class="step" data-target="#twigTemplateSubmit">
              						<button type="button" class="step-trigger" role="tab" aria-controls="twigTemplateSubmit">
                						<span class="bs-stepper-circle">
                  							<span class="fas fa-save" aria-hidden="true"></span>
                						</span>
                						<span class="bs-stepper-label">Submit</span>
              						</button>
            					</div>
            					<#-- /Header n: Submit -->

          					</div>
          					<#-- /Header -->
          					
          					<#-- Content -->
          					<div class="bs-stepper-content">
            					<form onSubmit="return false">
            					
            						<#-- Content 1: Background -->
            						<#assign prev = false>
            						<#assign next = true>
            						
              						<div id="twigTemplateBackground" role="tabpanel" class="bs-stepper-pane" aria-labelledby="">
                						<#include "../_includes/twigTemplate_design_background.ftl">
										<#include "../_includes/stepper_btn.ftl">
              						</div>
              						<#-- /Content 1: Background -->
              						
              						<#-- Content 2: Logo -->
              						<#assign prev = true>
            						<#assign next = true>
            						
              						<div id="twigTemplateLogo" role="tabpanel" class="bs-stepper-pane" aria-labelledby="twigTemplateSteppertrigger2">
                						<#assign currentElement = design.logo>
                						<#assign elementName = "logo">
                						<#include "../_includes/twigTemplate_design_common.ftl">
                						<#include "../_includes/stepper_btn.ftl">
              						</div>
              						<#-- /Content 2: Logo -->
              						
              						<#-- Content 3: Title -->
              						<#assign prev = true>
            						<#assign next = true>
            						
              						<div id="twigTemplateTitle" role="tabpanel" class="bs-stepper-pane" aria-labelledby="twigTemplateSteppertrigger2">
                						<#assign currentElement = design.title>
                						<#assign elementName = "title">
                						<#include "../_includes/twigTemplate_design_common.ftl">
                						<#include "../_includes/stepper_btn.ftl">
              						</div>
              						<#-- /Content 3: Title -->
              						
              						<#-- Content 4: Week -->
              						<#assign prev = true>
            						<#assign next = true>
            						
              						<div id="twigTemplateWeek" role="tabpanel" class="bs-stepper-pane" aria-labelledby="twigTemplateSteppertrigger2">
                						<#assign currentElement = design.week>
                						<#assign elementName = "week">
                						<#include "../_includes/twigTemplate_design_common.ftl">
                						<#include "../_includes/stepper_btn.ftl">
              						</div>
              						<#-- /Content 4: Week -->
              						
              						<#-- Content n: Submit -->
              						<#assign prev = true>
            						<#assign next = false>
              						<div id="twigTemplateSubmit" role="tabpanel" class="bs-stepper-pane" aria-labelledby="twigTemplateSteppertrigger3">

										<div class="callout callout-primary">
											<h5 class="bold-text mb-3">Heads up!</h5>
											Before you update the design, please make sure you have <span class="bold-text text-primary">previewed</span> the layout.
										</div>
										<#include "../_includes/stepper_btn.ftl">
              						</div>
              						<#-- /Content n: Submit -->
              						
            					</form>
          					</div>
          					<#-- /Content -->
          					
        				</div>
      				</div>
      				<#-- Card Body -->
      				
				</div>
				<#-- /Card -->
				
				<#-- Preview -->
				<#include "../_includes/twigTemplate_design_preview.ftl">
			</div>
		</section>
		<#-- /Main area -->

	</div>
	<#-- /Content Wrapper -->
	

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
   
    <#--  JS for TWIG template options. -->
	<script type="text/javascript" src="/static/js/etwig-twig-design.js"></script>
	<script type="text/javascript" src="/static/js/etwig-twig-template.js"></script>
	
	<#-- p5.js -->
	<script src="/static/js/p5.min.js"></script>
	
   	<#-- Post Scripts -->
	<script>
	
		// A prompt diaglue displays if the window size is lower than the recommended size.
		windowSizeCheck();
		
		// Initialize the preview.
		getCurrentDesign();
		
		// Initialize all position input masks,
		$("[id$='Position']").each(function() {
			$('#'+this.id).inputmask('(99,99)', {placeholder: "(__,__)"});
		});
		
		// Color picker
		$('.color-picker').colorpicker()
    	$('.color-picker').on('colorpickerChange', function(event) {
      		$('.color-picker .fa-palette').css('color', event.color.toString());
    	})

		// Initialize the template stepper
     	twigTemplateStepper = new Stepper(document.querySelector('#twigTemplateDesignStepper'), {
    		linear: false
  		});
    </script>

</body>
</html>