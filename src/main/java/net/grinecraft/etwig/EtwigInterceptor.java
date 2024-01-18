/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The interceptor, which allows variables can be shared across the whole application.
 	*/

package net.grinecraft.etwig;

import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.config.ConfigFile;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;

@Component
public class EtwigInterceptor implements HandlerInterceptor{
	
	@Autowired
	ConfigFile config;
	
	/**
	 * Add data that shared across the application.
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView == null) {
			return;
		}

		/**
		 * User-related data.
		 */
		
		// Check session first!
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			// Only proceed of session already set up
			User user = (User) session.getAttribute("user");
			if (user != null) {
				
				// Get the in-session user info.
				LinkedHashMap<String, Object> userInfo = new LinkedHashMap<String, Object>();
				userInfo.put("userId", user.getId());
				userInfo.put("username", null);
				
				// Put user info into Interceptor
				modelAndView.addObject("user", userInfo);
			}
			
			Set<UserRole> userRoles = (Set<UserRole>) session.getAttribute("role");
			if(userRoles != null) {
				modelAndView.addObject("role", userRoles);
			}
		}	
		
		/**
		 * Application information.
		 */
		
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<String, Object>();
		appInfo.put("appName", config.getAppName());
		appInfo.put("appVersion", "1.0");
		appInfo.put("appOwner", config.getAppOwner());
		modelAndView.addObject("app", appInfo);				
	}
}
