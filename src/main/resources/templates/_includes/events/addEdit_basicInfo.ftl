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
									<input type="hidden" id="isEdit" value="0" />

									<#-- EventId -->
									<div class="form-group row" id="eventIdBlock" style="display:none">
										<label for="EventId" class="col-lg-3">eventId</label>
										<div class="col-lg-9" id="eventId"></div>
									</div>
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
											<small class="form-text text-muted">Up to 63 characters.</small>
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
											<small class="form-text text-muted">Up to 63 characters.</small>
										</div>
									</div>
									<#-- /Location -->

									<#-- Organizer Role -->
									<div class="form-group row">
										<label for="eventRole" class="col-lg-3 col-form-label">
											Organizer Role&nbsp;<span class="required-symbol">*</span>
											</label>
										<div class="col-lg-9">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-user-tie"></i>
													</span>
												</div>
												
												<select class="form-control select2bs4" name="eventRole" id="eventRole"></select>
											</div>
											<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
										</div>
									</div>
									<#-- Organizer Role -->

									<#-- Created Time -->
									<div class="form-group row" id="eventCreatedTimeBlock" style="display:none;">
										<label for="eventCreatedTime" class="col-lg-3">Created Time</label>
										<div class="col-lg-9" id="eventCreatedTime"></div>
									</div>
									<#-- /Created Time -->

									<#-- Updated Time -->
									<div class="form-group row" id="eventUpdatedTimeBlock" style="display:none;">
										<label for="eventUpdatedTime" class="col-lg-3">Updated Time</label>
										<div class="col-lg-9" id="eventUpdatedTime"></div>
									</div>
									<#-- /Updated Time -->

								</div>
								<#-- /Col 1 -->

								<#-- Col 2 -->
								<div class="col-md-6">

									<#-- Description -->
									<div class="form-group">
										<label for="eventDescription">Description</label>
										<div id="eventDescription"></div>
										<small class="form-text text-muted">Up to 65,535 characters.</small>
									</div>
									<#-- /Description -->

								</div>
								<#-- /Col 2 -->
							</div>
						</div>
						<#-- /Basic Information -->