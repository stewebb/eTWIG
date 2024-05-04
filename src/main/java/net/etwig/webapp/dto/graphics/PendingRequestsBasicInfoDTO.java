package net.etwig.webapp.dto.graphics;

import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class PendingRequestsBasicInfoDTO {
	
	private Long id;
	private String eventName;
	private String requesterName;
	private String requesterPosition;
	//private String requesterPortfolioColor;
	private LocalDate expectDate;
	private String requestComments;
	
	public PendingRequestsBasicInfoDTO(GraphicsRequest graphicsRequest) {
		this.id = graphicsRequest.getId();
		this.eventName = graphicsRequest.getEvent().getName();
		this.expectDate = graphicsRequest.getExpectDate();
		
		// Get the first 31 characters of the comment.
		String comment = graphicsRequest.getRequestComment();
		if(comment == null) {
			this.requestComments = "";
		} else if (comment.length() <= 31){
			this.requestComments = comment;
		} else {
			comment.substring(0, 31);
		}
		
		UserRole requesterRole = graphicsRequest.getRequesterRole();
		this.requesterName = requesterRole.getUser().getFullName();
		this.requesterPosition = requesterRole.getPosition();
		//this.requesterPortfolioColor = requesterRole.getPortfolio().getColor();

	}
}
