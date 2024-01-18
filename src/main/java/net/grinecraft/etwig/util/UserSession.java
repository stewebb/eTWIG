package net.grinecraft.etwig.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.UserRoleDTO;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;

import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.repository.UserRoleRepository;

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
		
		// Use a DTO to avoid storing sensitive information in session.
		Set<UserRoleDTO> userRoleDTOs = new HashSet<UserRoleDTO>();
		
		for(UserRole role : userRoles) {
			userRoleDTOs.add(new UserRoleDTO(role));
		}
		System.out.println(userRoleDTOs);
		
		session.setAttribute("user", user);
		session.setAttribute("role", userRoles);
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
