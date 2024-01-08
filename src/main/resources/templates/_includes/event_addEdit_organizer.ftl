<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, organizer part.
	This part contains the form of portfolio and organizer information.
   -->
   
   						<#-- Organizer -->
						<div class="card card-primary card-outline">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-user-tie"></i>&nbsp;Organizer
								</h3>
							</div>

							<div class="card-body">						
								
								<#-- Portfolio -->
								<div class="form-group row">
									<label for="eventPortfolio" class="col-sm-2 col-form-label">Portfolio</label>
									<div class="col-sm-10 input-group">
									
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-briefcase"></i>
											</span>
										</div>
										
      									<select class="form-control select2" name="eventPortfolio" id="eventPortfolio" ${disabledStr}>
      										
        									<!-- <optgroup label="My Portfolio(s)"> -->
        									
        									<#if isEdit>
     											<option data-color="#${eventDetails.portfolio.color}" data-icon="<#if eventDetails.portfolio.icon?has_content>${eventDetails.portfolio.icon}</#if>" value="${eventDetails.portfolio.id}">
													${eventDetails.portfolio.name}
												</option>
        									<#else>
        										<#if portfolio?has_content>
        											<#list portfolio as portfolio_id, portfolio_info>
														<option data-color="#${portfolio_info.color}" data-icon="<#if portfolio_info.icon?has_content>${portfolio_info.icon}</#if>" value="${portfolio_id}">
															${portfolio_info.name}
														</option>
													</#list>
        										</#if>
        									</#if>
        									<!-- </optgroup> -->
      									</select>
      									
      									<div id="select-box"></div>
									</div>
								</div>
								
								<#-- Organizer -->
								<div class="form-group row">
									<label for="eventOrganizer" class="col-sm-2 col-form-label">Name</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-user"></i>
												</span>
											</div>
											
											<select class="form-control select2 common-select-box" id ="eventOrganizer" name="eventOrganizer" ${disabledStr}>
        										<!-- <optgroup label="Myself"> -->
        											<#if isEdit>
        												<option value="${eventDetails.user.Id}">${eventDetails.user.fullName}</option>
        											<#else>
          											 	<option value="${user.userId}">${user.username}</option>
          											 </#if>
        										<!-- </optgroup> -->
       
      										</select>
										</div>
									</div>
								</div>
								
								<#-- Role -->
								<#-- 
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
								-->
								
							</div>
						</div>