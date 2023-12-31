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
	<title>TWIG</title>
</head>

<body style="height: 100%;">

	<#-- Filter Modal -->
	<div class="modal fade" tabindex="-1" id="etwigSettingBox">
  		<div class="modal-dialog modal-dialog-centered">
    		<div class="modal-content">
    		
      			<div class="modal-header">
        			<h5 class="modal-title bold-text text-primary">
        				<i class="fa-solid fa-filter"></i>&nbsp;Filter and Settings
        			</h5>
      			</div>
      			
     			<div class="modal-body">
     			
					<#-- Select Portfolio -->
					<div class="form-group row">
						<label for="eventPortfolio" class="col-sm-2 col-form-label">Portfolio</label>
						<div class="col-sm-10 input-group">
									
							<div class="input-group-prepend">
								<span class="input-group-text">
									<i class="fa-solid fa-briefcase"></i>
								</span>
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
     			</div>
     			
      			<div class="modal-footer">
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
</script>
</body>
</html>