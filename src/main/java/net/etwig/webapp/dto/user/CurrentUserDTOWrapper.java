package net.etwig.webapp.dto.user;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDTOWrapper {
    private CurrentUserBasicInfoDTO basicInfo;
    private CurrentUserPermissionDTO permission;
    private CurrentUserPositionDTO position;
}
