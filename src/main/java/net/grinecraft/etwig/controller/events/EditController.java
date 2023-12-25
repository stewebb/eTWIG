package net.grinecraft.etwig.controller.events;

import java.util.HashSet;
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
	public String editEvents(HttpSession session, Model model, @RequestParam(required = false) String eventId) throws Exception{
		//NavBar currentNavbar = null;
		String mode = "";
		String page = "";
		Long id = null;
		
		// Case 1: eventId doesn't provided, return a "starter" page.
		if(eventId == null) {
			//currentNavbar = NavBar.EDIT_EVENT;
			mode = "DEFAULT";
			page = "events/edit_starter";
		}
		
		// Case 2-5 : eventId provided.
		else {
			
			// Case 2: Invalid eventId. (Not a Long number)
			id = NumberUtils.safeCreateLong(eventId);
			if(id == null) {
				//currentNavbar = NavBar.EDIT_EVENT;
				mode = "INVALID_EVENT_ID";
				page = "events/edit_starter";
			}
			
			// Case 3-4 : Valid eventId.
			else {
				
				//if(id < 0) {
				//	throw new IllegalArgumentException("EventId must be a positive integer!");
				//}
				
				// Case 3: eventId found in database, edit event
				LinkedHashMap<String, Object> event = eventService.findById(id, true);
				if(Boolean.TRUE.equals(event.get("exists"))) {
					//currentNavbar = NavBar.EDIT_EVENT;
					mode = "EDIT";
					page = "events/edit_main";
					model.addAttribute("eventDetails", event);
				}
				
				// Case 4: eventId cannot be found, 
				else {
					//currentNavbar = NavBar.EDIT_EVENT;
					mode = "EVENT_NOT_FOUND";
					page = "events/edit_starter";
				}		
			}
		}
		
		// Logged in user (me)
        User my = (User) session.getAttribute("user");
       
        // Get myColleagues: Other users who have the same portfolio with me
        LinkedHashMap<Long, User> myColleagues = userRoleService.getUsersByPortfolioId(id);
        
        // Get myPortfolios: Other portfolios that I have.
        LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(my.getId());
        
        // Extract the keys
        Set<Long> myPortfolioIds = myPortfolios.keySet();
        LinkedHashMap<Long, Portfolio> allPortfolios = portfolioService.getPortfolioList();
   
        // And get the remaining portfolios
        for (Long key : myPortfolioIds) {
        	allPortfolios.remove(key);
        }        
        
		model.addAttribute("eventId", eventId);
		model.addAttribute("mode", mode);
        model.addAttribute("navbar", NavBar.EDIT_EVENT);
        
        if(id != null) {
        	model.addAttribute("myColleagues", myColleagues);
        }
        
        if (my != null) {
        	model.addAttribute("myPortfolios", myPortfolios);
        	model.addAttribute("remainingPortfolios", allPortfolios);
        }
		return page;
	}
	
	@RequestMapping("/events/add")  
	public String addEvents(HttpSession session, Model model, @RequestParam(required = false) String eventId) throws Exception{
		
		User my = (User) session.getAttribute("user");
		LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(my.getId());
		
		
		model.addAttribute("mode", "ADD");
        model.addAttribute("navbar", NavBar.ADD_EVENT);
        model.addAttribute("myPortfolios", myPortfolios);
        
		return "events/add";
	
	}
}
