package net.etwig.webapp.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class ApiLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public ApiLoginAuthenticationFilter() {
        super("/api/login"); // URL that triggers this filter
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        ApiAuthenticationToken authRequest = new ApiAuthenticationToken(username + ":" + email, null, null);
        return getAuthenticationManager().authenticate(authRequest);
    }
}