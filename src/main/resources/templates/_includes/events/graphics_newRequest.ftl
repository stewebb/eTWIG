									        <#-- Returning Date -->
											<div class="form-group">
												<label for="returningDate">
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