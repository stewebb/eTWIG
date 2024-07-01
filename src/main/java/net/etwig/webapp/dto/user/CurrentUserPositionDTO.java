/**
 * Provides the classes necessary for user role data transfer within the web application.
 * This package includes data transfer objects (DTOs) to handle user roles operations such as
 * querying and modifying user roles in a web-based context.
 *
 * @since 3.3
 */

package net.etwig.webapp.dto.user;

import lombok.*;
import net.etwig.webapp.model.UserRole;

import java.util.*;

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
public class CurrentUserPositionDTO {

    private final HashSet<Position> myPositions;
    private Position myCurrentPosition;

    public CurrentUserPositionDTO(Set<UserRole> userRoles){

        // Re-format my positions.
        myPositions = new HashSet<>();
        for(UserRole userRole : userRoles) {
            this.myPositions.add(new Position(userRole));
        }

        // The default current position is the userRole with the smallest ID.
        Optional<Position> current = this.myPositions.stream().min(Comparator.comparingLong(Position::getUserRoleId));
        this.myCurrentPosition = current.get();
    }

    /**
     * Attempts to change the current user's position to a new position identified by the specified user role ID.
     * This method checks if the specified user role ID corresponds to any of the positions currently held by the user.
     * <p>
     * Process:
     * 1. Iterates through the list of positions held by the user ({@code myPositions}).
     * 2. Compares each position's user role ID with the provided {@code userRoleId}.
     * 3. If a match is found, updates the current position ({@code myCurrentPosition}) to the matched position,
     *    and returns true, indicating the position has been successfully updated.
     * 4. If no match is found after checking all positions, returns false, indicating that the user does not hold
     *    a position corresponding to the provided role ID.
     *
     * @param userRoleId the ID of the user role to which the user's position should be changed
     * @return true if the position was successfully changed; false otherwise
     */

    public boolean changeMyPosition(Long userRoleId){

        // Check if the switched new position is value.
        for(Position position : myPositions){
            if(Objects.equals(userRoleId, position.getUserRoleId())){
                this.myCurrentPosition = position;
                return true;
            }
        }

        return false;
    }

    public Long getMyCurrentPositionId(){
        return this.myCurrentPosition.getUserRoleId();
    }

    @Getter
    @ToString
    public class Position {

        private final Long userRoleId;
        private final String position;
        private final String portfolioName;
        private final String portfolioColor;
        private final String portfolioEmail;

        public Position(UserRole userRole){
            this.userRoleId = userRole.getId();
            this.position = userRole.getPosition();
            this.portfolioName = userRole.getPortfolio().getName();
            this.portfolioColor = userRole.getPortfolio().getColor();
            this.portfolioEmail = userRole.getEmail();
        }
    }
}
