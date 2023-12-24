package net.grinecraft.etwig.controller.events;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.NumberUtils;
import net.grinecraft.etwig.util.type.NavBar;

@Controller
public class EditController {

	@Autowired
	EventService eventService;
	
	@RequestMapping("/events/edit")  
	public String events(Model model, @RequestParam(required = false) String eventId) throws Exception{
		NavBar currentNavbar = null;
		String mode = "";
		String page = "";
		
		// Case 1: eventId doesn't provided, return a "starter" page.
		if(eventId == null) {
			currentNavbar = NavBar.EDIT_EVENT;
			mode = "DEFAULT";
			page = "events/edit_starter";
		}
		
		// Case 2-5 : eventId provided.
		else {
			
			// Case 2: Invalid eventId. (Not a Long number)
			Long id = NumberUtils.safeCreateLong(eventId);
			if(id == null) {
				currentNavbar = NavBar.EDIT_EVENT;
				mode = "INVALID_EVENT_ID";
				page = "events/edit_starter";
			}
			
			// Case 3-5 : Valid eventId.
			else {
				
				// Case 3: Negative eventId, add event.
				if(id <= -1) {
					currentNavbar = NavBar.ADD_EVENT;
					mode = "ADD";
					page = "events/edit_main";
				}
				
				// Case 4-5: Zero or positive eventId, add event.
				else {
					
					// Case 4: eventId found in database, edit event
					LinkedHashMap<String, Object> event = eventService.findById(id, true);
					if(Boolean.TRUE.equals(event.get("exists"))) {
						currentNavbar = NavBar.EDIT_EVENT;
						mode = "EDIT";
						page = "events/edit_main";
						model.addAttribute("eventDetails", event);
					}
					
					// Case 5: eventId cannot be found, 
					else {
						currentNavbar = NavBar.EDIT_EVENT;
						mode = "EVENT_NOT_FOUND";
						page = "events/edit_starter";
					}
				}
			}
		}
		
		model.addAttribute("eventId", eventId);
		model.addAttribute("mode", mode);
        model.addAttribute("navbar", currentNavbar);
		return page;
	}
}
