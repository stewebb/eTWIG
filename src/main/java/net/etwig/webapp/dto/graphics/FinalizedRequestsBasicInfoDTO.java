package net.etwig.webapp.dto.graphics;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.UserRole;

@Getter
@ToString
public class FinalizedRequestsBasicInfoDTO {
	
	private Long id;
	private String eventName;
	
	// Requester
	private String requesterName;
	private String requesterPosition;
	private LocalDate expectDate;
	
	// Approver
	private boolean approved;
	private String approverName;
	private String approverPosition;
	private LocalDateTime responseTime;
	
	public FinalizedRequestsBasicInfoDTO(GraphicsRequest graphicsRequest) {
		
		
		this.id = graphicsRequest.getId();
		this.eventName = graphicsRequest.getEvent().getName();
		this.expectDate = graphicsRequest.getExpectDate();
		
		// Requester
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requesterName = requesterRole.getUser().getFullName();
		this.requesterPosition = requesterRole.getPosition();
		
		// Approver
		this.approved = graphicsRequest.getApproved();
		this.responseTime = graphicsRequest.getResponseTime();
		
		UserRole approverRole = graphicsRequest.getApproverRole();
		this.approverName = approverRole.getUser().getFullName();
		this.approverPosition = approverRole.getPosition();
	}
}
