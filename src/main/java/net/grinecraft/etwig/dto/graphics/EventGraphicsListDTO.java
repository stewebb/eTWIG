package net.grinecraft.etwig.dto.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class EventGraphicsListDTO {

    private Long id;
    private String eventName;
    private int graphicsNum;
    private int bannerNum;
    private LocalDateTime lastModified;

    //public  EventGraphicsListDTO()
}
