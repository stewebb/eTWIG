package net.etwig.webapp.controller.graphics;

import net.etwig.webapp.dto.events.GraphicsRequestEventInfoDTO;
import net.etwig.webapp.dto.graphics.PendingRequestsDetailsDTO;
import net.etwig.webapp.services.EventGraphicsService;
import net.etwig.webapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured({"hasAuthority(ROLE_GRAPHICS)"})
@RequestMapping("/graphics/events/")
public class EventGraphicsController {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventGraphicsService eventGraphicsService;

	//@Autowired
	//private Us

	/**
	 * The page of event graphics list
	 * @return The event_list template.
	 */

	@GetMapping("/list")  
	public String list(){
		return "graphics/event_list";
	}

	@GetMapping("/view")
	public String view(Model model, @RequestParam @NonNull Long eventId) throws Exception{

		// Get event info and existence check.
		GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);
		if(event == null) {
			model.addAttribute("reason", "Event with id=" + eventId + " doesn't exist.");
			return "_errors/custom_error";
		}

		model.addAttribute("eventInfo", event);

		// Separated query.
		model.addAttribute("eventBanners", eventGraphicsService.getGraphicsDetailsByEventId(eventId, true));
		model.addAttribute("eventGraphics", eventGraphicsService.getGraphicsDetailsByEventId(eventId, false));


		return "graphics/event_view";
	}

}
