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
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/")
public class HomePageController {

	/**
	 * The root location, redirect to the public TWIG page.
	 * @location /
	 * @permission All users, including visitors
	 */

	@GetMapping("/")
	public String root(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Check if user is authenticated and not anonymous
		return (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) ? "redirect:/home.do" : "redirect:/twig/index.do";
	}
	
	@RequestMapping("/home.do")
	public String home(Model model) throws Exception{
		return "home";
	}
}
