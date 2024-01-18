package net.grinecraft.etwig.dto;

import java.util.Set;

import lombok.*;
import net.grinecraft.etwig.model.Role;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class UserAccessDTO {
	
	private boolean eventsAccess = false;
	private boolean graphicsAccess = false;
	private boolean adminAccess = false;
	
	public UserAccessDTO(Set<UserRole> userRoles) {
		
		// The permission of each roles accumulates.
		for (UserRole userRole : userRoles) {
			Role role = userRole.getRole();
			
			// Grant all permissions immediately as long as the user is admin.
			if(role.isAdminAccess()) {
				this.eventsAccess = true;
				this.graphicsAccess = true;
				this.adminAccess = true;
				return;
			}
			
			// Grant other permissions one by one
			if(role.isEventsAccess()) {
				this.eventsAccess = true;
			}
			
			if(role.isGraphicsAccess()) {
				this.graphicsAccess = true;
			}
		}
		
		
	}
	
}
