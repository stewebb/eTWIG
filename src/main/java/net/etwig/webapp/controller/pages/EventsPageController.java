/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.etwig.webapp.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.etwig.webapp.dto.events.GraphicsRequestEventInfoDTO;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.GraphicsRequestService;
import net.etwig.webapp.services.OptionService;
import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.services.PropertyService;
import net.etwig.webapp.services.UserRoleService;

@Controller
@RequestMapping("/events")
public class EventsPageController {

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
	 * The root location, redirect to index page.
	 * @location /events/
	 * @permission All logged in users
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:/index.do";
	}

	/**
	 * Event index page.
	 * @location /events/index.do
	 * @permission All logged in users
	 */

	@GetMapping("index.do")
	public String index(Model model){

		// TODO Make an index page for events
		return null;
	}
	
	/**
	 * Event calendar page.
	 * @location /events/calendar.do
	 * @permission All logged in users
	 */
	
	@GetMapping("/calendar.do")
	public String calendar(Model model){
		model.addAttribute("portfolios", portfolioService.getAllPortfolioList());
		return "events/calendar";
	}
	
	/**
	 * Add/View/Edit event page.
	 * @location /events/edit.do
	 * @permission All logged in users
	 */
	
	@RequestMapping("/edit.do")
	public String edit(Model model){

		// TODO Add, edit share a template, but different url
		// TODO Add a "view only" page, then set the permission of old pages to "event manager only"
		//System.out.println(optionService.findAllGroupByProperties());
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());	
		return "events/edit";
	}

	/**
	 * Graphics request page
	 * @location /events/graphics.do
	 * @permission Event managers only
	 */

	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@GetMapping("/graphics.do")
	public String graphics(Model model, @RequestParam Long eventId) throws Exception{

		// TODO Incorporate the banner request page into event page.
		
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

	/**
	 * Event (bulky) import page, which allows users to import multiple events simultaneously (via an EXCEL/ODS file).
	 * @location /events/import.do
	 */
	@GetMapping("/import.do")
	public String importEvent(Model model){
		//model.addAttribute("portfolios", portfolioService.getAllPortfolioList());
		return "events/import";
	}
}
