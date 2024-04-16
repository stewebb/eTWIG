package net.etwig.webapp.handler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import net.etwig.webapp.model.Role;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

	public CustomUserDetails(User user, Set<UserRole> userRoles) {
        super(user.getEmail(), user.getPassword(), groupsToAuthorities(userRoles));
    }

    private static Collection<? extends GrantedAuthority> groupsToAuthorities(Set<UserRole> userRoles) {
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