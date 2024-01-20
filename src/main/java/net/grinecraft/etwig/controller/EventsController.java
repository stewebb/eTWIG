/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.grinecraft.etwig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import net.grinecraft.etwig.services.OptionService;
import net.grinecraft.etwig.services.PropertyService;

@Controller
@RequestMapping("/events")
public class EventsController {

	@Autowired
	PropertyService propertyService;
	
	@Autowired
	OptionService optionService;
	
	/**
	 * Event calendar page.
	 */
	
	@GetMapping("/calendar")  
	public String calendar(Model model){
		return "events/calendar";
	}
	
	/**
	 * Add/Edit event page.
	 */
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@RequestMapping("/edit")  
	public String edit(Model model){
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());	
		return "events/edit";
	}
}
