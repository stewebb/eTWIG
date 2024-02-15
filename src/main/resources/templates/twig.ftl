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

	<#-- rrule.js -->
   	<script src="/static/js/etwig/recurrent.min.js"></script>

	<script src="/static/js/etwig/twig.js"></script>
	<script src="/static/js/etwig/twig-draw.js"></script>

	<script src="/static/js/vendor/qrcode.min.js"></script>

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
						<label for="eventPortfolio" class="col-sm-3 col-form-label">Portfolio</label>
						<div class="col-sm-9 input-group">
									
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fa-solid fa-briefcase"></i></span>
							</div>
							
							<select class="form-control select2 select2bs4" name="twigPortfolio" id="twigPortfolio">
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
						<label for="twigWeek" class="col-sm-3 col-form-label">Date</label>
						<div class="col-sm-9">
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
     					<div class="col-sm-3 bold-text">Week</div>
     					<div class="col-sm-9" id="calculatedWeek"></div>
     				</div>
					<#-- /Week -->

					<#-- Apply and reset -->
					<div class="d-flex flex-row-reverse">
						<div class="btn-group">			
							<a href="/twig" class="btn btn-outline-secondary">
								<i class="fa-solid fa-rotate"></i>&nbsp;Reset
							</a>
							<button class="btn btn-outline-primary" onclick="applyChanges();" id="applyBtn">
								<i class="fa-solid fa-check"></i>&nbsp;Apply
							</button>
						</div>
					</div>
					<#-- /Apply and reset -->
						
					<hr class="mb-3"/>
							
					<#-- Export -->
					<div class="form-group row">
						<label for="export" class="col-sm-3 col-form-label">Export</label>
						<div class="col-sm-9">

							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text">
										<i class="fa-solid fa-file-export"></i>
									</span>
								</div>

								<select class="form-control select2bs4" id="imgFormat">
									<option value="png">PNG image</option>
									<option value="jpg">JPG image</option>
								</select>	

								<span class="input-group-append">
									<button type="button" class="btn btn-primary btn-flat" onclick="downloadImg();">
										<i class="fa-solid fa-download"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
					<#-- /Export -->

					<#-- Copy link -->
					<div class="form-group row">
						<label for="twigLink" class="col-sm-3 col-form-label">Copy link</label>
						<div class="col-sm-9 input-group">
  							<div class="input-group-prepend">
    							<span class="input-group-text"><i class="fa-solid fa-link"></i></span>
  							</div>

  							<input type="text" id="twigLink" class="form-control">
  							<span class="input-group-append">
								<button type="button" class="btn btn-primary btn-flat" onclick="copyLink(window.location.href)">
									<i class="fa-solid fa-copy"></i>
								</button>
							</span>
						</div>
					</div>
					<#-- /Copy link -->

						
							
					<#-- QR Code -->
					<div class="form-group row">
						<label for="twigQRCode" class="col-sm-3">QR Code</label>
						<div class="col-sm-9 input-group">
  							<div id="qrcode"></div>
						</div>
					</div>
					<#-- /QR Code -->	
	
					
     			</div>
     			
     			
     			
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
		
			// Initialize week
			getWeekByDate(Date.today().toString("yyyy-MM-dd"));

			// Initialize twig link
			$('#twigLink').val(window.location.href);

			$('.select2bs4').select2({
				theme: 'bootstrap4',
			});
			
			// Create a QR code that points to the current page.
			var qrcode = new QRCode($("#qrcode")[0], {
				text: window.location.href,
				width: 191,
				height: 191,
				colorDark : "#000000",
				colorLight : "#ffffff",
				correctLevel : QRCode.CorrectLevel.H
			});

		});		
		
	</script>

</body>
</html>
