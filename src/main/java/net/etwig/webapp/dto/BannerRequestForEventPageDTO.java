package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.dto.user.UserDTO;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class BannerRequestForEventPageDTO {

    // Requests-related fields
    private final Long id;
    private final LocalDate expectDate;
    private final String requesterName;
    private LocalDateTime requestTime;
    private String requestComment;

    // Approval-related fields
    private Boolean approved;
    private String approverName;
    private String approverRole;
    private String approverPorrtfolioColor;
    private LocalDateTime responseTime;
    private String responseComment;
    private Long assetId;

    public BannerRequestForEventPageDTO(GraphicsRequest graphicsRequest){

        // Requests-related fields
        this.id = graphicsRequest.getId();
        this.expectDate = graphicsRequest.getExpectDate();
        this.requesterName = graphicsRequest.getRequesterRole().getUser().getFullName();
        this.requestTime = graphicsRequest.getRequestTime();
        this.requestComment = graphicsRequest.getRequestComment();

        // Approval-related fields
        this.approved = graphicsRequest.getApproved();
        this.responseTime = graphicsRequest.getResponseTime();
        this.responseComment = graphicsRequest.getResponseComment();
        this.assetId = graphicsRequest.getAssetId();

        UserRole approverRole = graphicsRequest.getApproverRole();
        this.approverName = approverRole.getUser().getFullName();
        this.approverRole = approverRole.getPosition();
        this.approverPorrtfolioColor = approverRole.getPortfolio().getColor();
    }

}
