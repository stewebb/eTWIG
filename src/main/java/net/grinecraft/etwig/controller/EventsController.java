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
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.EventDetailsDTO;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.OptionService;
import net.grinecraft.etwig.services.PropertyService;

@Controller
@RequestMapping("/events")
public class EventsController {

	@Autowired
	PropertyService propertyService;
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	EventService eventService;
	
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
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@RequestMapping("/_edit")  
	public String editEmbedded(Model model){
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());	
		return "events/edit";
	}
	
	/**
	 * Manage event page.
	 */
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@GetMapping("/manage")  
	public String manage(Model model, @RequestParam Long eventId){

		// Always do null check.
		EventDetailsDTO eventInfo = eventService.findById(eventId);
		if(eventInfo == null) {
			model.addAttribute("reason", "Event with id=" + eventId + " does not exist.");
			return "_errors/custom_error";
		}
		
		model.addAttribute("eventInfo", eventInfo);		
		return "events/manage";
	}
}
