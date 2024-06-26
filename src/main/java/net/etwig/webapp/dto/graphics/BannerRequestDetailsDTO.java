package net.etwig.webapp.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.BannerRequest;
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
    private final String requesterPosition;
    private final LocalDateTime requestTime;
    private final String requestComment;
    private final String eventName;

    // Approval-related fields
    private final Boolean approved;
    private String approverName;
    private String approverPosition;
    private String approverPortfolioColor;
    private final LocalDateTime responseTime;
    private final String responseComment;
    private final Long assetId;

    public BannerRequestDetailsDTO(BannerRequest bannerRequest){

        // Requests-related fields
        this.id = bannerRequest.getId();
        this.expectDate = bannerRequest.getExpectDate();
        this.requesterName = bannerRequest.getRequesterRole().getUser().getFullName();
        this.requesterPosition = bannerRequest.getRequesterRole().getPosition();
        this.requestTime = bannerRequest.getRequestTime();
        this.requestComment = bannerRequest.getRequestComment();
        this.eventName = bannerRequest.getEvent().getName();

        // Approval-related fields
        this.approved = bannerRequest.getApproved();
        this.responseTime = bannerRequest.getResponseTime();
        this.responseComment = bannerRequest.getResponseComment();
        this.assetId = bannerRequest.getAssetId();

        UserRole approverRole = bannerRequest.getApproverRole();

        // approverRole is null if the banner request is pending.
        if(approverRole != null){
            this.approverName = approverRole.getUser().getFullName();
            this.approverPosition = approverRole.getPosition();
            this.approverPortfolioColor = approverRole.getPortfolio().getColor();
        }
    }
}
