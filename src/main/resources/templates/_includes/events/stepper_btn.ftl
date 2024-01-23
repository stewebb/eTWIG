										
										<#-- Previous and Next button -->
										<div class="button-container">
                							
                							<#-- Previous (Left) -->
                	                		<button class="btn btn-outline-secondary <#if !prev>hidden-btn</#if>" onclick="eventStepper.previous()">
                								<i class="fa-solid fa-backward"></i>&nbsp;Previous
                							</button>
                							<#-- /Previous (Left) -->
                							
                							<#-- Next or Submit (Right) -->
                							<#if next>
                								<button class="btn btn-outline-primary" onclick="eventStepper.next();">
                									<i class="fa-solid fa-forward"></i>&nbsp;Next
                								</button>
                							<#else>
                								<button type="button" class="btn btn-outline-primary right-div" onclick="addEvent();">
                									<i class="fa-solid fa-check"></i>&nbsp;Submit
                								</button>
                							</#if>
                							<#-- /Next or Submit (Right) -->
                							
                						</div>