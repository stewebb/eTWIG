/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.grinecraft.etwig.controller;

import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.services.EventOptionService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.OptionService;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.services.PropertyService;
import net.grinecraft.etwig.services.UserRoleService;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.NumberUtils;

@Controller
@RequestMapping("/events")
public class EventsController {

	@Autowired
	EventService eventService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	EventOptionService eventOptionService;
	
	@Autowired
	HttpSession session;
	
	/**
	 * Event calendar page.
	 */
	
	@GetMapping("/calendar")  
	public String calendar(Model model){
		return "events/calendar";
	}
	
	/**
	 * Add event page.
	 */
	
	@PostAuthorize("hasAuthority('ROLE_EVENTS')")
	@RequestMapping("/add")  
	public String add(Model model){
        addCommonAttributes(model);
		return "events/add";
	}
	
	
	/**
	 * Edit or delete event page.
	 * They have the same validation part, so they are mapped to the same method.
	 * @param action The action of current operation. action := edit | delete
	 * @param session
	 * @param model
	 * @param eventId The id of target event.
	 * @param embedded
	 * @return
	 * @throws Exception
	 * @Permissions Event Manager, admin FULL; OTHERS READ-ONLY
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/events/{action}")  
	public String edit(@PathVariable String action, HttpSession session, Model model, @RequestParam String eventId, @RequestParam String embedded) throws Exception{
		Long id = null;
		
		model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		addCommonAttributes(model);
		
		// Check Invalid eventId. (Not a Long number)
		id = NumberUtils.safeCreateLong(eventId);
		if(id == null || id <0) {
			model.addAttribute("reason", "eventId=" + eventId + " is invalid. It must be a positive Integer.");
			return "_errors/custom_error";
		}
			
		// Check eventId can be founded in database or not
		LinkedHashMap<String, Object> event = null;//eventService.findById(id);
		if(Boolean.TRUE.equals(event.get("exists"))) {
			model.addAttribute("eventId", id);
			model.addAttribute("eventDetails", event);
			model.addAttribute("editPermission", eventService.eventEditPermissionCheck(event));
			
			// The options that selected in this event.
			model.addAttribute("selectedOptions", eventOptionService.getOptionsByEvent(id));
			
			// The action is either edit or delete.
			return "edit".equals(action) ? "events/edit" : "events/delete"; 
		}
				
		// Event cannot be found
		else {
			model.addAttribute("reason", "eventId=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}			
	}

	/**
	 * The attributes that will be added in all pages. 
	 * @param model
	 */
	
	private void addCommonAttributes(Model model) {
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());		
	}
}
