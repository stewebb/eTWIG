/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.grinecraft.etwig.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.dto.PositionWithoutEmailDTO;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.handler.CustomUserDetails;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.repository.UserRoleRepository;

@Service
public class UserRoleService implements UserDetailsService{
	
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private HttpSession session;
    
    public Set<Portfolio> getMyPortfolios(){
		UserDTO currentUser = (UserDTO) session.getAttribute("user");
		return userRoleRepository.findByUserId(currentUser.getId()).stream().map(UserRole::getPortfolio).collect(Collectors.toSet());
	}
    
    public Set<PositionWithoutEmailDTO> getMyPositions(){
    	UserDTO currentUser = (UserDTO) session.getAttribute("user");
    	Set<UserRole> myRoles = userRoleRepository.findByUserId(currentUser.getId());
    	return myRoles.stream().map(PositionWithoutEmailDTO::new).collect(Collectors.toSet());
    	
    	//Set<PositionWithoutEmailDTO> myPositions = new HashSet();
    	//for(UserRole r : myRoles) {
    	//	myPositions.add(new PositionWithoutEmailDTO(r));
    	//}
    	//return myPositions;
    }
    
    public UserRole findById(@NonNull Long userRoleId) {
        return userRoleRepository.findById(userRoleId).orElse(null);
    }
    
    @SuppressWarnings("null")
	public User getMyDetails() {
    	UserDTO currentUser = (UserDTO) session.getAttribute("user");
    	return userRepository.findById(currentUser.getId()).orElse(null);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
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
