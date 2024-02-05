<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, properties part.
	This part contains the form of timing, including event start/end time and duration.
   -->

   						<#-- Timing -->
						<div class="container-fluid">
							<div class="row col-12">

								<#-- Col 1 -->
								<div class="col-md-6">

									<#-- Recurrent -->
									<div class="form-group row">
										<label for="event-recurrent" class="col-sm-2">
											Recurrent&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="form-group clearfix">
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="single-time-event" name="event-recurrent" checked="" value="0">
													<label for="single-time-event">Single Time</label>
												</div>
												<div class="icheck-primary d-inline mr-2">
													<input type="radio" id="recurring-event" name="event-recurrent" value="1">
													<label for="recurring-event">Recurring</label>
												</div>
											</div>				

											<div class="callout callout-primary">
												<h5 class="bold-text mb-3">Be Careful!</h5>
												If you switch between recurrent options, some timing information may lose and they are not recoverable.
											</div>
												
										</div>
									</div>
									<#-- /Recurrent -->

									<#-- All day event -->
									<div class="form-group row">
										<label for="eventAllDayEvent" class="col-sm-2 col-form-label">
											All day event&nbsp;<span class="required-symbol">*</span>
										</label>
										<div class="col-sm-10">
											<div class="icheck-primary">
  												<input type="checkbox" id="eventAllDayEvent" name="eventAllDayEvent">
              									<label for="eventAllDayEvent">All day event</label>
											</div>
											<small class="form-text text-muted">If this event as lasting the entire day, enable the checkbox.</small>
										</div>
									</div>
									<#-- /All day event -->

								</div>
								<#-- /Col 1 -->

								<#-- Col 2 -->
								<div class="col-md-6">

									<div id="singleTimeEventOptions">
									
										<#-- Start Date -->
										<div class="form-group row">
											<label for="eventStartDate" class="col-sm-3 col-form-label">
												Start Date&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-regular fa-calendar"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="eventStartDate">
												</div>
												<div id="eventStartDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">yyyy-MM-dd format</small>
											</div>
										</div>
										<#-- /Start Date -->

										<#-- Start Time -->
										<div class="form-group row" id="eventStartTimeBlock">
											<label for="eventStartTime" class="col-lg-3 col-form-label">
												Start Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-start"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time" id="eventStartTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock</small>
											</div>
										</div>
										<#-- /Start Time -->

										<#-- End Date -->
										<div class="form-group row">
											<label for="eventEndDate" class="col-lg-3 col-form-label">
												End Date&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-end"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="eventEndDate">
												</div>
												<div id="eventEndDateWrapper" class="datepicker"></div>
												<small class="form-text text-muted">yyyy-MM-dd format</small>
											</div>
										</div>			
										<#-- /End Date -->

										<#-- End Time -->
										<div class="form-group row" id="eventEndTimeBlock">
											<label for="eventEndTime" class="col-lg-3 col-form-label">
												End Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-end"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time" id="eventEndTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock</small>
											</div>
										</div>			
										<#-- /End Time -->

										<#-- Duration -->
										<div class="form-group row">
											<label for="eventDurationCalculated" class="col-lg-3">Duration</label>
											<div class="col-lg-9">
												<div id="eventDurationCalculated"></div>
											</div>
										</div>			
										<#-- /Duration -->

										<#-- Possible clashes -->
										<!--
										<div class="form-group row">
											<label for="eventPossibleClashes" class="col-lg-3">Possible Clashes</label>
											<div class="col-lg-9">
												<div id="eventPossibleClashes"></div>
												<small class="form-text text-muted">You can add an event anyway regardless of the possible clashes. (Not recommended)</small>
											</div>
										</div>	
										-->		
										<#-- /Possible clashes -->
									</div>

									<div id="recurringEventOptions">

										<#-- Event Time -->
										<div class="form-group row" id="eventRecurringTimeBlock">
											<label for="eventRecurringTime" class="col-lg-3 col-form-label">
												Start Time&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-clock"></i>
														</span>
													</div>
													<input type="text" class="form-control event-time" id="eventRecurringTime" placeholder="__:__">
												</div>
												<small class="form-text text-muted">In a 24-hour clock.</small>
											</div>
										</div>
										<#-- /Event Time -->

										<#-- Duration -->
										<div class="form-group row" id="durationInput" >
											<label for="eventDuration" class="col-lg-3 col-form-label">
												Duration&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-half"></i>
														</span>
													</div>
													<input type="text" class="form-control" id="eventDuration">
												</div>
											</div>
										</div>
										<#-- /Duration -->

										<#-- Frequency-->
										<div class="form-group row">
											<label for="eventFrequency" class="col-lg-3">
												Frequency&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-lg-9">
												<div class="form-group clearfix">
												
													<#-- Daily -->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="daily" name="eventFrequency" checked value="3">
														<label for="daily">Daily</label>
													</div>
													
													<#-- Weekly -->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="weekly" name="eventFrequency" value="2">
														<label for="weekly">Weekly</label>
													</div>
													
													<#-- Monthly-->
													<div class="icheck-primary d-inline mr-2 event-rrule-options">
														<input type="radio" id="monthly" name="eventFrequency" value="1">
														<label for="monthly">Monthly</label>
													</div>
													
												</div>
											</div>
										</div>
										<#-- /Frequency -->

										<#-- Valid from -->
										<div class="form-group row">
											<label for="eventValidFromDate" class="col-sm-3 col-form-label">
												Valid From&nbsp;<span class="required-symbol">*</span>
											</label>
											<div class="col-sm-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-hourglass-start"></i>
														</span>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventValidFromDate">
												</div>
												<small class="form-text text-muted">The date when the recurrence start.</small>
												<div id="eventValidFromDateWrapper" class="datepicker"></div>
											</div>
										</div>
										<#-- /Valid from -->

										<#-- Valid to -->
										<div class="form-group row">
											<label for="eventValidToDate" class="col-lg-3 col-form-label">Valid To</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<div class="icheck-primary">
															<input type="checkbox" id="eventValidToDateEnabled" checked>
															<label for="eventValidToDateEnabled"></label>
														</div>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventValidToDate">
												</div>
												<small class="form-text text-muted">The date when the recurrence end.</small>
												<div id="eventValidToDateWrapper" class="datepicker"></div>
											</div>
										</div>			
										<#-- /Valid to -->

										<#-- Count -->
										<div class="form-group row">
											<label for="eventCount" class="col-lg-3 col-form-label">Count</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-stopwatch"></i>
														</span>
													</div>
													<input type="number" min="2" class="form-control event-rrule-options" id="eventCount">
												</div>
												<small class="form-text text-muted">How many occurrences will be generated. The smallest number allowed is 2.</small>
											</div>
										</div>
										<#-- /Count -->

										<#-- Interval -->
										<div class="form-group row">
											<label for="eventInterval" class="col-lg-3 col-form-label">Interval</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-person-running"></i>
														</span>
													</div>
													<input type="number" min="1" class="form-control event-rrule-options" id="eventInterval">
												</div>
												<small class="form-text text-muted">The interval between each frequency iteration.</small>
											</div>
										</div>
										<#-- /Interval -->

										<#-- By week day -->
										<div class="form-group row">
											<label for="eventByWeekDay" class="col-lg-3 col-form-label">By Week Day</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-week"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventByWeekDay">
														<option value="0">Monday</option>
														<option value="1">Tuesday</option>
														<option value="2">Wednesday</option>
														<option value="3">Thursday</option>
														<option value="4">Friday</option>
														<option value="5">Saturday</option>
														<option value="6">Sunday</option>
													</select>	
												</div>
												<small class="form-text text-muted">The day of week to apply the recurrence to.</small>
											</div>
										</div>
										<#-- /By week day -->

										<#-- By month -->
										<div class="form-group row">
											<label for="eventByMonth" class="col-lg-3 col-form-label">By Month</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-days"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventByMonth">
														<option value="1">January</option>
														<option value="2">February</option>
														<option value="3">March</option>
														<option value="4">April</option>
														<option value="5">May</option>
														<option value="6">June</option>
														<option value="7">July</option>
														<option value="8">August</option>
														<option value="9">September</option>
														<option value="10">October</option>
														<option value="11">November</option>
														<option value="12">December</option>				
													</select>									
												</div>
												<small class="form-text text-muted">The month to apply the recurrence to</small>
											</div>
										</div>
										<#-- /By month -->

										<#-- By Month Day -->
										<div class="form-group row">
											<label for="eventByMonthDay" class="col-lg-3 col-form-label">By Month Day</label>
											<div class="col-lg-9">

												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-days"></i>
														</span>
													</div>
													<input type="text" class="form-control event-rrule-options" id="eventByMonthDay">
												</div>
												<small class="form-text text-muted">The day of month to apply the recurrence to. Use comma to divide each number.</small>
											</div>
										</div>
										<#-- By Month Day -->

										<#-- Excluded dates -->
										<div class="form-group row">
											<label for="eventExcludedDates" class="col-lg-3 col-form-label">Excluded Dates</label>
											<div class="col-lg-9">
												<div class="input-group">
													<div class="input-group-prepend">
														<span class="input-group-text">
															<i class="fa-solid fa-calendar-xmark"></i>
														</span>
													</div>
													<select class="form-control select2bs4 event-rrule-options" multiple="multiple" id="eventExcludedDates">
			
													</select>									
												</div>
												<small class="form-text text-muted">The dates that you want to exclude.</small>
											</div>
										</div>
										<#-- Excluded dates -->

										<#-- Description -->
										<div class="form-group row">
											<label for="eventRRuleDescription" class="col-lg-3">Description</label>
											<div class="col-lg-9" id="eventRRuleDescription"></div>
										</div>			
										<#-- Description -->

										<#-- All dates -->
										<div class="form-group row">
											<label for="eventRRuleAllDates" class="col-lg-3">All dates</label>
											<div class="col-lg-9">
												<span id="eventRRuleAllDatesNum"></span>
												<div class="table-responsive" style="max-height: 300px;">
													<table class="table table-head-fixed text-nowrap table-striped table-hover" id="eventRRuleAllDates">
														<thead>
															<tr>
																<th>Day of week</th>
																<th>Year</th>
																<th>Month</th>
																<th>Day</th>
																<th>Exclude</th>
															</tr>
														</thead>
														<tbody>

														</tbody>
													</table>
												</div>
											</div>
										</div>
										<#-- /All dates -->

									</div>

								</div>
								<#-- /Col 2 -->

							</div>
						</div>
						<#-- /Timing -->
