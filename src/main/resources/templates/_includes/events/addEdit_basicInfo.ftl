<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
						
						<#-- Basic Information -->
						<div class="container-fluid">
							<div class="row col-12">

								<#-- Col 1 -->
								<div class="col-md-6">
									<div class="mb-2">&nbsp;</div>

									<#-- EventId -->
									<#--
										<div class="form-group row">
											<label for="eventId" class="col-lg-3 col-form-label">
												Id&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hashtag"></i>
														</span>
													</div>
													<input type="number" class="form-control" id="eventId" disabled>
												</div>
											</div>
										</div>
									-->
									<#-- /EventId -->

									<#-- Name -->
									<div class="form-group row">
										<label for="eventName" class="col-lg-3 col-form-label">
											Name&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-lightbulb"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="eventName" maxlength="63">
											</div>
											<small class="form-text text-muted">Event name (required), up to 63 characters.</small>
										</div>
									</div>
									<#-- /Name -->

									<#-- Location -->
									<div class="form-group row">
										<label for="eventLocation" class="col-lg-3 col-form-label">Location</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-location-dot"></i>
													</span>
												</div>
												<input type="text" class="form-control" id="eventLocation" maxlength="63">
											</div>
											<small class="form-text text-muted">Event location (optional), up to 63 characters.</small>
										</div>
									</div>
									<#-- /Location -->

									<#-- Organzier Role -->
									<div class="form-group row">
										<label for="eventRole" class="col-lg-3 col-form-label">
											Organzier Role&nbsp;<span class="required-symbol">*</span>
											</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												
												<select class="form-control select2" name="eventRole" id="eventRole">
													
												</select>
											</div>
										</div>
									</div>
									<#-- Organzier Role -->

								</div>
								<#-- /Col 1 -->

								<#-- Col 2 -->
								<div class="col-md-6">

									<#-- Description -->
									<div class="form-group">
										<label for="eventDescription">Description</label>
										<div id="eventDescription"></div>
										<small class="form-text text-muted">Event description (optional), up to 65,535 characters.</small>
									</div>
									<#-- /Description -->

								</div>
								<#-- /Col 2 -->
							</div>
						</div>
						<#-- /Basic Information -->