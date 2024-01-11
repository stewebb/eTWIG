/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The controller for user authentication - mainly login and logout.
	*/

package net.grinecraft.etwig.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	/**
	 * The login page.
	 * @param model
	 * @return
	 * @throws Exception
	 * @Permissions PUBLIC ACCESS
	 */
	
    @GetMapping("/user/login")
    public String login(Model model) throws Exception {
    	
		return "user/login";
    }
}