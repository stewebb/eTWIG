package net.etwig.webapp.dto.graphics;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.dto.events.GraphicsRequestEventInfoDTO;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class PendingRequestsDetailsDTO {
	
	private Long id;
	private GraphicsRequestEventInfoDTO event;

	private CurrentUserBasicInfoDTO requester;
	private LocalDateTime requestTime;
	private String requesterPosition;
	private Portfolio requesterPortfolio;

	private LocalDate expectDate;
	private String requestComments;
	
	public PendingRequestsDetailsDTO(GraphicsRequest graphicsRequest) {
		this.id = graphicsRequest.getId();
		this.event = new GraphicsRequestEventInfoDTO(graphicsRequest.getEvent());
		this.requestTime = graphicsRequest.getRequestTime();
		this.expectDate = graphicsRequest.getExpectDate();
		this.requestComments = graphicsRequest.getRequestComment();
		
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requester = new CurrentUserBasicInfoDTO(requesterRole.getUser());
		this.requesterPosition = requesterRole.getPosition();
		this.requesterPortfolio = requesterRole.getPortfolio();

	}
}
