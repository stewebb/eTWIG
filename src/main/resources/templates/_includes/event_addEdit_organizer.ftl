						<#-- Organizer -->
						<div class="card card-primary">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-user-tie"></i>&nbsp;Organizer
								</h3>
							</div>

							<div class="card-body">						
								
								<#-- Portfolio -->
								<div class="form-group row">
									<label for="event-poerfolio" class="col-sm-2 col-form-label">Portfolio</label>
									<div class="col-sm-10 input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-briefcase"></i>
											</span>
										</div>
      									<select class="form-control select2" name="event-portfolio" id="event-portfolio">
        									<option value="-1" selected>Please Select...</option>
        									<optgroup label="My Portfolio(s)">
        									
        									<#if myPortfolios?has_content>
        										<#list myPortfolios as portfolio_id, portfolio_info>
													<option data-color="#${portfolio_info.color}" data-icon="<#if portfolio_info.icon?has_content>${portfolio_info.icon}</#if>" value="${portfolio_id}">
														${portfolio_info.name}
													</option>
												</#list>
        									</#if>
        									</optgroup>
      									</select>
									</div>
								</div>
								
								<#-- Organizer -->
								<div class="form-group row">
									<label for="event-organizer" class="col-sm-2 col-form-label">Name</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-user"></i>
												</span>
											</div>
											
											<select class="form-control select2" name="event-organizer" id="event-organizer">
												<option value="-1" selected>Please Select...</option>
        										<optgroup label="Myself">
          											 <option value="${user.userId}">${user.username}</option>
        										</optgroup>
       
      										</select>
										</div>
									</div>
								</div>
								
								<#-- Role -->
								<div class="form-group row">
									<label for="event-organizer-role" class="col-sm-2 col-form-label">Role</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-clipboard-user"></i>
												</span>
											</div>
											
											<select class="form-control select2" name="event-organizer-role" id="event-organizer-role" disabled>
        										
      										</select>
										</div>
									</div>
								</div>
								
							</div>
						</div>