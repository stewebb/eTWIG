<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, basic information part.
	This part contains the form of basic information like name, location, recurrent and description.
   -->
	
	<#if isEdit && template?has_content>
		<#assign startAnytimeCheckedStr = template.availableFrom?has_content?string("", "checked")>
		<#assign availableFromDisabledStr = template.availableFrom?has_content?string("", "disabled")>
		<#assign untilForeverCheckedStr = template.availableTo?has_content?string("", "checked")>
		<#assign availableToDisabledStr = template.availableTo?has_content?string("", "disabled")>
	<#else>
		<#assign startAnytimeCheckedStr = "">
		<#assign untilForeverCheckedStr = "">
		<#assign availableFromDisabledStr = "">
		<#assign availableToDisabledStr = "">
	</#if>

				<#-- Basic Information -->
				<div class="card card-primary card-outline">
					<div class="card-header">
						<h3 class="card-title">
							<i class="fa-solid fa-circle-info"></i>&nbsp;Basic Information
						</h3>
					</div>

					<div class="card-body">
						<div class="row">
							<div class="col-md-6">
								
								<#-- id -->
								<#if isEdit>
									<div class="form-group row">
										<label for="templateId" class="col-sm-2 col-form-label">
											Id&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">
														<i class="fa-solid fa-hashtag"></i>
													</span>
												</div>
												<input type="number" class="form-control" placeholder="templateId" id="templateId" value="${template.id}" disabled>
											</div>
										</div>
									</div>
								</#if>
							
								<#-- Name -->
								<div class="form-group row">
									<label for="templateName" class="col-sm-2 col-form-label">
										Name&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-lightbulb"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Template Name" id="templateName" maxlength="31" value="<#if isEdit>${template.name}</#if>">
										</div>
									</div>
								</div>
								
								<#-- Portfolio -->
								<div class="form-group row">
									<label for="eventPortfolio" class="col-sm-2 col-form-label">
										Portfolio&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10 input-group">
									
										<div class="input-group-prepend">
											<span class="input-group-text">
												<i class="fa-solid fa-briefcase"></i>
											</span>
										</div>
										
      									<select class="form-control select2" name="twigPortfolio" id="twigPortfolio">
      									<optgroup label="All portfolios">
      										<option value="-1" selected>All portfolios</option>
      									</optgroup>
      									<optgroup label="Portfolio(s) with separate calendar">
	        								<#if portfolioSeparatedCalendar?has_content>
        										<#list portfolioSeparatedCalendar as portfolio_id, portfolio_info>
        											<option data-color="#${portfolio_info.color}" data-icon="<#if portfolio_info.icon?has_content>${portfolio_info.icon}</#if>" value="${portfolio_id}">
														${portfolio_info.name}
													</option>
												</#list>
											<#else>
												<option value="-2" disabled>(No portfolio)</option>
        									</#if>
        						 		</optgroup>
      								</select>
      									
									</div>
								</div>
							</div>
							
							<div class="col-md-6">
							
								<#-- Available From -->
								<div class="form-group row" id="endTimeInput">
									<label for="eventEndTime" class="col-sm-3 col-form-label">
										Available From&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-9">
									
										<#-- Start Anytime -->
										<div class="icheck-primary mb-2">
  											<input type="checkbox" id="startAnytime" name="startAnytime" ${startAnytimeCheckedStr}>
              								<label for="startAnytime">Start Anytime</label>
										</div>
									
										<#-- The start date input -->
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-start"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event End Time" id="eventEndTime" ${availableFromDisabledStr}>
										</div>
										<div id="eventEndWrapper" class="datepicker"></div>
									</div>
								</div>

								<#-- Available To -->
								<div class="form-group row" id="endTimeInput">
									<label for="eventEndTime" class="col-sm-3 col-form-label">
										Available To&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-9">
									
										<#-- Until Forever -->
										<div class="icheck-primary mb-2">
  											<input type="checkbox" id="untilForever" name="untilForever" ${untilForeverCheckedStr}>
              								<label for="untilForever">Until Forever</label>
										</div>
									
										<#-- The end date input -->
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-end"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event End Time" id="eventEndTime" ${availableToDisabledStr}>
										</div>
										<div id="eventEndWrapper" class="datepicker"></div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			