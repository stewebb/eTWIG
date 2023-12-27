				<#-- Submit Options -->
				<div class="card">
					<div class="card-body">
						<div class="btn-group" role="group" style="float: right;">
						
						<#-- Add -->
							<#assign addOnClickOption = embedded ? then("true", "false")>
							<button type="button" class="btn btn-outline-primary" onclick="addEvent(${addOnClickOption});">
								<i class="fa-regular fa-check"></i>&nbsp;Add event
							</button>

						<#-- Cancel -->
							<#assign cancelOnClickAction = embedded ? then("parent.$('#etwigModal').modal('hide');", "window.location.reload();")>
							<button type="button" class="btn btn-outline-secondary" onclick="${cancelOnClickAction}">
								<i class="fa-solid fa-xmark"></i>&nbsp;Cancel
							</button>
						</div>
						
					</div>
				</div>