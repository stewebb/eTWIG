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

import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.etwig.webapp.config.ConfigFile;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;

@Component
public class EtwigInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ConfigFile config;

	//@Autowired
	//private UserRoleService userRoleService;
	
	/**
	 * Add data that shared across the application.
     */
	
	@Override
	public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView == null) {
			return;
		}

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

		// Application information.
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<String, Object>();
		appInfo.put("appName", config.getAppName());

		appInfo.put("appVersion", "3.3");
		appInfo.put("appOwner", config.getAppOwner());
		modelAndView.addObject("app", appInfo);				
	}
}
