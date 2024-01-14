<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->

	<#-- Check the checkbox if enabled, or disable all other options -->
	<#assign backgroundEnabledStr = design.background.enabled?string("checked", "")>		
	<#assign backgroundDisabledStr = design.background.enabled?string("", "disabled")>		
	
	<#-- Background mode -->
	<#assign isBackgroundModeColor = design.background.mode == "color">		
	
	<#-- The background color in the input. (White for "image" mode) -->
	<#if isBackgroundModeColor>
		<#assign backgroundColor = design.background.value>
	<#else>
		<#assign backgroundColor = "#FFFFFF">
	</#if>
	
	<#--  --assign modeImageStr = design.background.mode == "image" ?string("checked", "")>		
	
						<#-- Background -->
						<#-- Enabled -->
						<div class="form-group row">
							<label for="templateBackgroundEnabled" class="col-sm-2 col-form-label">Enabled</label>
							<div class="col-sm-10">
								<div class="icheck-primary mb-2">
  									<input type="checkbox" id="templateBackgroundEnabled" name="templateBackgroundEnabled" ${backgroundEnabledStr}>
              						<label for="templateBackgroundEnabled">Enabled</label>
								</div>
							</div>
						</div>
						<#-- /Enabled -->
								
						<#-- Mode -->
						<div class="form-group row">
						<label for="templateMode" class="col-sm-2 col-form-label">Mode</label>
						<div class="col-sm-10">
									
							<#-- Solid color -->
							<div class="icheck-primary">
								<input type="radio" id="backgroundModeSolid" name="backgroundMode" <#if isBackgroundModeColor>checked</#if> ${backgroundDisabledStr}>
								<label for="backgroundModeSolid">Solid color</label>
							</div>
										
							<#-- Image -->
							<div class="icheck-primary">
								<input type="radio" id="backgroundModeImage" name="backgroundMode"<#if !isBackgroundModeColor>checked</#if> ${backgroundDisabledStr}>
								<label for="backgroundModeImage">Image</label>
							</div>					
						</div>
					</div>
					<#-- /Mode -->	
					
					<#-- Color -->
					<div class="form-group row" <#if !isBackgroundModeColor>style="display:none;"</#if>>
						<label for="templateBackgroundColor" class="col-sm-2 col-form-label">Color</label>
						<div class="col-sm-10">
							<div class="input-group my-colorpicker2">
								<div class="input-group-prepend">
									<span class="input-group-text">
										<i class="fa-solid fa-palette" style="color:${backgroundColor}"></i>
									</span>
								</div>
								<input type="text" class="form-control" placeholder="Color" id="templateBackgroundColor" maxlength="31" value="${backgroundColor}" ${backgroundDisabledStr}>
							</div>
						</div>
					</div>
					<#-- /Color -->		
					
					<#-- Image -->
					<div class="form-group row" <#if isBackgroundModeColor>style="display:none;"</#if>>
						<label for="templateName" class="col-sm-2 col-form-label">Image</label>
						<div class="col-sm-10">
							<div class="mb-3">
								<button type="button" class="btn btn-outline-secondary" ${backgroundDisabledStr}>
									<i class="fa-regular fa-image"></i>&nbsp;Select/Upload Image
								</button>			
							</div>
							<img src="/twig/assets?assetId=${design.background.value}" class="img-fluid"></img>	
						</div>
					</div>
					<#-- /Image -->	