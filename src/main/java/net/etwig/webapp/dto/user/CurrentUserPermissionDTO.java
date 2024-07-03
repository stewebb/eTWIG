package net.etwig.webapp.dto.user;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import net.etwig.webapp.model.Role;
import net.etwig.webapp.model.UserRole;

/**
 * Data Transfer Object representing the current user's permissions.
 * This class encapsulates the access permissions for various features
 * within the system such as events, graphics, and administrative access.
 * Permissions are determined based on the user's roles.
 *
 * <p>Each user role is checked to aggregate permissions:
 * If the user has an administrative role, all permissions are granted.
 * Otherwise, permissions for events and graphics are set based on specific roles.</p>
 *
 */

@Getter
@ToString
public class CurrentUserPermissionDTO {


	/**
	 * Indicates whether the user has access to event-related features.
	 */

	private boolean eventsAccess = false;

	/**
	 * Indicates whether the user has access to graphic-related features.
	 */

	private boolean graphicsAccess = false;


	/**
	 * Indicates whether the user has administrative access.
	 */

	private boolean adminAccess = false;

	/**
	 * A set of roles associated with the user, determining the permissions.
	 */

	private final Set<Role> myRoles = new HashSet<>();

	/**
	 * Constructs a new CurrentUserPermissionDTO with specific user roles.
	 * Permissions are aggregated based on the roles provided.
	 *
	 * @param userRoles A set of {@link UserRole} objects representing the roles assigned to the user.
	 */

	public CurrentUserPermissionDTO(Set<UserRole> userRoles) {

		// The permission of each role accumulates.
		for (UserRole userRole : userRoles) {
			Role role = userRole.getRole();

			// Add my roles
			myRoles.add(role);

			// Grant all permissions if the user is admin.
			if (role.isAdminAccess()) {
				this.eventsAccess = true;
				this.graphicsAccess = true;
				this.adminAccess = true;
				return;
			}

			// Grant other permissions one by one
			if (role.isEventsAccess()) {
				this.eventsAccess = true;
			}

			if (role.isGraphicsAccess()) {
				this.graphicsAccess = true;
			}
		}
	}
}
