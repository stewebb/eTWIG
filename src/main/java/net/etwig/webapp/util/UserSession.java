package net.etwig.webapp.util;

import java.util.Set;

import lombok.Setter;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;

import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;

@Component
public class UserSession {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRoleService userRoleService;
	
	@Setter
    private String email;
	
	public void put() {
		
		if(this.email == null) {
			throw new IllegalArgumentException("Email is required.");
		}
		
		// Find user info
		User user = userRepository.findByEmail(this.email);
		if(user == null) {
			throw new IllegalStateException("User authentication successfully, but the information cannot be found in the database.");
		 }
		
		// Get user role from DB.
		Long userId = user.getId();
		Set<UserRole> userRoles = userRoleRepository.findByUserId(userId);	
		
		// Each user must have assigned at least 1 portfolio.
		if(userRoles.isEmpty()) {
			throw new IllegalStateException("User authentication successfully, but the user is not assigned to any portfolio.");
		}
		
		// Store user info to session
		session.setAttribute("user", new CurrentUserBasicInfoDTO(user));
		session.setAttribute("access", new CurrentUserPermissionDTO(userRoles));
		session.setAttribute("position", new CurrentUserPositionDTO(userRoles));

		//HashMap<Long, String> positions = userRoleService.findPositionsByUser(userId);
		//System.out.println(new LoggedInUserPositionDTO(userRoles));
	}

}
