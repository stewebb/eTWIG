										
										<#-- Previous and Next button -->
										<div class="button-container">
                							
                							<#-- Previous or Cancel (Left) -->
                							<#if prev>
                	                			<button class="btn btn-outline-secondary" onclick="eventStepper.previous()">
                									<i class="fa-solid fa-backward"></i>&nbsp;Previous
                								</button>
                							<#else>
                								<button class="btn btn-outline-secondary" onclick="">
                									<i class="fa-solid fa-xmark"></i>&nbsp;Cancel
                								</button>
                							</#if>
                							<#-- /Previous or Cancel (Left) -->
                							
                							<#-- Next or Submit (Right) -->
                							<#if next>
                								<button class="btn btn-outline-primary" onclick="eventStepper.next();">
                									<i class="fa-solid fa-forward"></i>&nbsp;Next
                								</button>
                							<#else>
                								<button type="submit" class="btn btn-outline-primary right-div">
                									<i class="fa-solid fa-check"></i>&nbsp;Submit
                								</button>
                							</#if>
                							<#-- /Next or Submit (Right) -->
                							
                						</div>