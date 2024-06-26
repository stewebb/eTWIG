package net.etwig.webapp.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.EventGraphics;

import java.time.LocalDateTime;

@Getter
@ToString
public class EventGraphicsAPIForDetailsPageDTO {

    private final Long id;
    private final LocalDateTime uploadedTime;
    private final Boolean isBanner;
    private final Long assetId;

    public EventGraphicsAPIForDetailsPageDTO(EventGraphics eventGraphics){
        this.id = eventGraphics.getId();
        this.uploadedTime = eventGraphics.getUploadTime();
        this.isBanner = eventGraphics.isBanner();
        this.assetId = eventGraphics.getAssetId();
    }
}