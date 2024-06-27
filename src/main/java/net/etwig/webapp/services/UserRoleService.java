/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.etwig.webapp.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.handler.CustomUserDetails;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;

@Service
public class UserRoleService implements UserDetailsService{
	
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private UserSessionService userSessionService;


    /**
     * Retrieves a {@link UserRole} based on the provided role ID.
     * This method queries the database using the provided role ID to find the corresponding user role.
     * If a user role with the specified ID exists, it returns that role; otherwise, it returns null.
     * <p>
     * The use of {@code @NonNull} on the parameter indicates that passing a null value for {@code userRoleId}
     * will result in a {@link NullPointerException}.
     *
     * @param userRoleId the ID of the user role to find. This parameter must not be null.
     * @return the {@link UserRole} corresponding to the provided ID, or null if no such role exists.
     * @throws NullPointerException if {@code userRoleId} is null.
     */

    public UserRole findById(@NonNull Long userRoleId) {
        return userRoleRepository.findById(userRoleId).orElse(null);
    }

    // TODO REPLACE ME
    @SuppressWarnings("null")
	public User getMyDetails() {
        CurrentUserBasicInfoDTO currentUser = userSessionService.validateSession().getBasicInfo();
    	return userRepository.findById(currentUser.getId()).orElse(null);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("hello world");
        
    	// Find user details
    	User user = userRepository.findByEmail(email);
        if(user == null) {
        	throw new UsernameNotFoundException("User not found");
        }

        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        return new CustomUserDetails(user, userRoles);
    }
    
    public void changePassword(User user, String newPassword) {
        String encodedPassword = (new BCryptPasswordEncoder()).encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
