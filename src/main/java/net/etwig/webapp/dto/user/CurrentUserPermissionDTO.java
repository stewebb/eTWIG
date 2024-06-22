package net.etwig.webapp.dto.user;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import net.etwig.webapp.model.Role;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class CurrentUserPermissionDTO {
	
	private boolean eventsAccess = false;
	private boolean graphicsAccess = false;
	private boolean adminAccess = false;
	
	private final Set<Role> myRoles = new HashSet<Role>();
	
	public CurrentUserPermissionDTO(Set<UserRole> userRoles) {
		
		// The permission of each role accumulates.
		for (UserRole userRole : userRoles) {
			Role role = userRole.getRole();
			
			// Add my roles
			myRoles.add(role);
			
			// Grant all permissions if the user is admin.
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
