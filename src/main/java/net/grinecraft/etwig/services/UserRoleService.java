/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all user-role options.
	*/

package net.grinecraft.etwig.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    
    /**
     * Get all Portfolios for a user.
     * @param userId
     * @return The LinkedHashMap of portfolios.
     */
    
    public LinkedHashMap<Long, Portfolio> getPortfoliosByUserId(Long userId) {
    	
    	/*
        List<UserRole> roles = userRoleRepository.findByIdUserId(userId);
        LinkedHashMap<Long, Portfolio> portfoliosMap = new LinkedHashMap<>();

        for (UserRole role : roles) {s
            Long PortfolioId = role.getId().getPortfolioId();
            portfoliosMap.computeIfAbsent(PortfolioId, id -> portfolioRepository.findById(id).orElse(null));
        }
*/
       // return portfoliosMap;
    	
    	return null;
    }

    /**
     * Get all users that works in the portfolio.
     * @param portfolioId
     * @return The LinkedHashMap of users.
     */
    
    public LinkedHashMap<Long, User> getUsersByPortfolioId(Long portfolioId) {
    	
    	/*
        List<UserRole> roles = userRoleRepository.findByIdPortfolioId(portfolioId);
        LinkedHashMap<Long, User> usersMap = new LinkedHashMap<>();

        for (UserRole role : roles) {
            Long userId = role.getId().getUserId();
            usersMap.computeIfAbsent(userId, id -> userRepository.findById(id).orElse(null));
        }

        return usersMap;
        
        */
    	
    	return null;
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
