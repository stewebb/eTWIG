<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
   
	<#assign logoEnabledStr = design.logo.enabled?string("checked", "")>		
	<#assign logoDisabledStr = design.logo.enabled?string("", "disabled")>		
				
						<#-- Logo -->
						<#-- Enabled -->
						<div class="form-group row">
							<label for="templateLogoEnabled" class="col-sm-2 col-form-label">Enabled</label>
							<div class="col-sm-10">
								<div class="icheck-primary mb-2">
  									<input type="checkbox" id="templateLogoEnabled" name="templateLogoEnabled" ${logoEnabledStr}>
              						<label for="templateLogoEnabled">Enabled</label>
								</div>
							</div>
						</div>
								
						<#-- Image -->
						<div class="form-group row">
							<label for="templateLogoImage" class="col-sm-2 col-form-label">Image</label>
							<div class="col-sm-10">
								
								<#-- Control Button -->
								<div class="mb-3">
									<button type="button" class="btn btn-outline-secondary" ${logoDisabledStr}>
										<i class="fa-regular fa-image"></i>&nbsp;Select/Upload Image
									</button>			
								</div>
								<#-- /Control Button -->
								
								<img src="/twig/assets?assetId=${design.logo.image}" class="img-fluid"></img>										
							</div>
						</div>
						<#-- /Image -->			
								
						<#-- Size -->
						<div class="form-group row">
							<label for="templateLogoSize" class="col-sm-2 col-form-label">Size</label>
							<div class="col-sm-10">
							
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-up-right-and-down-left-from-center"></i>
										</span>
									</div>
									
									<input type="number" class="form-control" placeholder="Template logo size, the percentage of the short side of the TWIG. Allowed number: 5-20" id="templateLogoSize" value="${design.logo.size}" ${logoDisabledStr}>
									<div class="input-group-append">
    									<span class="input-group-text">%</span>
  									</div>
								</div>
							</div>
						</div>
						<#-- /Size -->	
						
						<#-- Position -->
						<div class="form-group row">
							<label for="templateLogoPosition" class="col-sm-2 col-form-label">Position</label>
							<div class="col-sm-10">
								<div class="input-group">
								
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-arrows-up-down-left-right"></i>
										</span>
									</div>
											
									<input type="text" id= "templateLogoPosition" class="form-control" value="${design.logo.position}" data-mask ${logoDisabledStr}>		
									<div class="input-group-append">
    									<span class="input-group-text">%</span>
  									</div>
								</div>
							</div>
						</div>
						<#-- /Position -->
							