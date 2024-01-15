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
	<#assign backgroundReadOnlyStr = design.background.enabled?string("", "readonly")>	
	
	<#-- Background mode -->
	<#assign isBackgroundModeColor = design.background.mode == "color">		
	
	<#-- The background color in the input. (White for "image" mode) -->
	<#if isBackgroundModeColor>
		<#assign backgroundColor = design.background.value>
		<#assign backgroundImage = 0>
	<#else>
		<#assign backgroundColor = "#FFFFFF">
		<#assign backgroundImage = design.background.value>
	</#if>
	
						<#-- Background -->
						<#-- Enabled -->
						<div class="form-group row">
							<label for="backgroundEnabled" class="col-sm-2 col-form-label">Enabled</label>
							<div class="col-sm-10">
								<div class="icheck-primary mb-2">
  									<input type="checkbox" id="backgroundEnabled" name="templateBackgroundEnabled" onclick="toggleElementsByIdPattern('templateBackground', 'backgroundEnabled');" ${backgroundEnabledStr}>
              						<label for="backgroundEnabled">Enabled</label>
								</div>
							</div>
						</div>
						<#-- /Enabled -->
								
						<#-- Mode -->
						<div class="form-group row">
							<label for="templateBackgroundMode" class="col-sm-2 col-form-label">Mode</label>
							<div class="col-sm-10">
									
								<#-- Color-->
								<div class="icheck-primary">
									<input type="radio" id="templateBackgroundModeColor" name="backgroundMode" <#if isBackgroundModeColor>checked</#if> ${backgroundDisabledStr} onclick="setBackgroundMode(true);" value="color">
									<label for="templateBackgroundModeColor">Solid color</label>
								</div>
								<#-- /Color-->	
								
								<#-- Image -->
								<div class="icheck-primary">
									<input type="radio" id="templateBackgroundModeImage" name="backgroundMode" <#if !isBackgroundModeColor>checked</#if> ${backgroundDisabledStr} onclick="setBackgroundMode(false);" value="image">
									<label for="templateBackgroundModeImage">Image</label>
								</div>		
								<#-- /Image -->			
								
							</div>
						</div>
						<#-- /Mode -->	
					
						<#-- Color -->
						<div class="form-group row" id="templateBackgroundColor" <#if !isBackgroundModeColor>style="display:none;"</#if>>
							<label for="templateBackgroundColorInput" class="col-sm-2 col-form-label">Color</label>
							<div class="col-sm-10">
								<div class="input-group color-picker">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-palette" style="color:${backgroundColor}"></i>
										</span>
									</div>
									<input type="text" class="form-control" placeholder="Color" id="templateBackgroundColorInput" value="${backgroundColor}" ${backgroundReadOnlyStr}>
								</div>
							</div>
						</div>
						<#-- /Color -->		
					
						<#-- Image -->
						<div class="form-group row" id="templateBackgroundImage" <#if isBackgroundModeColor>style="display:none;"</#if>>
							<label for="templateBackgroundImageInput" class="col-sm-2 col-form-label">Image</label>
							<div class="col-sm-10">
							
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-image"></i>
										</span>
									</div>
									
									<input type="text" class="form-control template-image-input" id="templateBackgroundImageInput" value="${design.background.value}" data-image-id="templateBackgroundImageContent" readonly>
									
									<div class="input-group-append">
    									<button type="button" id="templateBackgroundImageBtn" class="btn btn-outline-secondary" onclick="selectUpload('templateBackgroundImageInput', 'templateBackgroundImageContent');" ${backgroundDisabledStr}>
											<i class="fa-regular fa-upload"></i>&nbsp;Select/Upload
										</button>		
  									</div>
								</div>
								<img src="/assets/getPublicAsset?assetId=${backgroundImage}" class="img-fluid" id="templateBackgroundImageContent" />	
							</div>
						</div>
						<#-- /Image -->	