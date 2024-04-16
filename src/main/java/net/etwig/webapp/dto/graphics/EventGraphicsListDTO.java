package net.etwig.webapp.dto.graphics;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventGraphicsListDTO {

    private Long id;
    private String eventName;
    private LocalDateTime startTime;
    private long twigComponentNum;
    private long bannerNum;
    private LocalDateTime lastModified;
}
