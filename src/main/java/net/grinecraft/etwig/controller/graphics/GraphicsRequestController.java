package net.grinecraft.etwig.controller.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.grinecraft.etwig.dto.BannerRequestEventInfoDTO;
import net.grinecraft.etwig.services.BannerRequestService;
import net.grinecraft.etwig.services.EventService;

@Controller
@Secured({"ROLE_ADMINISTRATOR", "ROLE_EVENT_MANAGER"})
@RequestMapping("/graphics/request/") 
public class GraphicsRequestController {

	@Autowired
	private BannerRequestService bannerRequestService;
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/event")  
	public String view(Model model, @RequestParam Long eventId) throws Exception{
		
		System.out.println(bannerRequestService.getRequestsByEvent(eventId));
		
		model.addAttribute("count", bannerRequestService.countByEventId(eventId));
		model.addAttribute("requestInfo",bannerRequestService.getRequestsByEvent(eventId));
		model.addAttribute("eventInfo",eventService.findEventsForBannerRequestById(eventId));

		
		return "graphics/request_event";
	}
}
