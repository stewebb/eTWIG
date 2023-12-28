/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for public TWIG related page.
 */

package net.grinecraft.etwig.controller.twig;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class TwigController {
	
	/**
	 * The main public TWIG page (i.e., offer filters).
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/twig/main")  
	public String twigMain(Model model) throws Exception{
				
		return "twig/main";
	}
	
	/**
	 * The acutal TWIG content
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/twig/content")  
	public String content(Model model) throws Exception{
				
		return "twig/content";
	}
}
