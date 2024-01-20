/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.grinecraft.etwig.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.dto.UserDTO;
import net.grinecraft.etwig.handler.CustomUserDetails;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.PortfolioRepository;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.repository.UserRoleRepository;

@Service
public class UserRoleService implements UserDetailsService{
	
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
	private HttpSession session;
    
    public Set<Portfolio> getMyPortfolios(){
		if (userRoleRepository == null) {
	        return null;
	    }
		
		// Get my roles, then get my portfolios
		UserDTO user = (UserDTO) session.getAttribute("user");
		return userRoleRepository.findByUserId(user.getId()).stream().map(UserRole::getPortfolio).collect(Collectors.toSet());
	}
    
    public Set<PositionDTO> getMyPositions(){
    	if (userRoleRepository == null) {
	        return null;
	    }
    	UserDTO user = (UserDTO) session.getAttribute("user");
    	return userRoleRepository.getPositionsByUserId(user.getId());
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

}
