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
		if(session == null) {
			return;
		}
		
		// Session hasn't already set up
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return;
		}
		
		LinkedHashMap<String, Object> userInfo = new LinkedHashMap<String, Object>();
		userInfo.put("userId", user.getId());
		userInfo.put("username", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<String, Object>();
		appInfo.put("appName", "eTWIG Administration Portal");
		appInfo.put("appVersion", "1.0");
		
		
		modelAndView.addObject("user", userInfo);
		modelAndView.addObject("app", appInfo);
				
	}
}
