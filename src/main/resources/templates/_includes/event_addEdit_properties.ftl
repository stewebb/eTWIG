<#-- 
	eTWIG - The event management software for university communities.
	copyright: Copyright (c) 2024 Steven Webb
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
										
										<div class="form-group row">
										
											<label for="property-${property_id}" class="col-sm-6 col-form-label">
												${property_info.name}
												<#if property_info.mandatory>
													<span class="required-symbol">*</span>
												</#if>
											</label>
											
											<div class="col-sm-6 input-group">
									
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-${property_icon}"></i>
													</span>
												</div>
										
												<#-- Each property has a select box. -->
      											<select class="form-control select2 common-select-box" name="property-${property_id}" ${disabledStr}>
      												<option value="-1">(Not selected)</option>
     
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