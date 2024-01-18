package net.grinecraft.etwig.dto;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class UserRoleDTO {
	
	private Long id;
	private String position;
	private Long portfolio;
	
	public UserRoleDTO(UserRole userRole) {
		this.id = userRole.getId();
		this.position = userRole.getPosition();
		this.portfolio = userRole.getPortfolioId();
	}
}
