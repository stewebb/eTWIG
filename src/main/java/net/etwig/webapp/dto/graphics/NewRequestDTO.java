package net.etwig.webapp.dto.graphics;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.util.DateUtils;

@Getter
@ToString
public class NewRequestDTO {

	private Long eventId;
	private Long requesterRole;
	private String requestComment;
	private String expectDateStr;
	
	public void fromMap(Map<String, Object> requestInfo) {
		this.eventId = Long.parseLong(requestInfo.get("eventId").toString());
		this.requesterRole = Long.parseLong(requestInfo.get("requesterRole").toString());
		this.requestComment = requestInfo.get("requestComment").toString();
		this.expectDateStr = requestInfo.get("returningDate").toString();
	}
	
	public void fromParam(Long eventId, Long requesterRole, String requestComment,  String expectDateStr) {
		this.eventId = eventId;
		this.requesterRole = requesterRole;
		this.requestComment = requestComment;
		this.expectDateStr = expectDateStr;
	}
	
	public GraphicsRequest toEntity() {
		GraphicsRequest request = new GraphicsRequest();
		request.setEventId(this.eventId);
		request.setRequesterRoleId(this.requesterRole);
		request.setRequestComment(this.requestComment);
		request.setExpectDate(DateUtils.safeParseDate(this.expectDateStr, "yyyy-MM-dd"));
		request.setRequestTime(LocalDateTime.now());
		return request;
	}
}
