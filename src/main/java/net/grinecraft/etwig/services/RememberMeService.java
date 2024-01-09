/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The custom remember me service.
 	*/

package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.config.ConfigFile;
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
	
	// Remember Me for 90 days. (60*60*90)
    public static final int TOKEN_VALIDITY_SECONDS = 7776000;
	
	@Autowired
	public RememberMeService(UserDetailsService userDetailsService, ConfigFile config) {
		super(config.getCookieKey(), userDetailsService);
	}

	/**
	 * Put the user info into session if the user is logged in via "remember me".
	 * @param cookieTokens
	 * @param request
	 * @param response
	 */
	
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
	
	/**
	 * Set the custom cookie expire time.
	 */
	
	@Override
    protected int calculateLoginLifetime(HttpServletRequest request, Authentication authentication) {
        return TOKEN_VALIDITY_SECONDS;
    }
}
