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
            //this.basicInfo = new CurrentUserBasicInfoDTO(user.get());
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
        //this.permission = new CurrentUserPermissionDTO(userRoles);
        wrapper.setPermission(new CurrentUserPermissionDTO(userRoles));

        // Validate user position
        //this.position = new CurrentUserPositionDTO(userRoles);
        wrapper.setPosition(new CurrentUserPositionDTO(userRoles));
        if(!wrapper.getPosition().changeMyPosition(userRoleId)){
            throw new IllegalStateException("Failed to validate user position. Your session may expired!");
        }

        return wrapper;
    }

    public void switchPosition(Long userRoleId){
        CurrentUserDTOWrapper wrapper = validateSession();
        if(wrapper.getPosition().changeMyPosition(userRoleId)){
            return;
        }
    }

}
