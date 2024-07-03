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

/**
 * Custom authentication provider that allows authentication based on the username alone without verifying a password.
 * This provider should be used with caution and only in scenarios where password authentication is explicitly not required.
 * It is specifically designed to work with {@link CustomToken} to ensure it's only invoked under controlled circumstances.
 *
 * <p>This provider is intended for use cases such as internal applications or demos where password security can be bypassed
 * safely and controlled through other means.</p>
 *
 * @see AuthenticationProvider
 * @see CustomToken
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * Attempts to authenticate a given {@link Authentication} object if it is an instance of {@link CustomToken}.
     * This method bypasses password checks and authenticates the user based solely on the username present in the token.
     *
     * @param authentication the {@link Authentication} object to authenticate, expected to be an instance of {@link CustomToken}.
     * @return a fully authenticated object including authorities if the authentication is successful.
     * @throws AuthenticationException if authentication fails or the input is not supported.
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof CustomToken)) {
            return null; // This provider does not support the given Authentication type
        }

        String username = authentication.getName();
        UserDetails userDetails;
        try {
            userDetails = userRoleService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Indicates whether this provider supports a given authentication type, which is {@link CustomToken} in this case.
     *
     * @param authentication the class of the {@link Authentication} object that is being checked for support.
     * @return true if this provider supports the specified class, false otherwise.
     */

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomToken.class.isAssignableFrom(authentication);
    }
}
