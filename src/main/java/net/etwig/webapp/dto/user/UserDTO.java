package net.grinecraft.etwig.dto.user;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.User;

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
