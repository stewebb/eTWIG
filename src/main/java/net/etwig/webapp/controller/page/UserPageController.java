/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for user authentication - mainly login and logout.
	*/

package net.etwig.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserPageController {

	/**
	 * The root location, redirect to index page.
	 * @location /user/
	 * @permission All logged in users
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/user/index.do";
	}

	/**
	 * The login page
	 * @location /user/login.do
	 * @permission All users, including visitors
	 */
	
    @GetMapping("/login.do")
    public String login(Model model) {
		return "user/login";
    }

	/**
	 * The user profile page.
	 * @location /user/index.do
	 * @permission All logged in users
	 */

	@GetMapping("/index.do")
    public String profile(Model model) {
		return "user/profile";
    }

	/**
	 * The logout page.
	 * @location /user/logout.do
	 * @permission All logged in users
	 */

	@GetMapping("/logout.do")
	public String logout(Model model) {
		//return "user/profile";
		// TODO Logout page
		return null;
	}
}