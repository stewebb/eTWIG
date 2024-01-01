package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.UserRepository;

@Component
public class RememberMeService extends TokenBasedRememberMeServices {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	

	public RememberMeService( UserDetailsService userDetailsService) {
		super("d", userDetailsService);
		// TODO Auto-generated constructor stub
	}

	@Override
	    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
	        UserDetails userDetails = super.processAutoLoginCookie(cookieTokens, request, response);

	        // Add your logic to store user details in the session
	        // Example: request.getSession().setAttribute("userDetails", userDetails);
	     
	        System.out.println(userDetails);
	     // Get user object
		    User user = userRepository.findByEmail(userDetails.getUsername());
		    //if(user == null) {
		   // 	return;
		    //}
		    HttpSession session = request.getSession(true);
		    System.out.println(session);
		    
		    if(session == null) {
		    	return userDetails;
		    }
		    // Get user portfolios
		    LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(user.getId());
		    
		    // Write objects to session
		    session.setAttribute("user", user);
		    session.setAttribute("portfolio", myPortfolios);
	        
	        
	        return userDetails;
	    }
}
