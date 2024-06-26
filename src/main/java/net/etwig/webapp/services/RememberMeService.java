/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The custom remember me service.
 	*/

package net.etwig.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.etwig.webapp.config.ConfigFile;

@Component
public class RememberMeService extends TokenBasedRememberMeServices {
		
	// Remember Me for 90 days. (60*60*90)
    public static final int TOKEN_VALIDITY_SECONDS = 7776000;
	
	@Autowired
	public RememberMeService(UserDetailsService userDetailsService, ConfigFile config) {
		super(config.getCookieKey(), userDetailsService);
	}
	
	@Autowired
	private UserSessionService userSessionService;

	/**
	 * Processes the auto-login cookie and initializes a session with user details if the user is authenticated via "remember me".
	 * This method overrides the standard cookie processing to add session initialization logic after the auto-login is confirmed.
	 * <p>
	 * Upon successful auto-login, this method retrieves the user details using the parent class's implementation,
	 * then initializes a new session for the user through the {@code userSessionService}. This ensures that any necessary
	 * user-specific data is loaded into the session right after the authentication via cookie, enhancing the user experience
	 * by making the system immediately aware of the user's context.
	 *
	 * @param cookieTokens the tokens extracted from the auto-login cookie which are used to authenticate the user and obtain their details.
	 *                     These tokens typically include credentials and expiry information.
	 * @param request the {@code HttpServletRequest} in which the auto-login cookie was presented.
	 * @param response the {@code HttpServletResponse} where any necessary responses or new cookies might be set.
	 * @return {@code UserDetails} object containing the authenticated user's details.
	 *         This user detail is then used to perform further operations like session initialization.
	 * @throws IllegalArgumentException if the cookieTokens are invalid or if the authentication process fails.
	 */
	
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
		UserDetails userDetails = super.processAutoLoginCookie(cookieTokens, request, response);
		userSessionService.initializeSession(userDetails.getUsername());
	    return userDetails;
	}

	/**
	 * Calculates and sets the custom expiration time for the login token.
	 * This method overrides the default lifetime calculation for authentication tokens, providing a consistent expiration period
	 * as defined by {@code TOKEN_VALIDITY_SECONDS}. It allows for customization of how long an authentication token remains valid
	 * after a user logs in, which is particularly useful for managing session durations in security-sensitive environments.
	 * <p>
	 * By setting a custom token lifetime, the application can ensure that all tokens expire uniformly,
	 * thus adhering to specific security policies or user experience requirements.
	 *
	 * @param request the {@code HttpServletRequest} in which the login request was made.
	 *                This can be used to extract additional information about the request context if needed.
	 * @param authentication the {@code Authentication} object representing the current user's authentication state.
	 *                       This could be used to adjust the lifetime based on the user's roles or other factors, though
	 *                       it is not utilized in this override.
	 * @return an {@code int} value representing the number of seconds the login token should remain valid.
	 */
	
	@Override
    protected int calculateLoginLifetime(HttpServletRequest request, Authentication authentication) {
        return TOKEN_VALIDITY_SECONDS;
    }
}
