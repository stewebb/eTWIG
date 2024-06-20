package net.etwig.webapp.dto.user;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.User;

@Getter
@ToString
public class UserDTO {
	
	private final Long id;
	private final String fullName;
	private final String email;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
	}
}
