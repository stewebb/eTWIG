package net.etwig.webapp.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class BannerRequestDetailsDTO {

    // Requests-related fields
    private final Long id;
    private final LocalDate expectDate;
    private final String requesterName;
    private final LocalDateTime requestTime;
    private final String requestComment;
    private final String eventName;

    // Approval-related fields
    private final Boolean approved;
    private String approverName;
    private String approverPosition;
    private String approverPorrtfolioColor;
    private final LocalDateTime responseTime;
    private final String responseComment;
    private final Long assetId;

    public BannerRequestDetailsDTO(GraphicsRequest graphicsRequest){

        // Requests-related fields
        this.id = graphicsRequest.getId();
        this.expectDate = graphicsRequest.getExpectDate();
        this.requesterName = graphicsRequest.getRequesterRole().getUser().getFullName();
        this.requestTime = graphicsRequest.getRequestTime();
        this.requestComment = graphicsRequest.getRequestComment();
        this.eventName = graphicsRequest.getEvent().getName();

        // Approval-related fields
        this.approved = graphicsRequest.getApproved();
        this.responseTime = graphicsRequest.getResponseTime();
        this.responseComment = graphicsRequest.getResponseComment();
        this.assetId = graphicsRequest.getAssetId();

        UserRole approverRole = graphicsRequest.getApproverRole();

        // approverRole is null if the banner request is pending.
        if(approverRole != null){
            this.approverName = approverRole.getUser().getFullName();
            this.approverPosition = approverRole.getPosition();
            this.approverPorrtfolioColor = approverRole.getPortfolio().getColor();
        }

    }

}
