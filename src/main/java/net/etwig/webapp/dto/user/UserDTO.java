package net.etwig.webapp.dto.user;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.User;

@Getter
@ToString
public class UserDTO {
	
	private Long id;
	private String fullName;
	private String email;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
	}
}
