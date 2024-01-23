package net.grinecraft.etwig.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.DateUtils;

@Getter
@ToString
public class GraphicsRequestDTO {
	
	// Requests-related fields
	private Long id;
	private LocalDate expectDate;
	private UserDTO requester;
	private String requestComment;
	private LocalDateTime requestTime;
	private String requestTimeStr;
	
	// Approvel-related fields
	private Boolean approved;
	private UserDTO approver;
	private String responseComment;
	private LocalDateTime responseTime;
	private String responseTimeStr;
	private Long assetId;
	
	public GraphicsRequestDTO(GraphicsRequest graphicsRequest) {
		
		
		this.id = graphicsRequest.getId();
		this.expectDate = graphicsRequest.getExpectDate();
		this.requestComment = graphicsRequest.getRequestComment();
		
		this.approved = graphicsRequest.getApproved();
		this.responseComment = graphicsRequest.getResponseComment();
		this.assetId = graphicsRequest.getAssetId();
		
		// Request time
		this.requestTime = graphicsRequest.getRequestTime();
		this.requestTimeStr = DateUtils.timeAgo(requestTime);
		
		// Requester
		this.requester = new UserDTO(graphicsRequest.getRequesterRole().getUser());
		
		// The request status is pending.
		if(approved == null) {
			this.approver = null;
			this.responseTime = null;
		}
		
		// Only get user name when this request is NOT pending!
		else {
			this.approver = new UserDTO(graphicsRequest.getApproverRole().getUser());
			
			// Response time
			this.responseTime = graphicsRequest.getResponseTime();
			this.responseTimeStr = DateUtils.timeAgo(responseTime);
		}
		
		
	}
}
