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
								<#if allProperties?has_content>
        							<#list allProperties as property_id, property_info>
										
										<div class="form-group row">
											<label for="property-${property_id}" class="col-sm-6 col-form-label">
												${property_info.name}
											</label>
											<div class="col-sm-6 input-group">
									
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-briefcase"></i>
													</span>
												</div>
										
      											<select class="form-control select2 common-select-box" name="property-${property_id}" ${disabledStr}>
      										
     
      											</select>
											</div>
										</div>
										
										
										
									</#list>
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