package net.etwig.webapp.services;

import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import net.etwig.webapp.util.InvalidPositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserSessionService {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Initializes a session for a user based on the provided email.
     * This method performs several checks to ensure that the user exists, and has valid roles and a position.
     * <p>
     * Steps involved in session initialization:
     * 1. Validates that the provided email is not null. If null, an {@link IllegalArgumentException} is thrown.
     * 2. Retrieves the user information from the database using the provided email. If no user is found,
     *    an {@link IllegalStateException} is thrown, indicating a problem with finding the user in the database.
     * 3. Ensures that the user has at least one assigned role. If no roles are found, another
     *    {@link IllegalStateException} is thrown, indicating that the user is not assigned to any portfolio.
     * 4. A default position is assigned based on the user's roles, and this position, along with the user's ID,
     *    is stored in the session.
     * <p>
     * The user's ID and their default position ID are then stored as session attributes,
     * preparing the session for further interactions.
     *
     * @param email the email address of the user to authenticate and initialize in the session
     * @throws IllegalArgumentException if the provided email is null, indicating that a necessary parameter is missing.
     * @throws IllegalStateException if no user is found for the provided email, or if the user has no assigned roles,
     *         suggesting issues with user data or authentication.
     */

    public void initializeSession(String email){

        if(email == null) {
            throw new IllegalArgumentException("Email is required.");
        }

        // Find user info
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new IllegalStateException("User authentication successfully, but the information cannot be found in database.");
        }
        Long userId = user.getId();

        // Position check: Each user must have assigned at least one position.
        Set<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        if(userRoles.isEmpty()) {
            throw new IllegalStateException("User authentication successfully, but the user is not assigned to any portfolio.");
        }

        // Assign default position
        CurrentUserPositionDTO position = new CurrentUserPositionDTO(userRoles);

        // Store to session
        session.setAttribute("user", userId);
        session.setAttribute("position", position.getMyCurrentPositionId());
    }

    /**
     * Validates the current user's session and retrieves their associated data.
     * This method checks for the presence of "user" and "position" attributes in the session.
     * If either attribute is missing, the method returns null, indicating an invalid or expired session.
     * <p>
     * The validation process involves three main steps:
     * 1. Fetching the user's basic information from the database using their user ID and encapsulating it
     *    in a {@link CurrentUserBasicInfoDTO} object if the user exists.
     * 2. Validating the user's roles and permissions, ensuring that the user has at least one role,
     *    and encapsulating this information in a {@link CurrentUserPermissionDTO}.
     * 3. Validating the user's position by comparing it with the session's "position" attribute
     *    and updating the position information in {@link CurrentUserPositionDTO}.
     * <p>
     * If any step fails, an {@link IllegalStateException} is thrown with an appropriate error message,
     * suggesting that the session may have expired or the required information is otherwise incorrect.
     *
     * @return an instance of {@link CurrentUserDTOWrapper} containing the validated user information,
     *         or null if the session attributes are not properly set.
     * @throws IllegalStateException if the user's basic information, permissions, or position cannot be validated.
     */

    public CurrentUserDTOWrapper validateSession(){
        Long userId = (Long) session.getAttribute("user");
        Long userRoleId = (Long) session.getAttribute("position");

        // Sometime session is not set
        if(userId == null || userRoleId == null){
            return null;
        }

        CurrentUserDTOWrapper wrapper = new CurrentUserDTOWrapper();

        // Validate user basic info
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            wrapper.setBasicInfo(new CurrentUserBasicInfoDTO(user.get()));
        }

        else {
            throw new IllegalStateException("Failed to validate user information. Your session may expired!");
        }

        // Validate user permission
        Set<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        if(userRoles.isEmpty()) {
            throw new IllegalStateException("Failed to validate user permission. Your session may expired!");
        }
        wrapper.setPermission(new CurrentUserPermissionDTO(userRoles));

        // Validate user position
        wrapper.setPosition(new CurrentUserPositionDTO(userRoles));
        if(!wrapper.getPosition().changeMyPosition(userRoleId)){
            throw new IllegalStateException("Failed to validate user position. Your session may expired!");
        }

        return wrapper;
    }

    /**
     * Attempts to switch the current user's position to the specified role ID.
     * <p>
     * This method validates the user's session and attempts to update their position based on the provided role ID.
     * If the user's session is valid and the role ID is associated with the user, the position is updated in the session,
     * and the updated position is returned. If the role ID is not associated with the user, an {@link InvalidPositionException}
     * is thrown, indicating the role change is not permissible.
     * </p>
     *
     * @param userRoleId The role ID to which the user wants to switch.
     * @return The updated position of the user as a {@code CurrentUserPositionDTO.Position} if the role change is successful.
     * @throws InvalidPositionException If the role ID is not valid for the current user or the user is not allowed to
     *                                  switch to this role.
     */

    public CurrentUserPositionDTO.Position switchPosition(Long userRoleId){
        CurrentUserPositionDTO position = validateSession().getPosition();

        // Update position ID in session and return the updated position.
        if(position.changeMyPosition(userRoleId)) {
            session.setAttribute("position", userRoleId);
            return position.getMyCurrentPosition();
        }

        // Otherwise, return HTTP 403
        else{
            throw new InvalidPositionException("Selected position is invalid. You are not assigned by this position.");
        }
    }
}
