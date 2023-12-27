/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The controller for user authentication - mainly login and logout.
	 */

package net.grinecraft.etwig.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class LoginController {
	
	/**
	 * The login page.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
    @GetMapping("/user/login")
    public String login(Model model) throws Exception {
    	
        model.addAttribute("navbar", NavBar.LOGIN);
		return "user/login";
    }
}