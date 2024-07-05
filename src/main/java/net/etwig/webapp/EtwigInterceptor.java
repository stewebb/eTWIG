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
import net.etwig.webapp.util.Endpoints;
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

	@Autowired
	private Endpoints endpoints;

	/**
	 * Post-processes each HTTP request to enrich the ModelAndView object with additional attributes.
	 * This method is called after the request handler method has executed and before the view is rendered.
	 * It adds essential user and application data to the ModelAndView, making it available for every view within the application.
	 *
	 * <p>Attributes added include:</p>
	 * <ul>
	 *   <li>User basic information, permissions, and position if the user session is valid.</li>
	 *   <li>Application-wide accessible endpoints from {@link Endpoints} enumeration.</li>
	 *   <li>General application information such as name, version, and owner.</li>
	 * </ul>
	 *
	 * @param request The current HTTP request.
	 * @param response The current HTTP response.
	 * @param handler The handler (or {@link Controller}) that handled the request. Typically not used directly within this method.
	 * @param modelAndView The ModelAndView object that the handler method returned, which may be modified before it is rendered.
	 *                     If null, the method will exit early without making any modifications.
	 * @throws Exception If there's an issue during session validation or while adding attributes. This could be due to session
	 *                   expiration, data access problems, or misconfigurations in the application's context.
	 */
	
	@Override
	public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {

		// Early exit if there is no model and view to modify.
		if(modelAndView == null) {
			return;
		}

		// Adding current logged-in user information to the model if a valid session exists.
		CurrentUserDTOWrapper wrapper = userSessionService.validateSession();
		if(wrapper != null){
			modelAndView.addObject("userBasicInfo", wrapper.getBasicInfo());
			modelAndView.addObject("userPermission", wrapper.getPermission());
			modelAndView.addObject("userPosition", wrapper.getPosition());
		}

		// Adding application endpoints for global access in views.
		modelAndView.addObject("ENDPOINTS", endpoints);

		// Compile and add application information.
		LinkedHashMap<String, Object> appInfo = new LinkedHashMap<>();
		appInfo.put("appName", config.getAppName());

		appInfo.put("appVersion", "1.3.7");
		appInfo.put("appOwner", config.getAppOwner());
		modelAndView.addObject("app", appInfo);
	}
}
