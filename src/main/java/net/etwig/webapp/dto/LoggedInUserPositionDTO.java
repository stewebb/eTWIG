/**
 * Provides the classes necessary for user role data transfer within the web application.
 * This package includes data transfer objects (DTOs) to handle user roles operations such as
 * querying and modifying user roles in a web-based context.
 *
 * @since 3.3
 */

package net.etwig.webapp.dto;

import lombok.*;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.util.MapUtils;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Data Transfer Object representing the roles associated with a logged-in user.
 * This class encapsulates the roles available to a user and the currently active role,
 * allowing for operations such as changing the active role based on a valid role list.
 *
 * @author Steven Webb
 * @version 3.3
 */

@Getter
@ToString
public class LoggedInUserPositionDTO {

    /**
     * Stores the roles available to the user with role IDs as keys and role descriptions as values.
     */

    private final HashMap<Long, String> myRoles;

    /**
     * Stores the ID of the current active role of the user.
     */

    private Long myCurrentRole;


    public LoggedInUserPositionDTO(Set<UserRole> userRoles){
        this.myRoles = userRoles.stream()
                .collect(Collectors.toMap(
                        UserRole::getId,
                        UserRole::getPosition,
                        (existingValue, newValue) -> existingValue,
                        HashMap::new));

        this.myCurrentRole = MapUtils.getSmallestKey(this.myRoles);
    }

    /**
     * Attempts to change the user's current active role to a new role.
     * The new role will be set as the current role only if it is present in the available roles.
     *
     * @param newRole The role ID to which the current role should be changed.
     * @return {@code true} if the role change is successful (i.e., the new role exists in the role list),
     *         {@code false} otherwise.
     */

    public boolean changeMyCurrentRole(Long newRole){
        if(myRoles.containsKey(newRole)){
            myCurrentRole = newRole;
            return true;
        } else{
            return false;
        }
    }
}
