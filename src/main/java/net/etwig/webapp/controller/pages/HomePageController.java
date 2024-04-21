/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for the dashboard (main) page.
 	*/

package net.etwig.webapp.controller.pages;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/graphics")
public class HomePageController {

	/**
	 * The root location, redirect to index page.
	 * @location /
	 * @permission All users, including visitors
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/index.do";
	}
	
	@RequestMapping("/")  
	public String index(Model model) throws Exception{
		return "dashboard";
	}
}
