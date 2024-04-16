/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.etwig.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.events.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.GraphicsRequestService;
import net.grinecraft.etwig.services.OptionService;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.services.PropertyService;
import net.grinecraft.etwig.services.UserRoleService;

@Controller
@RequestMapping("/events")
public class EventsController {

	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private OptionService optionService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	/**
	 * Event calendar page.
	 */
	
	@GetMapping("/calendar")  
	public String calendar(Model model){
		model.addAttribute("portfolios", portfolioService.getAllPortfolioList());
		return "events/calendar";
	}
	
	/**
	 * Add/Edit event page.
	 */
	
	//@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@RequestMapping("/edit")  
	public String edit(Model model){
		System.out.println(optionService.findAllGroupByProperties());
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());	
		return "events/edit";
	}
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@GetMapping("/graphics")  
	public String graphics(Model model, @RequestParam Long eventId) throws Exception{
		
		// Get event info and existence check.
		GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);
		if(event == null) {
			model.addAttribute("reason", "Event with id=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}

		model.addAttribute("eventInfo", event);
		model.addAttribute("requestInfo",graphicsRequestService.getRequestsByEvent(eventId));

		// Get the number of requests of this event.
		model.addAttribute("count", graphicsRequestService.countByEventId(eventId));
		
		// Edit permission check
		model.addAttribute("editPermission", eventService.eventEditPermissionCheck(event.getPortfolio()));
		model.addAttribute("myPortfolios", userRoleService.getMyPortfolios());
		return "events/graphics";
	}
	
	@GetMapping("/import")  
	public String importEvent(Model model){
		//model.addAttribute("portfolios", portfolioService.getAllPortfolioList());
		return "events/import";
	}
}
