package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class PositionDTO {
	
	private Long userRoleId;
	private String position;
	private Portfolio portfolio;
	private String email;
	
	public PositionDTO(UserRole userRole){
		this.userRoleId = userRole.getId();
		this.position = userRole.getPosition();
		this.portfolio = userRole.getPortfolio();
		this.email = userRole.getEmail();
	}
}
