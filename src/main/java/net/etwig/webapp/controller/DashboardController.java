/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for the dashboard (main) page.
 	*/

package net.etwig.webapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class DashboardController {
	
	/**
	 * The dashboard page
	 * @param model
	 * @return
	 * @throws Exception
	 * @Permissions ALL FULL
	 */
	
	@RequestMapping("/")  
	public String home(Model model) throws Exception{
		return "dashboard";
	}
}
