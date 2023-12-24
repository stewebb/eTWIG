package net.grinecraft.etwig.interceptor;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.util.NameUtils;

@Component
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		// Check session first!
		HttpSession session = request.getSession(false);
		if(session == null) {
			return;
		}
		
		// Session has already set up, just return the value.
		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser!= null) {
			//modelAndView.addObject("user", sessionUser);
			addUserInfo(modelAndView, sessionUser);
			//System.out.println("Session");
			return;
		}
		
		// Check logged in or not
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
			return;
		}
		
		// Get logged in email
    	String email = authentication.getName();
    	if(email == null) {
    		return;
    	}
    	
    	// Get user object
	    User user = userRepository.findByEmail(email);
	    if(user == null) {
	    	return;
	    }
	    
	    //System.out.println("New");
	    // And store into session
	    session.setAttribute("user", user);
	    //modelAndView.addObject("user", user);
	    addUserInfo(modelAndView, user);
	}
	
	private void addUserInfo(ModelAndView modelAndView, User user) {
		
		if(modelAndView == null || user == null) {
			return;
		}
		
		LinkedHashMap<String, Object> userInfo = new LinkedHashMap<String, Object>();
		userInfo.put("userId", user.getId());
		userInfo.put("username", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		
		modelAndView.addObject("user", userInfo);
	}
}
