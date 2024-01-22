package net.grinecraft.etwig.dto;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class PositionDTO {
	
	private Long userRoleId;
	private String position;
	private Portfolio portfolio;
	//private Long portfolioId;
	
	public PositionDTO(UserRole userRole){
		this.userRoleId = userRole.getId();
		this.position = userRole.getPosition();
		this.portfolio = userRole.getPortfolio();
	}
}
