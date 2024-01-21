package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.services.GraphicsRequestService;
import net.grinecraft.etwig.services.EventService;
import net.grinecraft.etwig.util.BooleanUtils;

@Controller
@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
@RequestMapping("/graphics/request/") 
public class GraphicsRequestController {

	@Autowired
	private GraphicsRequestService bannerRequestService;
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/event")  
	public String view(Model model, @RequestParam Long eventId, @RequestParam(required=false) String embedded) throws Exception{
		
		// Get event info and existence check.
		GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);
		if(event == null) {
			model.addAttribute("embedded", false);
			model.addAttribute("reason", "Event with id=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}
		model.addAttribute("eventInfo", event);

		// Get the number of requests of this event.
		model.addAttribute("count", bannerRequestService.countByEventId(eventId));
		
		// Has pending requests left?
		model.addAttribute("hasPending", bannerRequestService.hasPendingRequests(eventId));
		
		// Edit permission check
		//model.addAttribute("editPermission", eventService.eventEditPermissionCheck(event.getPortfolio()));
		model.addAttribute("editPermission", null);
		
		model.addAttribute("requestInfo",bannerRequestService.getRequestsByEvent(eventId));
		model.addAttribute("embedded", BooleanUtils.toBoolean(embedded));
		
		return "graphics/request_event";
	}
}
