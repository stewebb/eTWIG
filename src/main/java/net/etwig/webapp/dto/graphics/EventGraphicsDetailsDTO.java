package net.etwig.webapp.dto.graphics;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.model.UserRole;

import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class EventGraphicsDetailsDTO {

    private Long id;
    private String operatorName;
    private String operatorPosition;
    private String uploadTime;
    private Long assetId;
    private boolean banner;

    public EventGraphicsDetailsDTO(EventGraphics eventGraphics){
        this.id = eventGraphics.getId();
        this.assetId = eventGraphics.getAssetId();
        this.banner = eventGraphics.isBanner();

        // Operator
        UserRole userRole = eventGraphics.getOperatorRole();
        this.operatorName = userRole.getUser().getFullName();
        this.operatorPosition = userRole.getPosition();

        // Convert upload time to ISO 8601 format
        this.uploadTime = eventGraphics.getUploadTime().format(DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss"));
    }
}
