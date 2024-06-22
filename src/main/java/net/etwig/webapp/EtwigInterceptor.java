/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The interceptor, which allows variables can be shared across the whole application.
 	*/

package net.etwig.webapp;

import java.util.LinkedHashMap;

import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.etwig.webapp.config.ConfigFile;

@Component
public class EtwigInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ConfigFile config;

	@Autowired
	private UserSessionService userSessionService;
	
	/**
	 * Add data that shared across the application.
     */
	
	@Override
	public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView == null) {
			return;
		}

		CurrentUserDTOWrapper wrapper = userSessionService.validateSession();

		if(wrapper != null){
			modelAndView.addObject("userBasicInfo", wrapper.getBasicInfo());
			modelAndView.addObject("userPermission", wrapper.getPermission());
			modelAndView.addObject("userPosition", wrapper.getPosition());

			//System.out.println(wrapper.getPosition());
		}
		//UserSessionService.SessionValidation validatedSession = userSessionService.validateSession();

		/*
		// User-related data.
		// Check session first!
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			// Put user details into session
			CurrentUserBasicInfoDTO currentUserBasicInfoDTO = (CurrentUserBasicInfoDTO) session.getAttribute("user");
			if (currentUserBasicInfoDTO != null) {
				modelAndView.addObject("user", currentUserBasicInfoDTO);
			}
			
			// Put user access into session
			CurrentUserPermissionDTO userAccess = (CurrentUserPermissionDTO) session.getAttribute("access");
			//System.out.println(userAccess);
			if(userAccess != null) {
				modelAndView.addObject("access", userAccess);
			}

			//System.out.println(userRoleService.getMyPositions());

			CurrentUserPositionDTO userPosition = (CurrentUserPositionDTO) session.getAttribute("position");
			if (userPosition != null){
				modelAndView.addObject("position", userPosition);
			}

		}
		*/


		// Application information.
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<String, Object>();
		appInfo.put("appName", config.getAppName());

		appInfo.put("appVersion", "3.3");
		appInfo.put("appOwner", config.getAppOwner());
		modelAndView.addObject("app", appInfo);


	}
}
