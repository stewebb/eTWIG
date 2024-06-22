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
import net.etwig.webapp.util.MapUtils;

import java.util.*;
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

        public Position(UserRole userRole){
            this.userRoleId = userRole.getId();
            this.position = userRole.getPosition();
            this.portfolioName = userRole.getPortfolio().getName();
            this.portfolioColor = userRole.getPortfolio().getColor();
        }

    }
}
