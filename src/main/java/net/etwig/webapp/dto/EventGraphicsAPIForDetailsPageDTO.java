package net.etwig.webapp.dto;

import net.etwig.webapp.model.EventGraphics;

public class EventGraphicsAPIForDetailsPageDTO {

    private Long id;
    private Long assetId;

    public EventGraphicsAPIForDetailsPageDTO(EventGraphics eventGraphics){
        this.id = eventGraphics.getId();
        this.assetId = eventGraphics.getAssetId();
    }
}
