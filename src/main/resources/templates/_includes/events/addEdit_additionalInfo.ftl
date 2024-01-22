<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, properties part.
	This part contains the form of custom properties.
   -->
   
						<#-- Additional Information -->
						<div class="container-fluid">
							<div class="row col-12">

								<#-- Col 1 -->
								<div class="col-md-6">

									<#-- Additional Properties -->
									<div style="text-align:left;">
										<h5 class="mb-3 bold-text text-primary">
											<i class="fa-solid fa-ellipsis"></i>&nbsp;Additional Properties
										</h5>

										<#-- Iterate all properties -->
										<#if allProperties?has_content>
											<#list allProperties as property_id, property_info>
										
												<#-- Set the default value of the icon if it's empty in the DB. -->
												<#if property_info.icon?has_content>
													<#assign property_icon = property_info.icon>
												<#else>
													<#assign property_icon = "list-check">
												</#if>
												<#-- /Set. -->
											
												<#-- Convert the propertyId to String, as Freemarker doesn't accept numeric key when accessing to a map. -->
												<#assign string_id = property_id?string>
												<#-- /Convert. -->

												<#-- Mandatory field check. -->
												<#if property_info.mandatory>
													<#assign mandatoryStr = "true">
													<#assign mandatorySymbol = "<span class='required-symbol'>*</span>">
												<#else>
													<#assign mandatoryStr = "false">
													<#assign mandatorySymbol = "">
												</#if>
												<#-- /Mandatory field check. -->

												<div class="form-group row">

													<#-- Property name -->
													<label for="property-${property_id}" class="col-lg-4 col-form-label">
														${property_info.name}&nbsp;${mandatorySymbol}
													</label>
													<#-- /Property name -->

													<div class="col-lg-8 input-group">
										
														<div class="input-group-prepend">
															<span class="input-group-text">
																<i class="fa-solid fa-${property_icon}"></i>
															</span>
														</div>
											
														<#-- Each property has a select box. -->
														<select class="form-control select2bs4 property-select-box" name="property-${property_id}" data-property-name="${property_info.name}" data-mandatory=${mandatoryStr}>
															<option value="-1">(Not selected)</option>
									
															<#-- Get all options of a property -->
															<#if allOptions[string_id]?has_content>
																<#list allOptions[string_id] as opt>
																	<option value="${opt.id}">${opt.name}</option>
																</#list>
															</#if>
															<#-- /Get all options of a property -->
															
														</select>
														<#-- /Each property has a select box. -->
														
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
										<#-- /Or. -->

									</div>
									<#-- /Additional Properties -->

								</div>
							
						
								<#-- Col 2 -->
								<div class="col-md-6" style="text-align:left;">

									<#-- Graphics Request --> 
									<div>
										<h5 class="mb-3 bold-text text-primary">
											<i class="fa-solid fa-hand"></i>&nbsp;Graphics Request
										</h5>

										<#-- Request a grphic now -->										
										<div class="mb-3">
											<div class="icheck-primary">
												<input type="checkbox" id="eventRequestNow" name="eventRequestNow">
												<label for="eventRequestNow">Request a graphics now (Optional)</label>
											</div>
											<small class="form-text text-muted">You can also request the graphics later by clicking the "graphics" tab.</small>
										</div>
										<#-- /Request a grphic now -->

										<#assign editPermission = true>
										<#assign disabledStr = "">
										<#include "./graphics_newRequest.ftl">
									</div>
									<#-- Graphics Request --> 

								</div>
								<#-- /Col 2 -->
								
							</div>
						</div>
						<#-- /Additional Information -->
