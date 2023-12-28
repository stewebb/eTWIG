/**
 * eTWIG - The event and banner management software for residential halls and student unions.
 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.services.PortfolioService;
import net.grinecraft.etwig.services.UserRoleService;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class EditController {

	@Autowired
	EventService eventService;
	
	@Autowired
	PortfolioService portfolioService;
	
	@Autowired
	UserRoleService userRoleService;

	@RequestMapping("/events/edit")  
	public String edit(HttpSession session, Model model, @RequestParam String eventId, @RequestParam String embedded) throws Exception{
		Long id = null;
		
		model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		model.addAttribute("isEdit", true);
		model.addAttribute("navbar", NavBar.OTHER);
		
		// Check Invalid eventId. (Not a Long number)
		id = NumberUtils.safeCreateLong(eventId);
		if(id == null || id <0) {
			model.addAttribute("reason", "eventId=" + eventId + " is invalid. It must be a positive Integer.");
			return "_errors/custom_error";
		}
			
		// Check eventId can be founded in database or not
		LinkedHashMap<String, Object> event = eventService.findById(id, true);
		if(Boolean.TRUE.equals(event.get("exists"))) {
			model.addAttribute("eventId", id);
			model.addAttribute("eventDetails", event);
			return "events/edit";
		}
				
		// Event cannot be found, 
		else {
			model.addAttribute("reason", "eventId=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}		
			
		
		
		// Logged in user (me)
        //User my = (User) session.getAttribute("user");
       
        // Get myColleagues: Other users who have the same portfolio with me
        //LinkedHashMap<Long, User> myColleagues = userRoleService.getUsersByPortfolioId(id);
        
        // Get myPortfolios: Other portfolios that I have.
        //LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(my.getId());
        
        // Extract the keys
        //Set<Long> myPortfolioIds = myPortfolios.keySet();
        //LinkedHashMap<Long, Portfolio> allPortfolios = portfolioService.getPortfolioList();
   
        // And get the remaining portfolios
        //for (Long key : myPortfolioIds) {
        //	allPortfolios.remove(key);
        //}        
        
		//model.addAttribute("eventId", eventId);
		//model.addAttribute("mode", mode);
        //model.addAttribute("navbar", NavBar.EDIT_EVENT);
        
        //if(id != null) {
        //	model.addAttribute("myColleagues", myColleagues);
        //}
        
        //if (my != null) {
        //	model.addAttribute("myPortfolios", myPortfolios);
        //	model.addAttribute("remainingPortfolios", allPortfolios);
        //}
		//return page;
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
	public String add(HttpSession session, Model model, @RequestParam(required = false) String embedded) throws Exception{
		
		// Logged in user (me)
		User my = (User) session.getAttribute("user");
		
		// Get myPortfolios: All portfolios that I have.
		LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(my.getId());
		
		model.addAttribute("isEdit", false);
        model.addAttribute("navbar", NavBar.ADD_EVENT);
        model.addAttribute("myPortfolios", myPortfolios);
        model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		return "events/add";
	
	}
}
