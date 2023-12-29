/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The intercepter, which allows variables can be shared across the whole application.
 */

package net.grinecraft.etwig;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.util.NameUtils;

@Component
public class EtwigInterceptor implements HandlerInterceptor{
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView == null) {
			return;
		}

		// Check session first!
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			// Only proceed of session already set up
			User user = (User) session.getAttribute("user");
			if (user != null) {
				
				// Get the in-session user info.
				LinkedHashMap<String, Object> userInfo = new LinkedHashMap<String, Object>();
				userInfo.put("userId", user.getId());
				userInfo.put("username", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
				
				// Put user info into Intercepter
				modelAndView.addObject("user", userInfo);
				modelAndView.addObject("portfolio", session.getAttribute("portfolio"));
			}
		}	
		
		// Define some application info
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<String, Object>();
		appInfo.put("appName", "eTWIG Administration Portal");
		appInfo.put("appVersion", "1.0");
		appInfo.put("appCustomer", "Griffin Hall 2024");
		
		// Put application info into Intercepter
		modelAndView.addObject("app", appInfo);				
	}
}
