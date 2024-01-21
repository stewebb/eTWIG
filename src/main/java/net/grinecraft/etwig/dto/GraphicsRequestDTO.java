package net.grinecraft.etwig.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.DateUtils;

@Getter
@ToString
public class GraphicsRequestDTO {
	
	// Requests-related fields
	private Long id;
	private LocalDate expectDate;
	private String requesterName;
	private String requestComment;
	private LocalDateTime requestTime;
	private String requestTimeStr;
	
	// Approvel-related fields
	private Boolean approved;
	private String approverName;
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
		
		// Requester (show full name)
		this.requesterName = graphicsRequest.getRequester().getFullName();
		
		if(approved == null) {
			this.approverName = null;
			this.responseTime = null;
		}
		
		// Only get user name when this request is NOT pending!
		else {
			this.approverName = graphicsRequest.getApprover().getFullName();
			
			// Response time
			this.responseTime = graphicsRequest.getResponseTime();
			this.responseTimeStr = DateUtils.timeAgo(responseTime);
		}
		
		
	}
}
