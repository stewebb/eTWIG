package net.etwig.webapp.controller.page;

import net.etwig.webapp.dto.events.GraphicsRequestEventInfoDTO;
import net.etwig.webapp.dto.graphics.PendingRequestsDetailsDTO;
import net.etwig.webapp.services.EventGraphicsService;
import net.etwig.webapp.services.EventService;
import net.etwig.webapp.services.BannerRequestService;
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
@RequestMapping("/graphics")
public class GraphicsPageController {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventGraphicsService eventGraphicsService;

	@Autowired
	private BannerRequestService bannerRequestService;

	/**
	 * The event list page
	 * @return The event_list template.
	 */

	@GetMapping("/eventList.do")
	public String eventList(){

		// TODO **Copy** this page to /events, then display different contents based on user permission
		return "graphics/event_list";
	}

	@GetMapping("/eventGraphics.do")
	public String eventGraphics(Model model, @RequestParam Long eventId) throws Exception{

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

	@GetMapping("/approvalList.do")
	public String approvalList() throws Exception{
		return "graphics/approval_list";
	}

	@GetMapping("/approvalDetails.do")
	public String approvalDetails(Model model, @RequestParam @NonNull Long requestId) {

		// Get request info
		PendingRequestsDetailsDTO request = bannerRequestService.getPendingRequestsById(requestId);
		if(request == null) {
			model.addAttribute("reason", "Graphics request with id=" + requestId + " doesn't exist, or it has been finalized.");
			return "_errors/custom_error";
		}

		model.addAttribute("requestInfo", request);
		return "graphics/approval_details";

		// TODO Decide and history in the same page.
		// TODO Use data tables to display data.
	}

}
