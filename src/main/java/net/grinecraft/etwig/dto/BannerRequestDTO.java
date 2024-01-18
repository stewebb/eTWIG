package net.grinecraft.etwig.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NameUtils;

@Getter
@ToString
public class BannerRequestDTO {
	
	private Long id;
	
	private LocalDate expectDate;
		
	private String requestorName;
	
	private String requestComment;
	
	private LocalDateTime requestTime;
	
	private String requestTimeStr;
	
	private Boolean approved;
	
	private String approverName;
	
	private String responseComment;
	
	private LocalDateTime responseTime;
	
	private String responseTimeStr;
	
	private Long assetId;
	
	public BannerRequestDTO(GraphicsRequest bannerRequest) {
		
		this.id = bannerRequest.getId();
		
		this.expectDate = bannerRequest.getExpectDate();
		
		this.requestComment = bannerRequest.getRequestComment();
		
		this.approved = bannerRequest.getApproved();
		this.responseComment = bannerRequest.getResponseComment();
		this.assetId = bannerRequest.getAssetId();
		
		// Request time
		this.requestTime = bannerRequest.getRequestTime();
		this.requestTimeStr = DateUtils.timeAgo(requestTime);
		
		// Requester (show full name)
		User requestor = bannerRequest.getRequestor();
		this.requestorName = NameUtils.nameMerger(requestor.getFirstName(), requestor.getMiddleName(), requestor.getLastName());
		
		if(approved == null) {
			this.approverName = null;
			this.responseTime = null;
		}
		
		// Only get user name when this request is NOT pending!
		else {
			User approver = bannerRequest.getApprover();
			this.approverName = NameUtils.nameMerger(approver.getFirstName(), approver.getMiddleName(), approver.getLastName());
			
			// Response time
			this.responseTime = bannerRequest.getResponseTime();
			this.responseTimeStr = DateUtils.timeAgo(responseTime);
		}
	}
}
