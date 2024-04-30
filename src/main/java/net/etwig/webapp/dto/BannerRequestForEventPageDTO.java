package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.dto.user.UserDTO;
import net.etwig.webapp.model.GraphicsRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class BannerRequestForEventPageDTO {

    // Requests-related fields
    private Long id;
    private LocalDate expectDate;
    //private UserDTO requester;
    //private String requestComment;
    //private LocalDateTime requestTime;
    //private String requestTimeStr;

    // Approval-related fields
    //private Boolean approved;
    //private UserDTO approver;
    //private String responseComment;
    //private LocalDateTime responseTime;
    //private String responseTimeStr;
    //private Long assetId;

    public BannerRequestForEventPageDTO(GraphicsRequest graphicsRequest){
        this.id = graphicsRequest.getId();
        this.expectDate = graphicsRequest.getExpectDate();
    }

}
