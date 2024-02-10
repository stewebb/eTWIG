<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The dashboard (site main) page.
   -->
<#assign navbar = "TWIG">

<!DOCTYPE html>
<html lang="en">
<head>

    <#-- Required meta tags. -->
	<meta charset="utf-8">
	<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<#-- The eTWIG icon. -->
	<link rel="icon" href="/static/images/favicon.ico">

    <title>TWIG - ${app.appName}</title>
	
</head>

<body>
	<link rel="stylesheet" href="/static/css/twig.css">

    <#-- Griffin Hall logo -->
    <div id="logo-container">
        <img src="/static/images/GHLogo.min.svg" class="beating-logo" id="logo" width="50%" height="50%"/>
    </div>
	<#-- /Griffin Hall logo -->

	<#-- Required resources -->
	<#include "./_includes/header/body_start.ftl">

	<#-- p5.js -->
	<script src="/static/js/vendor/p5.min.js"></script>
	
	<script src="/static/js/etwig/twig.js"></script>
	<script src="/static/js/etwig/twig-draw.js"></script>

	<#-- Settings/Share Modal -->
	<div class="modal fade" tabindex="-1" id="etwigSettingBox">
  		<div class="modal-dialog">
    		<div class="modal-content">

      			<div class="modal-header">
        			<h5 class="modal-title bold-text text-primary">
        				<i class="fa-solid fa-share"></i>&nbsp;Settings and Share
        			</h5>
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          				<span aria-hidden="true">&times;</span>
       				</button>
      			</div>
      			
     			<div class="modal-body">
     				
					<#-- Portfolio -->
					<div class="form-group row">
						<label for="eventPortfolio" class="col-sm-2 col-form-label">Portfolio</label>
						<div class="col-sm-10 input-group">
									
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fa-solid fa-briefcase"></i></span>
							</div>
							
							<select class="form-control select2" name="twigPortfolio" id="twigPortfolio">
      							<optgroup label="All portfolios">
      								<option value="-1" selected>All portfolios</option>
      							</optgroup>
      							<optgroup label="Portfolio(s) with separate calendar">
	        						<#if portfolioSeparatedCalendar?has_content>
        								<#list portfolioSeparatedCalendar as portfolio>
        									<option value="${portfolio.id}">${portfolio.name}</option>
										</#list>
									<#else>
										<option value="-2" disabled>(No portfolio)</option>
        							</#if>
        						</optgroup>
      						</select>
      					</div>
     				</div>
     				<#-- /Portfolio -->

     				<#-- Date -->
					<div class="form-group row">
						<label for="twigWeek" class="col-sm-2 col-form-label">Date</label>
						<div class="col-sm-10">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text">
										<i class="fa-solid fa-calendar-week"></i>
									</span>
								</div>
								<input type="text" class="form-control" id="twigWeek" onChange="getWeekByDate(datepicker.getDate());">
							</div>
							<div id="weekWrapper" class="datepicker"></div>
						</div>
					</div>		
     				<#-- /Date -->
							
					<#-- Week -->
     				<div class="form-group row">
     					<div class="col-sm-2 bold-text">Week</div>
     					<div class="col-sm-10" id="calculatedWeek"></div>
     				</div>
							<#-- /Week -->
						
							<hr class="mb-3"/>
							
							<#-- Export -->
							<div class="row">
								<label for="twigLink" class="col-sm-3 col-form-label">Export</label>
								<div class="col-sm-9">
									<div class="form-group clearfix">
									
										<#-- PNG -->
										<div class="icheck-primary">
											<input type="radio" id="downloadPNG" name="downloadOption" checked>
											<label for="downloadPNG">PNG</label>
										</div>
										
										<#-- JPG -->
										<div class="icheck-primary">
											<input type="radio" id="downloadJPG" name="downloadOption">
											<label for="downloadJPG">JPG</label>
										</div>
										
										<#-- Download button -->
										
										<div class="btn-group">
      										<button class="btn btn-outline-primary disabled-by-default">
      											<i class="fa-solid fa-download"></i>&nbsp;Download
      										</button>
      				
      										<button class="btn btn-outline-primary disabled-by-default">
      											<i class="fa-solid fa-print"></i>&nbsp;Print
      										</button>
     									</div>
     								
									</div>
								</div>
							</div>
							
							<#-- Copy link -->
							<div class="form-group row">
								<label for="twigLink" class="col-sm-3 col-form-label">Copy link</label>
								<div class="col-sm-9 input-group">
  									<div class="input-group-prepend">
    									<span class="input-group-text"><i class="fa-solid fa-link"></i></span>
  									</div>
  									<input type="text" id="twig-link" class="form-control" value="/twig/content" readonly>
  									<span class="input-group-append">
										<button type="button" class="btn btn-primary btn-flat disabled-by-default"><i class="fa-solid fa-copy"></i></button>
									</span>
								</div>
							</div>
							
							<#-- Share to email -->
							<div class="form-group row">
								<label for="twigLink" class="col-sm-3 col-form-label">Email</label>
								<div class="col-sm-9 input-group">
  									<div class="input-group-prepend">
    									<span class="input-group-text"><i class="fa-solid fa-envelope"></i></span>
  									</div>
  									<input type="text" id="twig-link" class="form-control disabled-by-default" placeholder="Email address...">
  									<span class="input-group-append">
										<button type="button" class="btn btn-primary btn-flat disabled-by-default"><i class="fa-solid fa-paper-plane"></i></button>
									</span>
								</div>
							</div>
							
							<#-- Share to mobile -->
							<#-- 
							<div class="form-group row">
								<label for="twigLink" class="col-sm-3 col-form-label">Mobile devices</label>
								<div class="col-sm-9 input-group">
  									<div id="qrcode"></div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-3"></div>
								<div class="col-sm-9">
									Scan	 the <span class="bold-text text-primary">QR code</span> on your mobile devices. <br />
								</div>
							</div>
							-->
							
						
					
     			</div>
     			
     			<#-- Apply and reset -->
      			<div class="modal-footer">
     				<div class="btn-group">
      					<button class="btn btn-outline-primary" onclick="applyChanges();" id="applyBtn">
      						<i class="fa-solid fa-check"></i>&nbsp;Apply
      					</button>
      				
      					<button class="btn btn-outline-secondary">
      						<i class="fa-solid fa-rotate"></i>&nbsp;Reset
      					</button>
     				</div>
     			</div>
				<#-- Apply and reset -->
     			
    		</div>
  		</div>
	</div>
	<#-- Settings Modal -->

	<#-- Settings button -->
	<div class="setting-button">
    	<button id="settingsButton" class="btn btn-primary" onclick="$('#etwigSettingBox').modal('show');">
        	<i class="fa fa-gear fa-xl fa-beat-fade" id="cogIcon"></i>
    	</button>
	</div>
	<#-- /Settings button -->
	
	<script>
		$(document).ready(function() {
		
			// Initialize the week
			getWeekByDate(Date.today().toString("yyyy-MM-dd"));

			//applyChanges();
			//enableShare(true);
			
			// Initialize the setting button.
			//$('#settingsButton').click(function() {
			//	$('#etwigSettingBox').modal('show');
			//});
			
			$('#twigPortfolio').select2({
				theme: 'bootstrap4',
			});

			var datepicker = createDatePicker();
			
			//$('#twigResolution').select2({
			//	theme: 'bootstrap4',
			//});
			
		});
		
		//var twigUrl = "/twig/content";
		
		
		
		
		//new QRCode(document.getElementById("qrcode"), "https://etwig.grinecraft.net");
		
		//createQRCode("qrcode",  "https://etwig.grinecraft.net", ["#000000", "#FFFFFF"]);
	</script>

</body>
</html>
