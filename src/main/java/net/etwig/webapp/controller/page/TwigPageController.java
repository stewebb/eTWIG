/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for public TWIG.
 	*/

package net.etwig.webapp.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.etwig.webapp.services.PortfolioService;


@Controller
@RequestMapping("/twig")
public class TwigPageController {

	/**
	 * The root location, redirect to index page.
	 * @location /twig/
	 * @permission All users, including visitors
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/twig/index.do";
	}
	
	@Autowired
	PortfolioService portfolioService;

	/**
	 * The public TWIG page
	 * @location /twig/index.do
	 * @permission All users, including visitors
	 */
	
	@RequestMapping("/index.do")
	public String index(Model model) throws Exception{
		System.out.println(portfolioService.getPortfolioListBySeparatedCalendar(true));
		model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));
		return "twig";
	}

}
