package net.grinecraft.etwig.dto.graphics;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.dto.AssetBasicInfoDTO;
import net.grinecraft.etwig.dto.events.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class FinalizedRequestsDetailsDTO {
	
	private Long id;
	
	// Response Info
	private Boolean approved;
	private String responseComment;
	private LocalDateTime responseTime;
	private Long assetId;
	
	// Approver Info
	private String approverName;
	private String approverPosition;
	private String approverPortfolioName;
	private String approverPortfolioColor;
	
	// Request Info
	private String requestComment;
	private LocalDateTime requestTime;
	private LocalDate expectDate;
	
	// Requester Info
	private String requesterName;
	private String requesterPosition;
	private String requesterPortfolioName;
	private String requesterPortfolioColor;
	
	// Event Info
	private String eventName;
	private String eventLocation;
	private String eventDescription;
	private boolean eventRecurrent;
	private LocalDateTime eventStartTime;
	private int eventDuration;
	
	// Event Organizer Info
	private String organizerName;
	private String organizerPosition;
	private String organizerPortfolioName;
	private String organizerPortfolioColor;
	
	public FinalizedRequestsDetailsDTO(GraphicsRequest graphicsRequest) {
		this.id = graphicsRequest.getId();
		
		// Response Info
		this.approved = graphicsRequest.getApproved();
		this.responseComment = graphicsRequest.getResponseComment();
		this.responseTime = graphicsRequest.getResponseTime();
		this.assetId = graphicsRequest.getAssetId();
		
		// Approver Info
		UserRole approverRole = graphicsRequest.getApproverRole();
		Portfolio approverPortfolio = approverRole.getPortfolio();
		this.approverName = approverRole.getUser().getFullName();
		this.approverPosition = approverRole.getPosition();
		this.approverPortfolioName = approverPortfolio.getName();
		this.approverPortfolioColor = approverPortfolio.getColor();
		
		// Request Info
		this.requestComment = graphicsRequest.getRequestComment();
		this.requestTime = graphicsRequest.getRequestTime();
		this.expectDate = graphicsRequest.getExpectDate();
		
		// Requester Info
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		Portfolio requesterPortfolio = requesterRole.getPortfolio();
		this.requesterName = requesterRole.getUser().getFullName();
		this.requesterPosition = requesterRole.getPosition();
		this.requesterPortfolioName = requesterPortfolio.getName();
		this.requesterPortfolioColor = requesterPortfolio.getColor();
		
		// Event Info
		Event event = graphicsRequest.getEvent();
		this.eventName = event.getName();
		this.eventLocation = event.getLocation();
		this.eventDescription = event.getDescription();
		this.eventRecurrent = event.isRecurring();
		this.eventStartTime = event.getStartTime();
		this.eventDuration = event.getDuration();
		
		// Event Organizer Info
		UserRole eventOrganizerRole = event.getUserRole();
		Portfolio eventOrganizerPortfolio = eventOrganizerRole.getPortfolio();
		this.organizerName = eventOrganizerRole.getUser().getFullName();
		this.organizerPosition = eventOrganizerRole.getPosition();
		this.organizerPortfolioName = eventOrganizerPortfolio.getName();
		this.organizerPortfolioColor = eventOrganizerPortfolio.getColor();
	}
}
