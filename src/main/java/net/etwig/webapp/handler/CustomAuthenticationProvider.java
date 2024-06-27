package net.etwig.webapp.handler;

import net.etwig.webapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRoleService userRoleService;  // Service to fetch user details

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        // Attempt to retrieve the user by username
        UserDetails userDetails;
        try {
            userDetails = userRoleService.loadUserByUsername(username);

            //return  new CustomUserDetails()

        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("User not found");
        }

        userDetails.getAuthorities();

        // If the username exists, authenticate the user without checking the password
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
