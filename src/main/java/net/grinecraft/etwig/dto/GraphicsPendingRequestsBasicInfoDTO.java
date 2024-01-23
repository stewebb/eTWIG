package net.grinecraft.etwig.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class GraphicsPendingRequestsBasicInfoDTO {
	
	private Long id;
	private String eventName;
	private String requesterName;
	private String requesterPosition;
	private String requesterPortfolioColor;
	private LocalDate expectDate;
	
	public GraphicsPendingRequestsBasicInfoDTO(GraphicsRequest graphicsRequest) {
		this.id = graphicsRequest.getId();
		this.eventName = graphicsRequest.getEvent().getName();
		this.expectDate = graphicsRequest.getExpectDate();
		
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requesterName = requesterRole.getUser().getFullName();
		this.requesterPosition = requesterRole.getPosition();
		this.requesterPortfolioColor = requesterRole.getPortfolio().getColor();
	}
}
