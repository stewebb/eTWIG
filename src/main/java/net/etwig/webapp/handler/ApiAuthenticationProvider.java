package net.etwig.webapp.handler;

import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

import static net.etwig.webapp.handler.CustomUserDetails.groupsToAuthorities;

public class ApiAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiAuthenticationToken token = (ApiAuthenticationToken) authentication;
        String[] userDetails = ((String) token.getPrincipal()).split(":");

        String username = userDetails[0];
        String email = userDetails[1];

        // Implement your logic to validate username and email here
        if (isValidUser(username, email)) {
            User user = userRepository.findByEmail(email);
            Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

            // Create a list of GrantedAuthorities, etc.
            return new ApiAuthenticationToken(username, null, groupsToAuthorities(userRoles));
        } else {
            throw new BadCredentialsException("Invalid username/email combination");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isValidUser(String username, String email) {
        // Validation logic here
        return true;
    }
}
