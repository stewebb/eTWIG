package net.etwig.webapp.services;

import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        session.setAttribute("user", userId);

        // Store user info to session
        //session.setAttribute("user", new CurrentUserBasicInfoDTO(user));
        //session.setAttribute("access", new CurrentUserPermissionDTO(userRoles));
        //session.setAttribute("position", new CurrentUserPositionDTO(userRoles));

    }
}
