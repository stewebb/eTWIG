package net.etwig.webapp.dto.graphics;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.BannerRequest;
import net.etwig.webapp.util.NumberUtils;
import org.apache.commons.lang3.BooleanUtils;

@Getter
@ToString
public class ApproveRequestsDTO {
	
	private final BannerRequest currentRequest;
	private final Long approverRole;
	private final boolean approved;
	private final String responseComment;
	private final Long assetId;
	
	public ApproveRequestsDTO(BannerRequest currentRequest, Map<String, Object> approvalInfo, Long loggedInUserPosition) {
		this.currentRequest = currentRequest;
		this.approverRole = loggedInUserPosition;
		this.approved = BooleanUtils.toBoolean(approvalInfo.get("approved").toString());
		this.responseComment = approvalInfo.get("comments").toString();
		
		Object assetObj = approvalInfo.get("asset");
		this.assetId = (assetObj == null) ? null : NumberUtils.safeCreateLong(assetObj.toString());
	}
	
	public BannerRequest toEntity() {
		currentRequest.setApproverRoleId(this.approverRole);
		currentRequest.setApproved(this.approved);
		currentRequest.setResponseComment(this.responseComment);
		currentRequest.setAssetId(this.assetId);
		currentRequest.setResponseTime(LocalDateTime.now());
		return currentRequest;
	}
}
