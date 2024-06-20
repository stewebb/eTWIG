package net.etwig.webapp.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import net.etwig.webapp.model.Role;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class LoggedInUserAccessDTO {
	
	private boolean eventsAccess = false;
	private boolean graphicsAccess = false;
	private boolean adminAccess = false;
	
	private final Set<Role> myRoles = new HashSet<Role>();
	
	public LoggedInUserAccessDTO(Set<UserRole> userRoles) {
		
		// The permission of each roles accumulates.
		for (UserRole userRole : userRoles) {
			Role role = userRole.getRole();
			
			// Add my roles
			myRoles.add(role);
			
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
