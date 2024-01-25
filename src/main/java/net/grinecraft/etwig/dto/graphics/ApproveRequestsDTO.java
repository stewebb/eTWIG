package net.grinecraft.etwig.dto.graphics;

import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.NumberUtils;

@Getter
@ToString
public class ApproveRequestsDTO {
	
	private GraphicsRequest currentRequest;
	private boolean approved;
	private String responseComment;
	private Long assetId;
	
	
	public ApproveRequestsDTO(GraphicsRequest currentRequest,  Map<String, Object> approvalInfo) {
		this.currentRequest = currentRequest;
		this.approved = BooleanUtils.toBoolean(approvalInfo.get("approved").toString());
		this.responseComment = approvalInfo.get("comments").toString();
		this.assetId = NumberUtils.safeCreateLong(approvalInfo.get("asset").toString());
	}
}
