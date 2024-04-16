package net.etwig.webapp.dto.graphics;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class NewRequestEmailNotificationDTO {
	
	// Event
	private String eventName;
	
	// Requester
	private String requesterName;
	private String requesterPosition;
	private String requesterPortfolioName;
	private String requesterPortfolioColor;
	
	// Request
	private LocalDate expectDate;
	private String comments;
	private LocalDateTime requestTime;
	
	public NewRequestEmailNotificationDTO(GraphicsRequest graphicsRequest) {
		
		// Event
		//this.eventName = graphicsRequest.getEvent().getName();
		
		// Requester
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requesterName = requesterRole.getUser().getFullName();
		this.requesterPosition = requesterRole.getPosition();
		
		Portfolio requesterPortfolio = requesterRole.getPortfolio();
		this.requesterPortfolioName = requesterPortfolio.getName();
		this.requesterPortfolioColor = requesterPortfolio.getColor();
		
		// Request
		this.expectDate = graphicsRequest.getExpectDate();
		this.comments = graphicsRequest.getRequestComment();
		this.requestTime = graphicsRequest.getRequestTime();
	}
	
}
