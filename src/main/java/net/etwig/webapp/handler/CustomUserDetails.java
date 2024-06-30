package net.etwig.webapp.handler;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import net.etwig.webapp.model.Role;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;

/**
 * Custom implementation of Spring Security's {@link org.springframework.security.core.userdetails.User} class,
 * encapsulating user details and their roles for authentication and authorization purposes.
 * This class converts a set of user roles into Spring Security's {@link GrantedAuthority} to
 * utilize Spring's built-in security mechanisms.
 * <p>
 * Extends:
 * {@link org.springframework.security.core.userdetails.User}
 */

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    /**
     * Serial version unique identifier for {@link CustomUserDetails} class.
     * It is used during the deserialization to verify that the sender and receiver of a serialized
     * object have loaded classes for that object that are compatible with respect to serialization.
     */

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@code CustomUserDetails} with the specified user and their roles.
     * This constructor initializes the superclass with the user's email and password, and
     * converts user roles into Spring Security's authorities.
     *
     * @param user the user whose details are to be represented.
     * @param userRoles the roles of the user, which are converted to authorities.
     */

	public CustomUserDetails(User user, Set<UserRole> userRoles) {
        super(user.getEmail(), user.getPassword(), groupsToAuthorities(userRoles));
    }

    /**
     * Converts a set of user roles to a collection of Spring Security's {@link GrantedAuthority}.
     * Each role in the set is examined, and depending on the role's permissions, a corresponding
     * {@link GrantedAuthority} is added to the set of authorities.
     *
     * @param userRoles the set of user roles to be converted into authorities.
     * @return a collection of {@link GrantedAuthority} representing the user's roles.
     */

     public static Collection<? extends GrantedAuthority> groupsToAuthorities(Set<UserRole> userRoles) {
    	 Set<GrantedAuthority> authorities = new HashSet<>();
    	 
    	 // Get the authorities by role.
         for (UserRole userRole : userRoles) {
             Role role = userRole.getRole();
             
             // Administrator
             if (role.isAdminAccess()) {
                 authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
             }
             
             // Event Manager
             if (role.isEventsAccess()) {
                 authorities.add(new SimpleGrantedAuthority("ROLE_EVENTS"));
             }
             
             // Graphics Manager
             if(role.isGraphicsAccess()) {
            	 authorities.add(new SimpleGrantedAuthority("ROLE_GRAPHICS"));
             }
         }
         
         return authorities;
    }
}