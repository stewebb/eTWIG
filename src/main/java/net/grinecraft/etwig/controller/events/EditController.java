/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.grinecraft.etwig.controller.events;

import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.services.EventOptionService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.OptionService;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.services.PropertyService;
import net.grinecraft.etwig.services.UserRoleService;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.NumberUtils;

@Controller
public class EditController {

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
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/events/{action}")  
	public String edit(@PathVariable String action, HttpSession session, Model model, @RequestParam String eventId, @RequestParam String embedded) throws Exception{
		Long id = null;
		
		model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		addCommonAtttributes(model);
		
		// Check Invalid eventId. (Not a Long number)
		id = NumberUtils.safeCreateLong(eventId);
		if(id == null || id <0) {
			model.addAttribute("reason", "eventId=" + eventId + " is invalid. It must be a positive Integer.");
			return "_errors/custom_error";
		}
			
		// Check eventId can be founded in database or not
		LinkedHashMap<String, Object> event = eventService.findById(id);
		if(Boolean.TRUE.equals(event.get("exists"))) {
			model.addAttribute("eventId", id);
			model.addAttribute("eventDetails", event);
			
			Set<Long> myPortfolios = ((LinkedHashMap<Long, Portfolio>) session.getAttribute("portfolio")).keySet();
			Long eventPortfolio = (Long) ((LinkedHashMap<String, Object>) event.get("portfolio")).get("id");
			model.addAttribute("editPermission", myPortfolios.contains(eventPortfolio));
			
			eventOptionService.getOptionsByEvent(id);
			
			// The action is either edit or delete.
			return "edit".equals(action) ? "events/edit" : "events/delete"; 
		}
				
		// Event cannot be found, TODO Permission check when edit.delete, on backend
		else {
			model.addAttribute("reason", "eventId=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}		
			
	}
	
	/**
	 * Add event page.
	 * @param session
	 * @param model
	 * @param embedded True the page is embedded  into a frame. Otherwise the page is standalone.
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/events/add")  
	public String add(Model model, @RequestParam(required = false) String embedded) throws Exception{
        model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
        addCommonAtttributes(model);
        
		return "events/add";
	}
	
	private void addCommonAtttributes(Model model) {
		model.addAttribute("allProperties", propertyService.findAll());		
        model.addAttribute("allOptions", optionService.findAllGroupByProperties());		
	}
}
