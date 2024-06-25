package net.etwig.webapp.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.model.BannerRequest;
import org.apache.commons.lang3.BooleanUtils;

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
     * @param bannerRequest The detail of approved requests.
     */

    public void fromApproval(BannerRequest bannerRequest){

        // Only approved requests are allowed to proceed.
        if(!bannerRequest.getApproved()){
            throw new IllegalArgumentException("This graphics request was declined.");
        }

        this.eventId = bannerRequest.getEventId();
        this.operatorRoleId = bannerRequest.getApproverRoleId();
        this.assetId = bannerRequest.getAssetId();
        this.banner = true;
    }

    /**
     * Get new graphics details from the dedicated Event graphics management page.
     * @param newGraphicsInfo
     */

    public void addDirectly(Map<String, Object> newGraphicsInfo, Long loggedInPosition){
        this.eventId = Long.parseLong(newGraphicsInfo.get("eventId").toString());
        this.operatorRoleId = loggedInPosition;//Long.parseLong(newGraphicsInfo.get("operatorRole").toString());
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
