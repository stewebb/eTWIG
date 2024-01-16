package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.BannerRequest;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.util.NameUtils;

@Getter
@ToString
public class BannerRequestDTO {
	
	private Long id;
	
	//private Long eventId;
	
	private String requestorName;
	
	private String requestComment;
	
	private LocalDateTime requestTime;
	
	private Boolean approved;
	
	private String approverName;
	
	private String responseComment;
	
	private LocalDateTime responseTime;
	
	private Long assetId;
	
	public BannerRequestDTO(BannerRequest bannerRequest) {
		
		this.id = bannerRequest.getId();
		this.requestComment = bannerRequest.getRequestComment();
		this.requestTime = bannerRequest.getRequestTime();
		this.approved = bannerRequest.getApproved();
		this.requestComment = bannerRequest.getRequestComment();
		this.responseTime = bannerRequest.getRequestTime();
		this.assetId = bannerRequest.getAssetId();
		
		if(approved == null) {
			this.requestorName = null;
			this.approverName = null;
		}
		
		// Only get user name when this request is NOT pending!
		else {
			User requestor = bannerRequest.getRequestor();
			User approver = bannerRequest.getApprover();
			this.requestorName = NameUtils.nameMerger(requestor.getFirstName(), requestor.getMiddleName(), requestor.getLastName());
			this.approverName = NameUtils.nameMerger(approver.getFirstName(), approver.getMiddleName(), approver.getLastName());
		}
}
}
