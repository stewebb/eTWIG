                                    <#-- Event Information -->
									<div class="mb-2">
										<h5 class="mb-2 bold-text text-primary">
											<i class="fa-solid fa-circle-info ="></i>&nbsp;Event Information
										</h5>

										<#-- Basic Info -->
										<table class="table table-bordered">
													
											<#-- Event Id -->
											<tr>
												<th scope="row">Event Id</th>
												<td>${eventInfo.id}</td>
											</tr>
											<#-- /Event Id -->
													
											<#-- Name -->
											<tr>
												<th scope="row">Name</th>
												<td>${eventInfo.name}</td>
											</tr>
											<#-- /Name -->
													
											<#-- Location -->
											<tr>
												<th scope="row">Location</th>
												<td><#if eventInfo.location?has_content>${eventInfo.location}</#if></td>
											</tr>
											<#--  /Location -->
													
											<#-- Type -->
											<tr>
												<th scope="row">Type</th>
												<td>${(eventInfo.recurrent)?string("Recurring","Single Time")} Event</td>
											</tr>
											<#-- /Type -->
													
											<#-- Start Time -->
											<tr>
												<th scope="row">Start Time</th>
												<td>${eventInfo.startTime}</td>
											</tr>
											<#-- /Start Time -->
													
											<#--  Organizer -->
											<tr>
												<th scope="row">Organizer</th>
												<td>${eventInfo.organizer.fullName}</td>
											</tr>
											<#--  /Organizer -->

											<#-- Position and Portfolio -->
											<tr>
												<th scope="row">Position and Portfolio</th>
												<td id="eventPortfolio" style="background-color:#${eventInfo.portfolio.color}">
													${eventInfo.organizerPosition}, ${eventInfo.portfolio.name}
												</td>
											</tr>
											<#-- /Position and Portfolio -->
													
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