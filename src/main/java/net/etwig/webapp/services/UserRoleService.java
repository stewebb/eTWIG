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

import net.etwig.webapp.util.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.etwig.webapp.handler.CustomUserDetails;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;

@Service
public class UserRoleService implements UserDetailsService {
	
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

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

    /**
     * Loads the user details by email.
     * <p>
     * This method searches for a user with the specified email address, retrieves their roles,
     * and returns a {@link CustomUserDetails} object containing the user's information and roles.
     * </p>
     *
     * @param email the email address of the user to load.
     * @return a {@link UserDetails} object containing the user's information and roles.
     * @throws UsernameNotFoundException if no user is found with the specified email address.
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    	// Find user details
    	User user = userRepository.findByEmail(email);
        if(user == null) {
        	throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Set role
        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        return new CustomUserDetails(user, userRoles);
    }

    /**
     * Changes a user's password after validating the current password.
     * This method first verifies if the user exists and checks if the provided current password matches
     * the stored password. If the current password is correct, it updates the user's password with the new password.
     *
     * @param userId The unique identifier of the user whose password is to be changed.
     * @param currentPassword The current password of the user, which needs to be verified.
     * @param newPassword The new password to be set for the user.
     * @return {@code true} if the password was successfully changed, {@code false} otherwise.
     * @throws InvalidParameterException if no user exists with the provided {@code userId}.
     * @throws IllegalArgumentException if the current password does not match the stored password.
     */

    public boolean changePassword(Long userId, String currentPassword, String newPassword) {

        // User check
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
        	throw new InvalidParameterException("User with id=" + userId + "does not exist.");
        }

        // Original password check
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        // Change password
        String encodedPassword = (new BCryptPasswordEncoder()).encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

}
