package net.etwig.webapp.controller.page;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.dto.graphics.BannerRequestDetailsDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForDetailsPageDTO;
import net.etwig.webapp.model.BannerRequest;
import net.etwig.webapp.services.BannerRequestService;
import net.etwig.webapp.services.EventGraphicsService;
import net.etwig.webapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public String eventGraphics(Model model, @RequestParam Long eventId) throws Exception {

		// EventDetailsDTO eventDetails = new EventDetailsDTO(bannerRequest.getEvent());

		// Get event info and existence check.
		//GraphicsRequestEventInfoDTO event = eventService.findEventsForGraphicsRequestById(eventId);

		EventDetailsDTO event = eventService.findById(eventId);
		if(event == null) {
			model.addAttribute("reason", "Event with id=" + eventId + " doesn't exist.");
			return "error_page";
		}

		// Disable pagination for result, but sort by uploadedTime descending.
		Pageable pageable = Pageable.unpaged(Sort.by(Sort.Direction.DESC, "uploadedTime"));

		// Get graphics info
		Page<EventGraphicsAPIForDetailsPageDTO> banners = eventGraphicsService.findByCriteriaForDetails(eventId, true, pageable);

		//System.out.println(banners);
		//for (EventGraphicsAPIForDetailsPageDTO banner : banners) {
		//	System.out.println(banner);
		//}
		// Separated query.
		model.addAttribute("eventBanners", banners);
		//model.addAttribute("eventGraphics", eventGraphicsService.getGraphicsDetailsByEventId(eventId, false));
		model.addAttribute("eventInfo", event);


		return "graphics/event_view";
	}

	@GetMapping("/approvalList.do")
	public String approvalList() throws Exception{
		return "graphics/approval_list";
	}

	@GetMapping("/approvalDetails.do")
	public String approvalDetails(Model model, @RequestParam @NonNull Long requestId) {

		// Get banner request details
		BannerRequest bannerRequest = bannerRequestService.findById(requestId);
		if (bannerRequest == null) {
			model.addAttribute("reason", "Banner request with id=" + requestId + " does not exist.");
			return "error_page";
		}
		BannerRequestDetailsDTO requestDetails = new BannerRequestDetailsDTO(bannerRequest);

		// Get relevant event information.
		EventDetailsDTO eventDetails = new EventDetailsDTO(bannerRequest.getEvent());

		model.addAttribute("requestInfo", requestDetails);
		model.addAttribute("eventInfo", eventDetails);
		return "graphics/approval_details";
	}
}
