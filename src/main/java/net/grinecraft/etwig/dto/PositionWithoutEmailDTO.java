package net.grinecraft.etwig.dto;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class PositionWithoutEmailDTO {
	
	private Long userRoleId;
	private String position;
	private Long portfolioId;
	private String portfolioName;
	
	public PositionWithoutEmailDTO(UserRole userRole){
		this.userRoleId = userRole.getId();
		this.position = userRole.getPosition();
		
		Portfolio portfolio = userRole.getPortfolio();
		this.portfolioId = portfolio.getId();
		this.portfolioName = portfolio.getName();
	}
}
