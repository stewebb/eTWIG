<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, properties part.
	This part contains the form of timing, including event start/end time and duration.
   -->

   						<#-- Timing: Single Time Event -->
						<div class="card card-primary card-outline">
							<div class="card-header">
								<h3 class="card-title">
									<i class="fa-solid fa-clock"></i>&nbsp;Timing: Single Time Event
								</h3>
							</div>

							<div class="card-body">
								
								<#-- Time Unit-->
								<div class="form-group row">
									<label for="eventTimeUnit" class="col-sm-2 col-form-label">
										Time Unit&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="form-group clearfix">
										
											<#-- Hour-->
											<div class="icheck-primary">
												<input type="radio" id="hour" name="eventTimeUnit" <#if timeUnit == "h">checked</#if> value="h" ${disabledStr}>
												<label for="hour">Hour</label>
											</div>
											
											<#-- Day-->
											<div class="icheck-primary">
												<input type="radio" id="day" name="eventTimeUnit" <#if timeUnit == "d">checked</#if> value="d" ${disabledStr}>
												<label for="day">Day [00:00-23:59]</label>
											</div>
											
											<#-- Week-->
											<div class="icheck-primary">
												<input type="radio" id="week" name="eventTimeUnit" <#if timeUnit == "w">checked</#if> value="w" ${disabledStr}>
												<label for="week">Week [00:00 Mon-23:59 Sun]</label>
											</div>
											
											<#-- Month-->
											<div class="icheck-primary">
												<input type="radio" id="month" name="eventTimeUnit" <#if timeUnit == "m">checked</#if> value="m" ${disabledStr}>
												<label for="month">Month [00:00 1st day-23:59 last day]</label>
											</div>
											
											<#-- Customize-->
											<div class="icheck-primary">
												<input type="radio" id="customize" name="eventTimeUnit" <#if timeUnit == "c">checked</#if> value="c" ${disabledStr}>
												<label for="customize">Custom</label>
											</div>
										</div>
									</div>
								</div>
								
								<#-- Start Time -->
								<div class="form-group row">
									<label for="eventStartTime" class="col-sm-2 col-form-label">
										Start Time&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-start"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event Start Time" id="eventStartTime" ${disabledStr}>
										</div>
										<div id="eventStartWrapper" class="datepicker"></div>
									</div>
								</div>					
								
								<#-- Duration -->
								<div class="form-group row" id="durationInput">
									<label for="eventDuration" class="col-sm-2 col-form-label">
										Duration&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-half"></i>
												</span>
											</div>
											<input type="number" min="0" class="form-control" placeholder="Event Duration" id="eventDuration" value="${duration}" ${disabledStr}>
											<div class="input-group-append">
												<span class="input-group-text" id="unitText">Hour(s)</span>
											</div>
										</div>
									</div>
								</div>
								
								<#-- End Time -->
								<div class="form-group row" id="endTimeInput" style="display:none;">
									<label for="eventEndTime" class="col-sm-2 col-form-label">
										End Time&nbsp;<span class="required-symbol">*</span>
									</label>
									<div class="col-sm-10">
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text">
													<i class="fa-solid fa-hourglass-end"></i>
												</span>
											</div>
											<input type="text" class="form-control" placeholder="Event End Time" id="eventEndTime" <#if isEdit && !editPermission>disabled</#if>>
										</div>
										<div id="eventEndWrapper" class="datepicker"></div>
									</div>
								</div>			
								
								<#-- Calculated -->
								<#-- 
								<div class="form-group row">
									<label for="eventCalculatedTime" class="col-sm-2 col-form-label">Calculated Times</label>
									<div class="col-sm-10 table-responsive">				
										<table class="table table-bordered">
  											<thead>
    											<tr>
      												<th scope="col">Start</th>
      												<th scope="col">Duration</th>
      												<th scope="col">End</th>
    											</tr>
  											</thead>
  											<tbody>
												<tr>
      												<td>Null</td>
      												<td>Null</td>
      												<td>Null</td>
    											</tr>
  											</tbody>
										</table>
										
									</div>
								</div>		
								-->
							</div>
						</div>