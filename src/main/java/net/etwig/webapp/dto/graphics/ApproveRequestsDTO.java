package net.grinecraft.etwig.dto.graphics;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.NumberUtils;

@Getter
@ToString
public class ApproveRequestsDTO {
	
	private GraphicsRequest currentRequest;
	private Long approverRole;
	private boolean approved;
	private String responseComment;
	private Long assetId;
	
	public ApproveRequestsDTO(GraphicsRequest currentRequest,  Map<String, Object> approvalInfo) {
		this.currentRequest = currentRequest;
		this.approverRole = Long.parseLong(approvalInfo.get("role").toString());
		this.approved = BooleanUtils.toBoolean(approvalInfo.get("approved").toString());
		this.responseComment = approvalInfo.get("comments").toString();
		
		Object assetObj = approvalInfo.get("asset");
		this.assetId = (assetObj == null) ? null : NumberUtils.safeCreateLong(assetObj.toString());
		//this.assetId = NumberUtils.safeCreateLong(approvalInfo.get("asset").toString());
	}
	
	public GraphicsRequest toEntity() {
		currentRequest.setApproverRoleId(this.approverRole);
		currentRequest.setApproved(this.approved);
		currentRequest.setResponseComment(this.responseComment);
		currentRequest.setAssetId(this.assetId);
		currentRequest.setResponseTime(LocalDateTime.now());
		return currentRequest;
	}
}
