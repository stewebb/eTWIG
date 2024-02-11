package net.grinecraft.etwig.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.model.UserRole;

import java.time.LocalDateTime;

@Getter
@ToString
public class EventGraphicsDetailsDTO {

    private Long id;
    private String operatorName;
    private String operatorPosition;
    private LocalDateTime uploadTime;
    private Long assetId;

    public EventGraphicsDetailsDTO(EventGraphics eventGraphics){
        this.id = eventGraphics.getId();
        this.uploadTime = eventGraphics.getUploadTime();
        this.assetId = eventGraphics.getAssetId();

        // Operator
        UserRole userRole = eventGraphics.getOperatorRole();
        this.operatorName = userRole.getUser().getFullName();
        this.operatorPosition = userRole.getPosition();
    }
}
