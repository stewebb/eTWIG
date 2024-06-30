/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The handler after authentication success.
 	*/

package net.etwig.webapp.handler;

import java.io.IOException;

import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles the successful authentication event by initializing the user session and redirecting the user
 * to either a saved request URL or the home page if no saved request is found.
 * <p>
 * This component is responsible for post-authentication actions such as session initialization
 * and redirect handling. It uses {@link DefaultRedirectStrategy} for managing redirections
 * and {@link HttpSessionRequestCache} for retrieving the originally requested URL before authentication.
 * </p>
 *
 * @Component Indicates that an annotated class is a "component". Such classes are considered as candidates
 * for auto-detection when using annotation-based configuration and classpath scanning.
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    
    @Autowired
    private UserSessionService userSessionService;

	/**
	 * Called when a user has been successfully authenticated.
	 * This method checks if the authentication object is valid and authenticated. If so,
	 * it initializes the user's session with their email and redirects them to their original destination
	 * or to the default page if no saved request is found.
	 * <p>
	 * The redirection strategy can be customized through the {@link DefaultRedirectStrategy} component.
	 * </p>
	 *
	 * @param request The request from which the authentication attempt was initiated.
	 * @param response The response, where the redirect will be written.
	 * @param authentication The authentication token which can be used to obtain the authenticated user's details.
	 * @throws IOException If an input or output exception occurs during redirect.
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
	    userSessionService.initializeSession(email);
	    	    
	    DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
	    String targetUrl = "/";
	    
        if (savedRequest != null) {
        	targetUrl = savedRequest.getRedirectUrl();   
        }
        
        // Redirect back
        redirectStrategy.sendRedirect(request, response, targetUrl);
	}
}