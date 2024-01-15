<#-- 
	eTWIG - The event management software for Griffin Hall.
	copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	license: MIT
	author: Steven Webb [xiaoancloud@outlook.com]
	website: https://etwig.grinecraft.net
	function: The template for add/edit events, footer part
	This part contains the submit buttons.
   -->
   
   				<#-- Submit Options -->
				<div class="card">
					<div class="card-body">
						<div class="btn-group" role="group" style="float: right;">
						
						<#-- Add -->
							
							<#if !disabled>
								<button type="button" class="btn btn-outline-primary" onclick="addEvent(${isEmbeddedStr}, ${isEditStr});">
									<i class="fa-regular fa-check"></i>&nbsp; ${modeStr} event
								</button>
							</#if>
							
						<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<#assign cancelBtn = embedded ? then("Close", "Cancel")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;${cancelBtn}
							</button>
						</div>
						
					</div>
				</div>