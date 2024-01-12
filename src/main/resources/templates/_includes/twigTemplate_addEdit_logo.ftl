<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
						
						<#-- Background -->
						<div class="card card-primary card-outline">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-regular fa-image"></i>&nbsp;Background
								</h3>
							</div>
							
							<div class="card-body">
							
								<#-- Enabled -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Enabled</label>
									<div class="col-sm-10">
										<div class="icheck-primary mb-2">
  											<input type="checkbox" id="backgroundEnabled" name="backgroundEnabled">
              								<label for="backgroundEnabled">Enabled</label>
										</div>
									</div>
								</div>
								
								<#-- Mode -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Mode</label>
									<div class="col-sm-10">
									
										<#-- Solid color -->
										<div class="icheck-primary">
											<input type="radio" id="backgroundModeSolid" name="backgroundMode">
											<label for="backgroundModeSolid">Solid color</label>
										</div>
										
										<#-- Image -->
										<div class="icheck-primary">
											<input type="radio" id="backgroundModeImage" name="backgroundMode">
											<label for="backgroundModeImage">Image</label>
										</div>					
									</div>
								</div>
								
								<#-- Color -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Color</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-palette"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Color" id="templateName" maxlength="31" value="">
										</div>
									</div>
								</div>
								
								<#-- Image -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">Image</label>
									<div class="col-sm-10">
									
										<#-- 
										<div class="input-group mb-2">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-palette"></i>
												</span>
											</div>
											
											 <div class="custom-file">
    											<input type="file" class="custom-file-input" id="customFile">
    											<label class="custom-file-label" for="customFile">Choose file</label>
  											</div>			
  											
  											<div class="input-group-append btn-group">
  											
  												 Download existing 
												<button type="button" class="btn btn-outline-success">
													<i class="fa-solid fa-download"></i>
												</button>	
											
												Remove existing 
												<button type="button" class="btn btn-outline-secondary">
													<i class="fa-solid fa-trash"></i>
												</button>	
											
												Upload new
												<button type="button" class="btn btn-outline-primary">
													<i class="fa-solid fa-upload"></i>
												</button>	
											
											</div>
											
										-->																							
										</div>
									</div>
									
									<#-- Perview -->
									<div class="form-group row">
										<label for="templateName" class="col-sm-2 col-form-label">Perview</label>
										<div class="col-sm-10">
																													
										</div>
									</div>
								</div>
								
							</div>
						</div>	