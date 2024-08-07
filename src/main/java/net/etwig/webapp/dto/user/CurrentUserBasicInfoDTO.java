package net.etwig.webapp.dto.user;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.User;

import java.time.LocalDateTime;

@Getter
@ToString
public class CurrentUserBasicInfoDTO {
	
	private final Long id;
	private final String fullName;
	private final String email;
	private final LocalDateTime lastLogin;
	
	public CurrentUserBasicInfoDTO(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.lastLogin = user.getLastLogin();
	}
}
