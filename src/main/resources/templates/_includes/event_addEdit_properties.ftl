<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, properties part.
	This part contains the form of custom properties.
   -->
   
   						<#-- Properties -->
						<div class="card card-primary card-outline">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-ellipsis"></i>&nbsp;Properties
								</h3>
							</div>

							<div class="card-body">
							 
								<#-- Iterate all properties -->
								<#if allProperties?has_content>
        							<#list allProperties as property_id, property_info>
        							
        								<#-- Set the default value of the icon if it's empty in the DB. -->
        								<#if property_info.icon?has_content>
											<#assign property_icon = property_info.icon>
										<#else>
											<#assign property_icon = "list-check">
										</#if>
										
										<#-- Convert the propertyId to String, as Freemarker doesn't accept numberic key when accessing to a map. -->
										<#assign string_id = property_id?string>
										
										<#-- Mandatory field check. -->
										<#if property_info.mandatory>
											<#assign mandatoryStr = "true">
											<#assign mandatorySymbol = "<span class='required-symbol'>*</span>">
										<#else>
											<#assign mandatoryStr = "false">
											<#assign mandatorySymbol = "">
										</#if>
										<div class="form-group row">
										
											<label for="property-${property_id}" class="col-sm-6 col-form-label">
												${property_info.name}&nbsp;${mandatorySymbol}
											</label>
											
											<div class="col-sm-6 input-group">
									
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-${property_icon}"></i>
													</span>
												</div>
										
												<#-- Each property has a select box. -->
      											<select class="form-control select2 common-select-box property-select-box" name="property-${property_id}" data-property-name="${property_info.name}" data-mandatory=${mandatoryStr} ${disabledStr}>
      												<option value="-1">(Not selected)</option>
								
													<#-- Get all options of a property -->
													<#if allOptions[string_id]?has_content>
     													<#list allOptions[string_id] as opt>
     													
     														<#-- Get the selected event in edit mode. -->
     														<#if isEdit && selectedOptions?has_content>
																<#assign selectedStr = (selectedOptions?seq_contains(opt.id))?string('selected', '')>
															<#else>
																<#assign selectedStr = ''>
															</#if>
															
															<option value="${opt.id}" ${selectedStr}>${opt.name}</option>
														</#list>
													</#if>
      											</select>
											</div>
										</div>

									</#list>
								
								<#-- Or just tell user there has no properties. -->
								<#else>
									<div class="d-flex justify-content-center">
										<i class="fa-regular fa-face-dizzy big-icons"></i>
									</div>
									
									<div class="d-flex justify-content-center bold-text text-secondary">
										No properties.
									</div>
        						</#if>
        						
							</div>
						</div>