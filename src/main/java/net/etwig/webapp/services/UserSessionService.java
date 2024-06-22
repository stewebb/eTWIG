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
