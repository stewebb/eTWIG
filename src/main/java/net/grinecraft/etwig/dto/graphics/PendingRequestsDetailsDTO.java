package net.grinecraft.etwig.dto.graphics;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.dto.events.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class PendingRequestsDetailsDTO {
	
	private Long id;
	
	private GraphicsRequestEventInfoDTO event;
	private UserDTO requester;
	private String requesterPosition;
	private Portfolio requesterPortfolio;

	private LocalDate expectDate;
	private String requestComments;
	
	public PendingRequestsDetailsDTO(GraphicsRequest graphicsRequest) {
		this.id = graphicsRequest.getId();
		this.event = new GraphicsRequestEventInfoDTO(graphicsRequest.getEvent());
		this.expectDate = graphicsRequest.getExpectDate();
		this.requestComments = graphicsRequest.getRequestComment();
		
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requester = new UserDTO(requesterRole.getUser());
		this.requesterPosition = requesterRole.getPosition();
		this.requesterPortfolio = requesterRole.getPortfolio();

	}
}
