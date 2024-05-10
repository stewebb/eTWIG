package net.etwig.webapp.dto;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.EventGraphics;

import java.time.LocalDateTime;

@Getter
@ToString
public class EventGraphicsAPIForDetailsPageDTO {

    private final Long id;
    private final LocalDateTime uploadedTime;
    private final Long assetId;

    public EventGraphicsAPIForDetailsPageDTO(EventGraphics eventGraphics){
        this.id = eventGraphics.getId();
        this.uploadedTime = eventGraphics.getUploadTime();
        this.assetId = eventGraphics.getAssetId();
    }
}
