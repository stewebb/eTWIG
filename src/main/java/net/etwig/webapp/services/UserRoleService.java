/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.etwig.webapp.services;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import net.etwig.webapp.dto.LoggedInUserInfoDTO;
import net.etwig.webapp.dto.LoggedInUserPositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.dto.PositionWithoutEmailDTO;
import net.etwig.webapp.dto.user.UserDTO;
import net.etwig.webapp.handler.CustomUserDetails;
import net.etwig.webapp.model.Portfolio;
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
	private HttpSession session;

    /**
     * Retrieves the {@code LoggedInUserPositionDTO} object from the session.
     * This method fetches the position information encapsulated in {@code LoggedInUserPositionDTO}
     * from the session attribute named "position".
     *
     * @return the {@code LoggedInUserPositionDTO} object from the session.
     * @throws ClassCastException if the object retrieved from the session cannot be cast to {@code LoggedInUserPositionDTO}.
     */

    private LoggedInUserPositionDTO getCurrentPosition() {
        return (LoggedInUserPositionDTO) session.getAttribute("position");
    }

    /**
     * Retrieves the current role ID of the logged-in user.
     * This method uses {@code getCurrentPosition()} to extract the user's position information from the session
     * and returns the current role ID associated with it.
     *
     * @return the current role ID of the logged-in user as a {@code Long}.
     * @throws NullPointerException if the session does not contain position data.
     */

    public Long getMyLoggedInPosition(){
        return getCurrentPosition().getMyCurrentRole();
    }

    /**
     * Retrieves all role positions of the logged-in user.
     * This method uses {@code getCurrentPosition()} to obtain the user's position information from the session
     * and returns a map of role IDs and their corresponding names.
     *
     * @return a {@code HashMap<Long, String>} representing all role positions.
     * @throws NullPointerException if the session does not contain position data.
     */

    public HashMap<Long, String> getMyPositions(){
        return getCurrentPosition().getMyRoles();
    }



    public Set<Portfolio> getMyPortfolios(){
		UserDTO currentUser = (UserDTO) session.getAttribute("user");
		return userRoleRepository.findByUserId(currentUser.getId()).stream().map(UserRole::getPortfolio).collect(Collectors.toSet());
	}

    /*
    public Set<PositionWithoutEmailDTO> getMyPositions(){
    	LoggedInUserInfoDTO currentUser = (LoggedInUserInfoDTO) session.getAttribute("user");
    	Set<UserRole> myRoles = userRoleRepository.findByUserId(currentUser.getId());
    	return myRoles.stream().map(PositionWithoutEmailDTO::new).collect(Collectors.toSet());
    	
    	//Set<PositionWithoutEmailDTO> myPositions = new HashSet();
    	//for(UserRole r : myRoles) {
    	//	myPositions.add(new PositionWithoutEmailDTO(r));
    	//}
    	//return myPositions;
    }

     */
    
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
