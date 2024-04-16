package net.etwig.webapp.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private HttpSession session;
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //res.setContentType("application/json;charset=UTF-8");
        //res.setStatus(403);
        //res.getWriter().print("Access Denied!");
    	//response.sendRedirect("/user/login");
    	
    	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	//System.out.println(auth);
    	
    	// Not logged in.
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/user/login");
        	//super.commence(request, response, authException);
        } 
        
        // Logged in but with no access to the given resource.
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied - You don't have permission to access this resource");
        }
        
        
    }

	
}