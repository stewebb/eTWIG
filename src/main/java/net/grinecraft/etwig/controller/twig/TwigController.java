/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
 * @license: MIT
 * @author: Steven Webb [xiaoancloud@outlook.com]
 * @website: https://etwig.grinecraft.net
 * @function: The controller for public TWIG.
 */

package net.grinecraft.etwig.controller.twig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.config.WebConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class TwigController {
	
	
	/**
	 * The actual TWIG content
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/twig/main")  
	public String content(Model model) throws Exception{
		return "twig/twig";
	}
	
	
}
