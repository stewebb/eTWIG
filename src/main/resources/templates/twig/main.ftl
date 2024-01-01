<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The main public TWIG page with filter.
   -->
   
<!DOCTYPE html>
<html>
<head>
	<#include "../_includes/header.ftl">
	
	<#-- Custom CSS for public TWIG. -->
	<link rel="stylesheet" href="/static/css/etwig-twig.css">
	
	<#-- JS for generating QR codes. -->
	<script src="/static/js/qrcode.min.js"></script>
	
	<title>TWIG</title>
</head>

<body style="height: 100%;">

	<#-- Options Modal -->
	<div class="modal fade" tabindex="-1" id="etwigSettingBox">
  		<div class="modal-dialog modal-dialog-scrollable">
    		<div class="modal-content">
    		
      			<div class="modal-header">
        			<h5 class="modal-title bold-text text-primary">
        				<i class="fa-solid fa-gear"></i>&nbsp;Options
        			</h5>
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          				<span aria-hidden="true">&times;</span>
       				</button>

      			</div>
      			
     			<div class="modal-body">
     				<ul class="nav nav-tabs mb-3" role="tablist">
     				
     					<#-- Tab: Filter -->
  						<li class="nav-item" role="presentation">
    						<button class="nav-link active" id="filter-tab" data-toggle="tab" data-target="#filter" type="button" role="tab" aria-controls="filter" aria-selected="true">
    							<i class="fa-solid fa-filter"></i>&nbsp;Filter
    						</button>
  						</li>
  						
  						<#-- Tab: Settings -->
  						<li class="nav-item" role="presentation">
    						<button class="nav-link" id="settings-tab" data-toggle="tab" data-target="#settings" type="button" role="tab" aria-controls="settings" aria-selected="false">
    							<i class="fa-solid fa-screwdriver-wrench"></i>&nbsp;Settings
    						</button>
  						</li>
  						
  						<#-- Tab: Share -->
  						<li class="nav-item" role="presentation">
    						<button class="nav-link" id="share-tab" data-toggle="tab" data-target="#share" type="button" role="tab" aria-controls="share" aria-selected="false">
    							<i class="fa-solid fa-share"></i>&nbsp;Share and Export
    						</button>
  						</li>
					</ul>
		
					<div class="tab-content">
					
						<#-- Content: Filter -->
  						<div class="tab-pane fade show active" id="filter" role="tabpanel" aria-labelledby="filter-tab">
							
							<#-- Select Portfolio -->
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
      									<optgroup label="Portfolio(s) with seperate calendar">
	        								<#if portfolioSeperatedCalendar?has_content>
        										<#list portfolioSeperatedCalendar as portfolio_id, portfolio_info>
        											<option data-color="#${portfolio_info.color}" data-icon="<#if portfolio_info.icon?has_content>${portfolio_info.icon}</#if>" value="${portfolio_id}">
														${portfolio_info.name}
													</option>
												</#list>
											<#else>
												<option value="-2" disabled>(No portfolio)</option>
        									</#if>
        						 		</optgroup>
      								</select>
      							</div>
     						</div>
     						
     						<div class="btn-group">
      				
      						<button class="btn btn-outline-info">
      					<i class="fa-solid fa-share"></i>&nbsp;Share
      				</button>
      				
      				<!--
      				<button class="btn btn-outline-primary" onclick="$('#twigFrame').attr('src', $('#twigFrame').attr('src'));">
      					<i class="fa-solid fa-rotate"></i>&nbsp;Reload
      				</button>
      				-->
      				
      				<button class="btn btn-outline-primary">
      					<i class="fa-solid fa-check"></i>&nbsp;Apply
      				</button>
      				
      				<button class="btn btn-outline-secondary" onclick="$('#etwigSettingBox').modal('hide');">
      					<i class="fa-solid fa-xmark"></i>&nbsp;Close
      				</button>
     			</div>
     			
     			
						</div>
						
						<#-- Content: Settings -->
  						<div class="tab-pane fade" id="settings" role="tabpanel" aria-labelledby="settings-tab">
  				
						</div>
						
						<#-- Content: Share -->
						<div class="tab-pane fade" id="share" role="tabpanel" aria-labelledby="share-tab">
							
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
      										<button class="btn btn-outline-primary">
      											<i class="fa-solid fa-download"></i>&nbsp;Download
      										</button>
      				
      										<button class="btn btn-outline-primary">
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
  									<input type="text" id="twig-link" class="form-control" value="https://etwig.grinecraft.net" disabled>
  									<span class="input-group-append">
										<button type="button" class="btn btn-primary btn-flat"><i class="fa-solid fa-copy"></i></button>
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
  									<input type="text" id="twig-link" class="form-control" placeholder="Email address...">
  									<span class="input-group-append">
										<button type="button" class="btn btn-primary btn-flat"><i class="fa-solid fa-paper-plane"></i></button>
									</span>
								</div>
							</div>
							
							<#-- Share to mobile -->
							<div class="form-group row">
								<label for="twigLink" class="col-sm-3 col-form-label">Mobile devices</label>
								<div class="col-sm-9 input-group">
  									<div id="qrcode"></div>
								</div>
							</div>
							
						</div>
					</div>
					
     			</div>
     			
      			<div class="modal-footer">
      			
     			</div>
    		</div>
  		</div>
	</div>
	
	
	<iframe src="/twig/content" class="fullscreen-iframe" id="twigFrame"></iframe>
	
	<#-- The floating settings button -->
	<div class="setting-button">
    	<button id="settingsButton" class="btn btn-primary">
        	<i class="fa fa-gear fa-xl fa-beat-fade" id="cogIcon"></i>
    	</button>
	</div>

<script>
    $(document).ready(function() {
        $('#settingsButton').click(function() {
            $('#etwigSettingBox').modal('show');
        });
    });
    
    $('#twigPortfolio').select2({
    	theme: 'bootstrap4',
    	templateResult: formatState,
  		templateSelection: formatState,
	});
	
	new QRCode(document.getElementById("qrcode"), "https://etwig.grinecraft.net");
</script>
</body>
</html>