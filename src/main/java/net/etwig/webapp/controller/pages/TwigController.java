/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for public TWIG.
 	*/

package net.etwig.webapp.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.etwig.webapp.services.PortfolioService;

@Controller
public class TwigController {
	
	@Autowired
	PortfolioService portfolioService;
	
	@RequestMapping("/twig")  
	public String twig(Model model) throws Exception{
		System.out.println(portfolioService.getPortfolioListBySeparatedCalendar(true));
		model.addAttribute("portfolioSeparatedCalendar", portfolioService.getPortfolioListBySeparatedCalendar(true));
		return "twig";
	}

}
