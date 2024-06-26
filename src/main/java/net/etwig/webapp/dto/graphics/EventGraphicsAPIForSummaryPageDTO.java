package net.etwig.webapp.dto.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventGraphicsAPIForSummaryPageDTO {

    private Long id;
    private String eventName;
    private LocalDateTime startTime;
    private long twigComponentNum;
    private long bannerNum;
    private LocalDateTime lastModified;
    private long pendingApprovalNum;
}
