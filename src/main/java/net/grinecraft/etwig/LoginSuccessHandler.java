/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The handler after authentication success.
 */

package net.grinecraft.etwig;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.services.UserRoleService;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

		// Check session first!
		HttpSession session = request.getSession(false);
		if(session == null) {
			return;
		}
		
		// Check logged in or not
		if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
			return;
		}
		
		// Get logged in email
    	String email = authentication.getName();
    	if(email == null) {
    		return;
    	}
    	
    	// Get user object
	    User user = userRepository.findByEmail(email);
	    if(user == null) {
	    	return;
	    }
	    
	    // Get user portfolios
	    LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(user.getId());
	    
	    // Write objects to session
	    session.setAttribute("user", user);
	    session.setAttribute("portfolio", myPortfolios);
	    	    
	    DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
	    String targetUrl = "/";
	    
        if (savedRequest != null) {
        	targetUrl = savedRequest.getRedirectUrl();   
        }
        
        // Redirect back
        redirectStrategy.sendRedirect(request, response, targetUrl);

	}
}