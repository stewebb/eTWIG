package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.User;

@Getter
@ToString
public class LoggedInUserInfoDTO {

    private final Long id;
    private final String fullName;
    private final String email;

    public LoggedInUserInfoDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
    }
}
