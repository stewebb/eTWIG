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


						
								<#-- Col 2 -->
								<div class="col-md-6" style="text-align:left;">

									<#-- Graphics Request --> 
									<div id="eventRequestNowBlock">
										<h5 class="mb-3 bold-text text-primary">
											<i class="fa-solid fa-hand"></i>&nbsp;Graphics Request
										</h5>

										<#-- Request a grphic now -->										
										<div class="mb-3">
											<div class="icheck-primary">
												<input type="checkbox" id="eventRequestNow" name="eventRequestNow" checked="">
												<label for="eventRequestNow">Request a graphics now (Optional)</label>
											</div>
											<small class="form-text text-muted">
												You can also request the graphics later by clicking the "graphics" tab after adding the event.
											</small>
										</div>
										<#-- /Request a grphic now -->

										<#assign editPermission = true>
										<#assign disabledStr = "">
										<#include "./graphics_newRequest.ftl">
									</div>
									<#-- Graphics Request --> 
								</div>
								<#-- /Col 2 -->

								<#-- Col 1 -->
								<div class="col-md-6">

									

								</div>
							
								
							</div>
						</div>
						<#-- /Additional Information -->
