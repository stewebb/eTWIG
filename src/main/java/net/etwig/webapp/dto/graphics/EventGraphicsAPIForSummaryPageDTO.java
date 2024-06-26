package net.etwig.webapp.dto.graphics;

import lombok.*;
import net.etwig.webapp.model.EventGraphics;

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

    //public EventGraphicsAPIForSummaryPageDTO(EventGraphics eventGraphics) {
    //}
}
