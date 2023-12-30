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
	
	private final Path rootLocation;
	
	
	@Autowired
    public TwigController(WebConfig config) {
        this.rootLocation = Paths.get(config.getRootLocation());
    }
	
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
	
	@RequestMapping(value = "/twig/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> image(@RequestParam String fileName) throws Exception {
		Path file = rootLocation.resolve(fileName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
        	return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
           throw new IllegalArgumentException("The fileName " + fileName + " is invalid.");
        } 
    }
}
