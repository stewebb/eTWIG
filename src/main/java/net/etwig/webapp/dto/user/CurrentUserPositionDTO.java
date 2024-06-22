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

import java.util.HashMap;
import java.util.Map;
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
public class CurrentUserPositionDTO {



    @Getter
    @ToString
    private class Position {

        private Long userRoleId;
        private String position;
        private String portfolioName;
        private String portfolioColor;

        public Position(UserRole userRole){
            this.userRoleId = userRole.getId();
            this.position = userRole.getPosition();
            this.portfolioName = userRole.getPortfolio().getName();
            this.portfolioColor = userRole.getPortfolio().getColor();
        }

    }
}
