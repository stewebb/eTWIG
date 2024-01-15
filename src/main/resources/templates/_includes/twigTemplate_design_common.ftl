<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
   
	<#assign enabledStr = currentElement.enabled?string("checked", "")>		
	<#assign disabledStr =currentElement.enabled?string("", "disabled")>		
				
	<#assign elementNameLower = elementName?lower_case>
	<#assign elementNameCapFirst = elementNameLower?cap_first>
	
						<#-- Enabled -->
						<div class="form-group row">
							<label for="${elementNameLower}Enabled" class="col-sm-2 col-form-label">Enabled</label>
							<div class="col-sm-10">
								<div class="icheck-primary mb-2">
  									<input type="checkbox" id="${elementNameLower}Enabled" name="${elementNameLower}Enabled" onclick="toggleElementsByIdPattern('template${elementNameCapFirst}', '${elementNameLower}Enabled');" ${enabledStr}>
              						<label for="${elementNameLower}Enabled">Enabled</label>
								</div>
							</div>
						</div>
						<#-- /Enabled -->
						
						<#-- Image -->
						<div class="form-group row" id="template${elementNameCapFirst}Image">
							<label for="template${elementNameCapFirst}ImageInput" class="col-sm-2 col-form-label">Image</label>
							<div class="col-sm-10">
							
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-image"></i>
										</span>
									</div>
									
									<input type="text" class="form-control template-image-input" id="template${elementNameCapFirst}ImageInput" value="${currentElement.image}">
									
									<div class="input-group-append">
    									<button type="button" id="template${elementNameCapFirst}ImageBtn" class="btn btn-outline-secondary" onclick="selectUpload('template${elementNameCapFirst}ImageInput', 'template${elementNameCapFirst}ImageContent');" ${disabledStr}>
											<i class="fa-regular fa-upload"></i>&nbsp;Select/Upload
										</button>		
  									</div>
								</div>
								
								<!--
								<img src="/assets/getPublicAsset?assetId=${currentElement.image}" class="img-fluid" id="template${elementNameCapFirst}ImageContent" />	
								-->
							</div>
						</div>
						<#-- /Image -->		
								
						<#-- Size -->
						<div class="form-group row">
							<label for="template${elementNameCapFirst}Size" class="col-sm-2 col-form-label">Size</label>
							<div class="col-sm-10">
							
								<div class="input-group">
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-up-right-and-down-left-from-center"></i>
										</span>
									</div>
									
									<input type="number" class="form-control" placeholder="Template ${elementNameLower} size, the percentage of the short side of the TWIG. Allowed number: 5-20" id="template${elementNameCapFirst}Size" value="${currentElement.size}" ${disabledStr}>
									<div class="input-group-append">
    									<span class="input-group-text">%</span>
  									</div>
								</div>
							</div>
						</div>
						<#-- /Size -->	
						
						<#-- Position -->
						<div class="form-group row">
							<label for="template${elementNameCapFirst}Position" class="col-sm-2 col-form-label">Position</label>
							<div class="col-sm-10">
								<div class="input-group">
								
									<div class="input-group-prepend">
										<span class="input-group-text">
											<i class="fa-solid fa-arrows-up-down-left-right"></i>
										</span>
									</div>
											
									<input type="text" id= "template${elementNameCapFirst}Position" class="form-control" value="(${currentElement.position})" data-mask ${disabledStr}>		
									<div class="input-group-append">
    									<span class="input-group-text">%</span>
  									</div>
								</div>
							</div>
						</div>
						<#-- /Position -->
							