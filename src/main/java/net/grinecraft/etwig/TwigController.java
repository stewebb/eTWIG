/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for public TWIG related page.
 */

package net.grinecraft.etwig;

import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class TwigController {
	
	
	/**
	 * The actual TWIG content
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/twig")  
	public String content(Model model) throws Exception{
				
		return "twig/twig";
	}
}
