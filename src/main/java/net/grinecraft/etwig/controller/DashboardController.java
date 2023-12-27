/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for the dashboard (main) page.
 */

package net.grinecraft.etwig.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class DashboardController {
	
	/**
	 * The dashboard page
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/")  
	public String home(Model model) throws Exception{
				
		model.addAttribute("navbar", NavBar.DASHBOARD);
		return "dashboard";
	}
}
