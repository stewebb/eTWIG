package net.etwig.webapp.dto.user;

import lombok.*;

/**
 * Wrapper class for current user data, consolidating basic information, permissions, and positional data into a single object.
 * This class is used throughout the application to easily pass and manipulate user-specific data in various layers and contexts.
 * <p>
 * Annotations:
 * - @Setter: Automatically generates setter methods for all fields.
 * - @Getter: Automatically generates getter methods for all fields.
 * - @ToString: Automatically generates a custom toString method that includes all fields.
 * - @NoArgsConstructor: Generates a no-argument constructor.
 * - @AllArgsConstructor: Generates an all-argument constructor that accepts values for all fields.
 * <p>
 * Fields:
 * - basicInfo: Contains basic information of the user such as name, email, etc.
 * - permission: Contains permission details associated with the user, defining what operations the user can perform.
 * - position: Contains positional or organizational details about the user like department, title, etc.
 */

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
