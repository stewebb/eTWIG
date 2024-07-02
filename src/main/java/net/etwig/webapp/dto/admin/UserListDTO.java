package net.etwig.webapp.dto.admin;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@ToString
public class UserListDTO {

    private Long userId;
    private String userName;
    private final Set<UserPosition> userPositions = new HashSet<>();

    @Getter
    @AllArgsConstructor
    @ToString
    public class UserPosition {

        private final Long id;
        private final String name;
    }

    public void setUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    // Method to add position to the list
    public void addPosition(Long positionId, String positionName) {
        this.userPositions.add(new UserPosition(positionId, positionName));
    }
}
