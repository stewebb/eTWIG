                                    <#-- Event Information -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
										</h5>

										<#-- Basic Info -->
										<table class="table table-bordered">

											<#-- Basic /-->
											<tr><th scope="row" colspan="2" class="table-title">Basic</th></tr>
													
											<#-- Event Id /-->
											<tr><th scope="row">Event Id</th><td>${eventInfo.id}</td></tr>
													
											<#-- Name /-->
											<tr><th scope="row">Name</th><td>${eventInfo.name}</td></tr>
													
											<#-- Location -->
											<tr>
												<th scope="row">Location</th>
												<td><#if eventInfo.location?has_content>${eventInfo.location}</#if></td>
											</tr>
											<#--  /Location -->

											<#--  Organizer /-->
											<tr><th scope="row">Organizer</th><td>${eventInfo.organizer.fullName}</td></tr>

											<#-- Position and Portfolio -->
											<tr>
												<th scope="row">Position and Portfolio</th>
												<td id="eventPortfolio" style="color:#FFFFFF; background-color:#${eventInfo.portfolio.color}">
													${eventInfo.organizerPosition}, ${eventInfo.portfolio.name}
												</td>
											</tr>
											<#-- /Position and Portfolio -->

											<#-- Timing /-->
											<tr><th scope="row" colspan="2" class="table-title">Timing</th></tr>
													
											<#-- Type -->
											<tr>
												<th scope="row">Type</th>
												<td>${(eventInfo.recurrent)?string("Recurring","Single Time")} Event</td>
											</tr>
											<#-- /Type -->
													
											<#-- Start Time /-->
											<tr id="eventStartTimeRow"><th scope="row">Start Time</th><td id="eventStartTime"></td></tr>

											<#-- Duration /-->
											<tr><th scope="row">Duration</th><td id="eventDuration"></td></tr>

											<#-- End Time /-->
											<tr id="eventStartTimeRow"><th scope="row">End Time</th><td id="eventEndTime"></td></tr>

										</table>
										<#-- /Basic Info -->

										<#-- Description -->
										<blockquote>
											<label>Description</label>
											<#if eventInfo.description?has_content>
												${eventInfo.description}
											<#else>
												<span>No description.</span>	
											</#if>
										</blockquote>
										<#-- /Description -->	

									</div>
									<#-- Event Information -->

									<script>

										// Display duration
										var duration = ${eventInfo.duration};
										$("#eventDuration").text(formatTime(duration));


										var startTime = Date.parse("${eventInfo.startTime}");
										$("#eventStartTime").text(startTime.toString("ddd yyyy-MM-dd HH:mm"));
										$("#eventEndTime").text(startTime.addMinutes(duration).toString("ddd yyyy-MM-dd HH:mm"));

										<#-- Hide the start and end time for recurring events. -->
										<#if eventInfo.recurrent>
										</#if>
									</script>