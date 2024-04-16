/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The handler after authentication success.
 	*/

package net.grinecraft.etwig.handler;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.grinecraft.etwig.util.UserSession;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    
    @Autowired
    private UserSession userSession;
    
	/**
	 * Put the user-related information into session after user logged in, then redirect user back.
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException 
	 */
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
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
	    userSession.setEmail(email);
		userSession.put();
	    	    
	    DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
	    String targetUrl = "/";
	    
        if (savedRequest != null) {
        	targetUrl = savedRequest.getRedirectUrl();   
        }
        
        // Redirect back
        redirectStrategy.sendRedirect(request, response, targetUrl);
	}
}