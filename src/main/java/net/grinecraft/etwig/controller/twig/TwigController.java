/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for public TWIG.
 */

package net.grinecraft.etwig.controller.twig;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TwigController {
	
	
	/**
	 * The actual TWIG content
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/twig/main")  
	public String twigMain(Model model) throws Exception{
		return "twig/main";
	}
	
	@RequestMapping("/twig/content")  
	public String content(Model model) throws Exception{
		return "/twig/content";
	}
}
