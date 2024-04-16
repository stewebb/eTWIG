package net.etwig.webapp.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.dto.user.UserAccessDTO;
import net.etwig.webapp.dto.user.UserDTO;
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
		
		// Each user must has assigned at least 1 portfolio.
		if(userRoles.isEmpty()) {
			throw new IllegalStateException("User authentication successfully, but the user is not assigned to any portfolio.");
		}
		
		// Store user and permission
		session.setAttribute("user", new UserDTO(user));
		session.setAttribute("access", new UserAccessDTO(userRoles));
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
