package net.grinecraft.etwig.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.BooleanUtils;

import java.time.LocalDateTime;

@Getter
@ToString
public class NewGraphicsDTO {

    private Long eventId;
    private Long operatorRoleId;
    private Long assetId;

    public void fromApproval(GraphicsRequest graphicsRequest){

        // Only approved requests are allowed to proceed.
        if(!BooleanUtils.toBoolean(graphicsRequest.getApproved())){
            throw new IllegalArgumentException("This graphics request was declined.");
        }

        this.eventId = graphicsRequest.getEventId();
        this.operatorRoleId = graphicsRequest.getApproverRoleId();
        this.assetId = graphicsRequest.getAssetId();
    }

    public EventGraphics toEntity(){
        EventGraphics eventGraphics = new EventGraphics();

        eventGraphics.setEventId(this.eventId);
        eventGraphics.setOperatorRoleId(this.operatorRoleId);
        eventGraphics.setAssetId(this.assetId);
        eventGraphics.setUploadTime(LocalDateTime.now());

        return eventGraphics;
    }

}
