package net.etwig.webapp.services;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
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

        // Store user info to session
        //session.setAttribute("user", new CurrentUserBasicInfoDTO(user));
        //session.setAttribute("access", new CurrentUserPermissionDTO(userRoles));
        //session.setAttribute("position", new CurrentUserPositionDTO(userRoles));

    }

    /*
    public CurrentUserBasicInfoDTO getUserInfo() {
        Long userId = (Long) session.getAttribute("user");

        // Only user ID is stored in session. It will be validated.
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return new CurrentUserBasicInfoDTO(user.get());
        }

        else {
            throw new IllegalStateException("Failed to validate in-session user ID. Your session may expired!");
        }
    }

    //public void resetPosition(Long userRoleId) {
    //

  //  }*/

    public SessionValidation validateSession(){
        Long userId = (Long) session.getAttribute("user");
        Long userRoleId = (Long) session.getAttribute("position");
        return new SessionValidation(userId, userRoleId);
    }


    @Getter
    @ToString
    public class SessionValidation {

        private final CurrentUserBasicInfoDTO basicInfo;
        private final CurrentUserPermissionDTO permission;
        private final CurrentUserPositionDTO position;

        public SessionValidation(Long userId, Long userRoleId) {

            // Validate user basic info
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                this.basicInfo = new CurrentUserBasicInfoDTO(user.get());
            }

            else {
                throw new IllegalStateException("Failed to validate user information. Your session may expired!");
            }

            // Validate user permission
            Set<UserRole> userRoles = userRoleRepository.findByUserId(userId);
            if(userRoles.isEmpty()) {
                throw new IllegalStateException("Failed to validate user permission. Your session may expired!");
            }
            this.permission = new CurrentUserPermissionDTO(userRoles);

            // Validate user position
            this.position = new CurrentUserPositionDTO(userRoles);
            if(!this.position.changeMyPosition(userRoleId)){
                throw new IllegalStateException("Failed to validate user position. Your session may expired!");
            }
        }
    }
}
