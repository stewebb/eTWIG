package net.grinecraft.etwig.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.util.BooleanUtils;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@ToString
public class NewGraphicsDTO {

    private Long eventId;
    private Long operatorRoleId;
    private Long assetId;
    private boolean banner;

    /**
     * Get new graphics details from request approvals.
     * @param graphicsRequest The detail of approved requests.
     */

    public void fromApproval(GraphicsRequest graphicsRequest){

        // Only approved requests are allowed to proceed.
        if(!BooleanUtils.toBoolean(graphicsRequest.getApproved())){
            throw new IllegalArgumentException("This graphics request was declined.");
        }

        this.eventId = graphicsRequest.getEventId();
        this.operatorRoleId = graphicsRequest.getApproverRoleId();
        this.assetId = graphicsRequest.getAssetId();
        this.banner = true;
    }

    /**
     * Get new graphics details from the dedicated Event graphics management page.
     * @param newGraphicsInfo
     */

    public void addDirectly(Map<String, Object> newGraphicsInfo){
        this.eventId = Long.parseLong(newGraphicsInfo.get("eventId").toString());
        this.operatorRoleId = Long.parseLong(newGraphicsInfo.get("operatorRole").toString());
        this.banner = BooleanUtils.toBoolean(newGraphicsInfo.get("isBanner").toString());
        this.assetId = Long.parseLong(newGraphicsInfo.get("asset").toString());
    }

    public EventGraphics toEntity(){
        EventGraphics eventGraphics = new EventGraphics();

        eventGraphics.setEventId(this.eventId);
        eventGraphics.setOperatorRoleId(this.operatorRoleId);
        eventGraphics.setAssetId(this.assetId);
        eventGraphics.setUploadTime(LocalDateTime.now());
        eventGraphics.setBanner(this.banner);

        return eventGraphics;
    }

}
