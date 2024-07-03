/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for the dashboard (main) page.
 	*/

package net.etwig.webapp.controller.page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/")
public class HomePageController {

	/**
	 * Handles the root URL ("/") of the application and redirects users based on their authentication status.
	 * Authenticated users are redirected to the home page, while non-authenticated visitors are redirected to the public TWIG page.
	 * <p>
	 * This method performs an authentication check using Spring Security's context to determine if the current user is authenticated
	 * and not recognized as an "anonymousUser". Based on this check, it redirects to the appropriate internal page.
	 *
	 * @location / The endpoint for this controller method.
	 * @permission Accessible to all users, including unauthenticated visitors, as it serves as the entry point to the application,
	 *             routing users based on their authentication status.
	 * @return A redirection string pointing either to the internal home page for authenticated users or to the public TWIG page
	 *         for visitors without authentication.
	 */

	@GetMapping("/")
	public String root(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Check if user is authenticated and not anonymous
		return (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) ? "redirect:/home.do" : "redirect:/twig/index.do";
	}

	/**
	 * Serves the homepage of the application. This method is responsible for handling the initial request to the root of the
	 * application, directing users to the homepage which may include quick links and essential information for navigating the application.
	 * <p>
	 * The method currently does not manipulate the model or handle exceptions specifically, but includes the potential to throw
	 * exceptions which should be managed appropriately, perhaps by a global exception handler or specific error handling mechanisms.
	 *
	 * @return The view name 'home' which corresponds to the homepage template of the application.
	 * @throws Exception Indicates that there is a potential for this method to throw exceptions, which could be related to
	 *                   various aspects of the application's operations not directly handled within this method.
	 * @location /home.do
	 * @permission All logged-in users.
	 */

	@RequestMapping("/home.do")
	public String home() throws Exception {
		return "home";
	}
}
