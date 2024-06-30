package net.etwig.webapp.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Custom implementation of {@link AuthenticationEntryPoint} that handles the commencement
 * of the authentication process for unauthorized users.
 * <p>
 * This class is designed to redirect users to a login page if they are not authenticated,
 * or to return a 403 Forbidden status if the user is authenticated but does not have
 * permission to access the requested resource.
 * </p>
 */

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * The session object for obtaining user-specific data.
     */

	@Autowired
	private HttpSession session;

    /**
     * Commences the authentication process by redirecting unauthenticated users to the
     * login page or by sending a 403 error for users who are authenticated but do not
     * have the necessary permissions for the requested resource.
     *
     * @param request The request object containing client request data.
     * @param response The response object where the response data should be written.
     * @param authException The exception that caused the commencement, typically due to
     *                      an unauthenticated user trying to access a restricted resource.
     * @throws IOException if an input or output exception occurs while handling the redirection
     *                     or error reporting.
     */

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	
    	// Not logged in.
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/user/login.do");
        }
        
        // Logged in but with no access to the given resource.
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied.");
        }
    }
}