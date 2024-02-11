package net.grinecraft.etwig.dto.graphics;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventGraphicsListDTO {

    private Long id;
    private String eventName;
    private long graphicsNum;
    private long bannerNum;
    private LocalDateTime lastModified;

    //public  EventGraphicsListDTO()
}
