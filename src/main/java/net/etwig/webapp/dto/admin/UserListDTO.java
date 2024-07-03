package net.etwig.webapp.dto.admin;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@ToString
public class UserListDTO {

    // User information
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime userLastLogin;

    // Associated positions
    private final Set<UserPosition> userPositions = new HashSet<>();

    public void setUser(Long userId, String userName, String userEmail, LocalDateTime userLastLogin) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userLastLogin = userLastLogin;
    }

    // Method to add position to the list
    public void addPosition(
            String positionName,
            String portfolioName,
            String portfolioColor,
            String portfolioEmail
    ) {
        this.userPositions.add(
                new UserPosition(positionName, portfolioName, portfolioColor, portfolioEmail)
        );
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public class UserPosition {

        private final String positionName;
        private final String portfolioName;
        private final String portfolioColor;
        private final String portfolioEmail;
    }
}
