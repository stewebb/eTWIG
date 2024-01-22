package net.grinecraft.etwig.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.grinecraft.etwig.services.GraphicsRequestService;
import net.grinecraft.etwig.dto.EventDetailsDTO;
import net.grinecraft.etwig.services.EmailService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.WebReturn;

@RestController
@RequestMapping(value = "/api/private/")
//@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
public class GraphicsRequestAPIController {
	
	@Autowired
	private GraphicsRequestService graphicsRequestService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("/countRequestsByEventId")
    public Map<String, Object> countRequestsByEventId(@RequestParam Long eventId) throws Exception {
		
		Map<String, Object> myReturn = WebReturn.errorMsg(null, true);
		myReturn.put("count", graphicsRequestService.countByEventId(eventId));
		return myReturn;
		
	}
	
	@PostMapping(value = "/requestGraphic")
    public Map<String, Object> requestGraphic(@RequestBody Map<String, Object> requestInfo) throws Exception {
		
		// Check permission again!
		EventDetailsDTO event = eventService.findById(Long.parseLong(requestInfo.get("eventId").toString()));
		if(!eventService.eventEditPermissionCheck(event.getPosition().getPortfolio())) {
			return WebReturn.errorMsg("You don't have permission to make this request.", false);
		} 
		
		// Add request info to the DB.
		graphicsRequestService.addRequest(requestInfo);

		// Send an email to all graphics managers
		emailService.graphicsRequest(requestInfo);		
        return WebReturn.errorMsg(null, true);
    }
}
