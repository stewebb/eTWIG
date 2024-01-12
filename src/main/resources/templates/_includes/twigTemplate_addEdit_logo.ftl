<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
						
						<#-- Logo -->
						<div class="card card-primary card-outline">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-star"></i>&nbsp;Logo
								</h3>
							</div>
							
							<div class="card-body">
							
								<#-- Enabled -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Enabled</label>
									<div class="col-sm-10">
										<div class="icheck-primary mb-2">
  											<input type="checkbox" id="logoEnabled" name="logoEnabled">
              								<label for="logoEnabled">Enabled</label>
										</div>
									</div>
								</div>
								
								<#-- Image -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Image</label>
									<div class="col-sm-10">
																					
									</div>
								</div>
									
								<#-- Perview -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Perview</label>
									<div class="col-sm-10">
																													
									</div>
								</div>
								
								<#-- Size -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Size</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-up-right-and-down-left-from-center"></i>
												</span>
											</div>
											<input type="number" class="form-control" placeholder="Size" id="templateName" maxlength="31" value="">
											<div class="input-group-append">
    											<span class="input-group-text">%</span>
  											</div>
										</div>
									</div>
								</div>
								
								<#-- Position -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Position</label>
										<div class="col-sm-10 form-row align-items-center">
										
											<#-- X Axis -->
                    						<div class="col-sm-5">
                    							<div class="input-group">
                    								<div class="input-group-prepend">
                    									<span class="input-group-text">
                    										<i class="fa-solid fa-arrow-right"></i>
                    									</span>
                    								</div>
                        							<input type="number" class="form-control" id="widthInput" placeholder="X-Axis">
                    								<div class="input-group-append">
    													<span class="input-group-text">%</span>
  													</div>
                    							</div>
                    						</div>
                    						
                    						<#-- Comma -->
                    						<div class="col-auto">
                        						<span class="form-control-plaintext">,</span>
                    						</div>
                    					
                    						<#-- Y Axis -->
                    						<div class="col-sm-5">
                    							<div class="input-group">
                    								<div class="input-group-prepend">
                    									<span class="input-group-text">
                    										<i class="fa-solid fa-arrow-down"></i>
                    									</span>
                    								</div>
                        							<input type="number" class="form-control" id="widthInput" placeholder="Y-Axis">
                    								<div class="input-group-append">
    													<span class="input-group-text">%</span>
  													</div>
                    							</div>
                    						</div>
                    						
                    					</div>
								</div>
									
									
							</div>
						</div>	