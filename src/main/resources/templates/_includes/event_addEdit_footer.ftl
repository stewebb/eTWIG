<#-- 
	eTWIG - The event and banner management software for residential halls and student unions.
	copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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
							<#assign addOnClickOption = embedded ? then("true", "false")>
							<#assign cancelBtn = embedded ? then("Close", "Cancel")>
							<#if !disabled>
								<button type="button" class="btn btn-outline-primary" onclick="addEvent(${addOnClickOption});">
									<i class="fa-regular fa-check"></i>&nbsp; ${str} event
								</button>
							</#if>
							
						<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;${cancelBtn}
							</button>
						</div>
						
					</div>
				</div>