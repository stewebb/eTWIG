/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-authentication-related options..
	*/

package net.grinecraft.etwig.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Permission;
import net.grinecraft.etwig.model.UserAuth;
import net.grinecraft.etwig.repository.UserAuthRepository;
import net.grinecraft.etwig.util.type.UserPermission;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserAuthRepository userAuthRepository;

	/**
	 * User Authentication, find the user information by the email address..
	 * @param email The given email address.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserAuth userAuth = userAuthRepository.findByEmail(email);
        if (userAuth == null) {
        	throw new UsernameNotFoundException("User not found");
        }
        
        return new org.springframework.security.core.userdetails.User(
        		userAuth.getEmail(), 
        		userAuth.getPassword(), 
        		getAuthorities(userAuth)
        );
	}
	
	/**
	 * Get user permission from the database.
	 * @param userAuth
	 * @return
	 */
	
    private Collection<? extends GrantedAuthority> getAuthorities(UserAuth userAuth) {
    	 Permission permission = userAuth.getPermission();
         UserPermission userPermission = UserPermission.fromString(permission.getName());
         return Collections.singletonList(new SimpleGrantedAuthority(userPermission.toString()));
    }
}
