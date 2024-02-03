									        <#-- Requester Role -->
											<div class="form-group">
												<label for="requesterRole">
													Role&nbsp;<span class="required-symbol">*</span>
												</label>
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-user-tie"></i>
														</span>
													</div>
														
													<select class="form-control" name="requesterRole" id="requesterRole"></select>
												</div>
												<small class="form-text text-muted">The position and associated portfolio, divided by comma.</small>
											</div>
											<#-- Requester Role -->

											<#-- Returning Date -->
											<div class="form-group">
												<label for="eventGraphicsDate">
													Returning date&nbsp;<span class="required-symbol">*</span>
												</label>
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-clock"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="eventGraphicsDate" ${disabledStr}>
												</div>
												<div id="eventGraphicsDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">Returning date for getting your graphic.</small>
											</div>			
											<#-- Returning Date -->
											
											<#-- Comment -->
											<div class="form-group">
												<label for="comment">Additional Comments</label>										
												<textarea id="requestComment" class="form-control fixed-textarea" maxlength="255" rows="5" ${disabledStr}></textarea>
												<small class="form-text text-muted">Additional comments and requirements, up to 255 characters.</small>
											</div>			
											<#-- /Comment -->

											<script>
												var myPositions = getMyPositions();
												for (var key in myPositions) {
													$("#requesterRole").append('<option value="' + myPositions[key].userRoleId + '">' + myPositions[key].position + ', ' + myPositions[key].portfolioName + '</option>');
												}

												$('#requesterRole').select2({
      												theme: 'bootstrap4'
    											})

											</script>