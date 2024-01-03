/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The custom remember me service.
 */

package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.config.WebConfig;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.UserRepository;

@Component
public class RememberMeService extends TokenBasedRememberMeServices {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	public RememberMeService(UserDetailsService userDetailsService, WebConfig config) {
		super(config.getCookieKey(), userDetailsService);
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
		
		UserDetails userDetails = super.processAutoLoginCookie(cookieTokens, request, response);
		User user = userRepository.findByEmail(userDetails.getUsername());
		   
		if(user == null) {
			throw new IllegalStateException("User authentication successfully, but the information cannot be found in the database.");
		 }
		
		HttpSession session = request.getSession(true);
		LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(user.getId());
		session.setAttribute("user", user);
		session.setAttribute("portfolio", myPortfolios);
	        
	    return userDetails;
	 }
}
