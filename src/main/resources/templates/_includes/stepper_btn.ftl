										
										<#-- Previous and Next button -->
										<div class="button-container">
                							
                							<#-- Previous (Left) -->
                	                		<button class="btn btn-outline-primary <#if !prev>hidden-btn</#if>" onclick="twigTemplateStepper.previous()">
                								<i class="fa-solid fa-backward"></i>&nbsp;Previous
                							</button>
                							
                							<#-- /Previous (Left) -->
                							
                							<#-- Preview (Middle) -->
                							<button class="btn btn-outline-secondary" onclick="getCurrentDesign();" id="previewTpl">
                								<i class="fa-solid fa-eye"></i>&nbsp;Preview
                							</button>
                							<#-- /Preview (Middle) -->
                							
                							<#-- Next or Submit (Right) -->
                							<#if next>
                								<button class="btn btn-outline-primary" onclick="twigTemplateStepper.next();">
                									<i class="fa-solid fa-forward"></i>&nbsp;Next
                								</button>
                							<#else>
                								<button type="submit" class="btn btn-outline-primary right-div">
                									<i class="fa-solid fa-check"></i>&nbsp;Submit
                								</button>
                							</#if>
                							<#-- /Next or Submit (Right) -->
                							
                						</div>